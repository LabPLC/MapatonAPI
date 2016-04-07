package mx.krieger.pides.mapaton.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.common.net.MediaType;

import mx.krieger.internal.commons.utils.constants.EarthConstants;
import mx.krieger.internal.commons.utils.location.beans.GPSLocation;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.pides.mapaton.handlers.TrailsHandler;
import mx.krieger.pides.mapaton.model.entities.RegisteredTrail;
import mx.krieger.pides.mapaton.model.wrappers.PointData;

/**
 * This class is used to manage the contents of the application through the
 * dashboard.
 *
 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
 * @version 1.0.0.0
 * @since 22 / feb / 2016
 */
public class GTFSHelper {
	public static final String GTFS_FILE = "mapatonGTFS.zip";
	public static final String GTFS_VERSION_FILE = "mapatonGTFS_version_.zip";
	public static final String BUCKET_NAME = "mapaton-public-gtfs";
	private static final int MAX_CAPACITY = 2097152; // 2 ^ 21, used to avoid an
														// out of memory error

	private static Logger logger = new Logger(GTFSHelper.class);

	// private static final double AVERAGE_SPEED = 14.5; // km/h
	private static final double AVERAGE_SPEED_METERS_PER_SEC = 4.027778; // m/s,
	// used to get an approximation of the time from a point to another
	private static final int STOP_DISTANCE = 250;
	private static final int STOPTIMES_PER_HOUR = 4;
	private static final int TOTAL_SERVICE_HOURS = 16; // 6:00 - 22:00
	private static final int STOPTIME_MINUTE_DIFFERENCE = 60 / STOPTIMES_PER_HOUR;
	private static final int NUMBER_OF_STOPTIMES = STOPTIMES_PER_HOUR * TOTAL_SERVICE_HOURS;

	/**
	 * Headers for the files needed in the GTFS Zip
	 */
	private static final String AGENCY_HEADER = "agency_id, agency_name,agency_url,agency_timezone,agency_phone,agency_lang\n";
	private static final String STOPS_HEADER = "stop_id,stop_name,stop_desc,stop_lat,stop_lon\n";
	private static final String TRIPS_HEADER = "route_id,service_id,trip_id,trip_headsign,direction_id,shape_id\n";
	private static final String ROUTES_HEADER = "route_id,route_short_name,route_long_name,route_desc,route_type\n";
	private static final String STOPTIMES_HEADER = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,pickup_type,drop_off_type,timepoint\n";
	private static final String CALENDAR_HEADER = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date\n";
	private static final String FARE_ATTRIBUTES_HEADER = "fare_id,price,currency_type,payment_method,transfers,transfer_duration\n";
	private static final String FARE_RULES_HEADER = "fare_id,route_id\n";
	private static final String SHAPES_HEADER = "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence\n";
	//private static final String CALENDAR_DATES_HEADER = "service_id,date,exception_type\n";

	/**
	 * File names for the GTFS Zip
	 */
	private static final String AGENCY_TXT = "agency.txt";
	private static final String STOPS_TXT = "stops.txt";
	private static final String TRIPS_TXT = "trips.txt";
	private static final String ROUTES_TXT = "routes.txt";
	private static final String STOPTIMES_TXT = "stop_times.txt";
	private static final String CALENDAR_TXT = "calendar.txt";
	private static final String FARE_ATTRIBUTES_TXT = "fare_attributes.txt";
	private static final String FARE_RULES_TXT = "fare_rules.txt";
	private static final String SHAPES_TXT = "shapes.txt";
	//private static final String CALENDAR_DATES_TXT = "calendar_dates.txt";

	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private Map<String, RouteGtfs> routesMap = new HashMap<>();
	private Map<String, List<String>> fareRules = new HashMap<>();
	private Map<Location, Stop> stopsMap = new TreeMap<>();// new HashMap<>();
	private List<StopTime> stoptimes = new ArrayList<>();
	private List<Trip> trips = new ArrayList<>();
	private List<CalendarGtfs> calendars = new ArrayList<>();
	private List<Shape> shapes = new ArrayList<>();

	/**
	 * Method used to write to a Google Cloud Services file a byte array
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param fileName
	 *            the Google Cloud Services file that points to the file to
	 *            write
	 * @param content
	 *            the byte array that will be written
	 * @since 22 / feb / 2016
	 */
	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	/**
	 * Method used to read the contents of a Google Cloud Services file to a
	 * byte array
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param fileName
	 *            the Google Cloud Services file that points to the file to
	 *            write
	 * @return the byte array that contains the contents
	 * @since 22 / feb / 2016
	 */
	private byte[] readFromFile(GcsFilename fileName) throws IOException {
		int fileSize = (int) gcsService.getMetadata(fileName).getLength();
		ByteBuffer result = ByteBuffer.allocate(fileSize);
		try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0)) {
			readChannel.read(result);
		}
		return result.array();
	}

	/**
	 * Method used to ZIP a Google Cloud Services file array to a predefined
	 * Google Cloud Services file Zip
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param filesToZip
	 *            the array of Google Cloud Services files that points to the
	 *            zip
	 * @since 22 / feb / 2016
	 */
	private void zipFiles(final GcsFilename... filesToZip) throws IOException {
		logger.debug("zipping files " + filesToZip.length);

		GcsFilename targetZipFile = new GcsFilename(BUCKET_NAME, GTFS_FILE);
		final int fetchSize = 4 * 1024 * 1024;
		final int readSize = 2 * 1024 * 1024;
		GcsOutputChannel outputChannel = null;
		ZipOutputStream zip = null;
		try {
			GcsFileOptions options = new GcsFileOptions.Builder().mimeType(MediaType.ZIP.toString()).build();
			outputChannel = gcsService.createOrReplace(targetZipFile, options);
			zip = new ZipOutputStream(Channels.newOutputStream(outputChannel));
			GcsInputChannel readChannel = null;
			for (GcsFilename file : filesToZip) {
				logger.debug("zipping file: " + file);
				try {
					final GcsFileMetadata meta = gcsService.getMetadata(file);
					if (meta == null) {
						continue;
					}
					ZipEntry entry = new ZipEntry(file.getObjectName());
					zip.putNextEntry(entry);
					readChannel = gcsService.openPrefetchingReadChannel(file, 0, fetchSize);
					final ByteBuffer buffer = ByteBuffer.allocate(readSize);
					int bytesRead = 0;
					while (bytesRead >= 0) {
						bytesRead = readChannel.read(buffer);
						buffer.flip();
						zip.write(buffer.array(), buffer.position(), buffer.limit());
						buffer.rewind();
						buffer.limit(buffer.capacity());
					}

				} finally {
					logger.debug("closing file");
					zip.closeEntry();
					readChannel.close();
				}
			}
		} finally {
			zip.flush();
			zip.close();
			outputChannel.close();
		}
	}

	/**
	 * Method used to get the GTFS ZIP from Google Cloud Services
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @return filesToZip the main GTFS ZIP file that was generated
	 * @since 22 / feb / 2016
	 */
	public byte[] getGTFSZipFile() throws IOException {
		logger.debug("getting gtfs version 1");
		GcsFilename targetZipFile = new GcsFilename(BUCKET_NAME, GTFS_FILE);
		return readFromFile(targetZipFile);

	}
	/**
	 * Method used to get the GTFS ZIP from Google Cloud Services
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @return filesToZip the main GTFS ZIP file that was generated
	 * @since 22 / feb / 2016
	 */
	public byte[] getGTFSZipFile(String version) throws IOException {
		logger.debug("getting gtfs version " + version);
		GcsFilename targetZipFile = new GcsFilename(BUCKET_NAME, GTFS_VERSION_FILE.replaceAll("_version_", version));
		return readFromFile(targetZipFile);

	}

	/**
	 * Method used to generate the GTFS ZIP file from an array of trail Ids.
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param trailIds
	 *            the trail Ids that will be part of the generated GTFS ZIP file
	 * @since 22 / feb / 2016
	 */
	public void generateGTFS(long[] trailIds) throws IOException {
		logger.debug("generating with ids " + Arrays.toString(trailIds));
		CalendarGtfs calendar = new CalendarGtfs();
		calendar.serviceId = "C1";
		calendars.add(calendar);
		int trailNumber = 0;
		int pointPosition = 0;
		NumberFormat formatter = new DecimalFormat("#0.00");
		int size = trailIds.length;
		for (long trailId : trailIds) {
			trailNumber++;
			logger.debug("looking at trail " + trailNumber + " of " + size);
			RegisteredTrail trail;
			try {
				trail = TrailsHandler.getTrailById(trailId);
			} catch (TrailNotFoundException e) {
				logger.error("Trail not found with id : " + trailId);
				e.printStackTrace();
				continue;
			}
			

			List<PointData> points = trail.getPoints();
			int pointSize = points.size();
			if(pointSize < 2) {
				continue;
			}
			
			RouteGtfs rg = new RouteGtfs();
			boolean isInbound = false;
			boolean isNew = false;
			String origin = trail.getOrigin().get().getStation().getName();
			String destiny = trail.getDestination().get().getStation().getName();
			if (routesMap.containsKey(origin + " - " + destiny)) {
				rg = routesMap.get(origin + " - " + destiny);
				rg.description += "; " + trailId;
				routesMap.put(origin + " - " + destiny, rg);
			} else if (routesMap.containsKey(destiny + " - " + origin)) {
				rg = routesMap.get(destiny + " - " + origin);
				rg.description += "; " + trailId;
				routesMap.put(destiny + " - " + origin, rg);

				isInbound = true;
			} else {
				rg.routeId = trailNumber + "";
				rg.shortName = "R " + trailNumber;
				rg.longName = origin + " - " + destiny;
				rg.description = "Ruta generada a partir de los recorridos: " + trailId;
				routesMap.put(origin + " - " + destiny, rg);
				isNew = true;
			}
			logger.debug("route added");

			if (isNew) {
				String tariff = formatter.format(trail.getMaxTariff());
				if (fareRules.containsKey(tariff)) {
					fareRules.get(tariff).add(rg.routeId);
				} else {
					fareRules.put(tariff, new ArrayList<String>());
					fareRules.get(tariff).add(rg.routeId);
				}
				logger.debug("fare rule added");
			}

			Trip baseTrip = new Trip();
			baseTrip.tripId = trailNumber + "T";
			if (trail.getBranch() != null) {
				baseTrip.headsign = trail.getBranch().get().getName();
			}
			baseTrip.routeId = rg.routeId;
			baseTrip.serviceId = calendar.serviceId;
			baseTrip.direction = isInbound ? "1" : "0";
			baseTrip.shapeId = trailNumber + "SH";

			logger.debug("base trip generated as " + baseTrip);
			for (int i = 0; i < NUMBER_OF_STOPTIMES; i++) {
				Trip trip = new Trip();
				trip.tripId = baseTrip.tripId + i;
				trip.headsign = baseTrip.headsign;
				trip.routeId = baseTrip.routeId;
				trip.serviceId = baseTrip.serviceId;
				trip.direction = baseTrip.direction;
				trip.shapeId = baseTrip.shapeId;

				trips.add(trip);
			}
			logger.debug("trips added");

			int position = 0;
			GtfsTime current = new GtfsTime(6, 0, 0);

			Shape baseShape = new Shape();
			baseShape.id = baseTrip.shapeId;

			GPSLocation prev = null;
//			List<PointData> points = trail.getPoints();
//			int pointSize = points.size();
			logger.debug("generating stops and stoptimes");
			for (int i = 0; i < pointSize; i++) {
				double seconds = 0;
				PointData pd = points.get(i);
				GPSLocation curr = pd.getLocation();

				Shape shape = new Shape();
				shape.id = baseTrip.shapeId;
				shape.lat = curr.getLatitude();
				shape.lon = curr.getLongitude();
				shape.seq = pointPosition++;

				shapes.add(shape);

				if (prev == null) {
					prev = curr;
				} else {
					double dist = getHarvesineDistance(prev, curr);
					if (dist > STOP_DISTANCE) {
						prev = curr;
						seconds = dist / AVERAGE_SPEED_METERS_PER_SEC;
					} else {
						continue;
					}
				}
				logger.debug("generating new stop and stoptimes from point");
				current = current.addSeconds((int) seconds);

				Location loc = new Location(prev.getLatitude(), prev.getLongitude());
				Stop stop;
				if(stopsMap.containsKey(loc)){
					stop = stopsMap.get(loc);
				} else {
					stop = new Stop();
					stop.lat = prev.getLatitude();
					stop.lon = prev.getLongitude();
					stop.stopId = trailNumber + "S" + position;
					stop.name = rg.shortName + " " + stop.stopId;
					stop.description = rg.longName + " " + stop.stopId;
					stopsMap.put(loc, stop);
				}

				generateStoptimes(current, stop, baseTrip, trailNumber * 10000 + position * NUMBER_OF_STOPTIMES);
				position++;

			}
			logger.debug(" trail processing completed");

		}

		try {
			logger.info("updating the zip file");
			updateGtfs();
		} catch (IOException e) {
			logger.error("No se pudo actualizar el gtfs");
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * Method used to generate the StopTimes from start to finish for a given
	 * stop.
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param start
	 *            the starting time point for the groups of stopTimes
	 * @param stop
	 *            the current stop that will be part of the stopTimes
	 * @param trip
	 *            the base trip that contains the id of the trips that will be
	 *            part of the stoptimes
	 * @param position
	 *            the starting position of the sequence of stopTimes
	 * @since 22 / feb / 2016
	 */
	public void generateStoptimes(GtfsTime start, Stop stop, Trip trip, int position) {
		GtfsTime current = start;
		int i;
		for (i = 0; i < NUMBER_OF_STOPTIMES; i++) {
			StopTime stoptime = new StopTime();
			stoptime.stopId = stop.stopId;
			stoptime.seq = position + i;
			stoptime.tripId = trip.tripId + i;

			stoptime.arrivalTime = current.toString();
			// stoptime.departureTime = stoptime.arrivalTime;

			stoptimes.add(stoptime);
			current = current.addMinutes(STOPTIME_MINUTE_DIFFERENCE);
		}
		logger.debug(i + " stoptimes generated for stop " + stop.stopId);

	}

	/**
	 * Method used to update the GTFS ZIP file to be served later on.
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private void updateGtfs() throws IOException {
		GcsFilename calsFile = new GcsFilename(BUCKET_NAME, CALENDAR_TXT);
		GcsFilename stopsFile = new GcsFilename(BUCKET_NAME, STOPS_TXT);
		GcsFilename routesFile = new GcsFilename(BUCKET_NAME, ROUTES_TXT);
		GcsFilename tripsFile = new GcsFilename(BUCKET_NAME, TRIPS_TXT);
		GcsFilename stoptimesFile = new GcsFilename(BUCKET_NAME, STOPTIMES_TXT);
		GcsFilename shapesFile = new GcsFilename(BUCKET_NAME, SHAPES_TXT);
		GcsFilename agencyFile = new GcsFilename(BUCKET_NAME, AGENCY_TXT);
		GcsFilename fareRule = new GcsFilename(BUCKET_NAME, FARE_RULES_TXT);
		GcsFilename fareAttrs = new GcsFilename(BUCKET_NAME, FARE_ATTRIBUTES_TXT);

		logger.debug("updating agencies file");
		updateAgency(agencyFile);
		logger.info("agencies file updated");
		logger.debug("updating fares file");
		updateFareRules(fareRule, fareAttrs);
		logger.info("fare rules and fare attributes file updated");
		logger.debug("updating calendar file");
		updateLargeFile(calsFile, calendars, CALENDAR_HEADER);
		calendars = null;
		logger.info("calendar file updated");
		logger.debug("updating routes file");
		updateLargeFile(routesFile, routesMap.values(), ROUTES_HEADER);
		routesMap = null;
		logger.info("routes file updated");
		logger.debug("updating stops file");
		updateLargeFile(stopsFile, stopsMap.values(), STOPS_HEADER);
		stopsMap = null;
		logger.info("stops file updated");
		logger.debug("updating trips file");
		updateLargeFile(tripsFile, trips, TRIPS_HEADER);
		trips = null;
		logger.info("trips file updated");
		logger.debug("updating stoptimes file");
		updateLargeFile(stoptimesFile, stoptimes, STOPTIMES_HEADER);
		stoptimes = null;
		logger.info("stoptimes file updated");
		logger.debug("updating shapes file");
		updateLargeFile(shapesFile, shapes, SHAPES_HEADER);
		shapes = null;
		logger.info("shapes file updated");

		zipFiles(agencyFile, calsFile, stopsFile, routesFile, tripsFile, stoptimesFile, fareRule, fareAttrs,
				shapesFile);
	}

	/**
	 * Method used to update the AGENCY_TXT
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param file
	 *            the Google Cloud Services file that contains the agencies
	 * @since 22 / feb / 2016
	 */
	private void updateAgency(GcsFilename file) throws IOException {
		String contents = AGENCY_HEADER
				+ "MapatonCDMX,Mapaton Ciudad de Mexico,http://www.mapatoncd.mx,America/Mexico_City,000000000,es";
		writeToFile(file, contents.getBytes("UTF-8"));
	}

	/**
	 * Method used to update the FARE_RULES and FARE_ATTRIBUTES
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param file
	 *            the Google Cloud Services file that contains the fares
	 * @since 22 / feb / 2016
	 */
	private void updateFareRules(GcsFilename rules, GcsFilename attributes)
			throws UnsupportedEncodingException, IOException {
		StringBuilder rulesString = new StringBuilder(FARE_RULES_HEADER);
		StringBuilder attributesString = new StringBuilder(FARE_ATTRIBUTES_HEADER);
		int number = 0;
		for (String tariff : fareRules.keySet()) {
			String tariffKey = "TA" + number;
			attributesString.append(tariffKey).append(",").append(tariff).append(",").append("MXN,0,0,\n");
			for (String route : fareRules.get(tariff)) {
				rulesString.append(tariffKey).append(",").append(route).append("\n");
			}
			number++;
		}
		writeToFile(attributes, attributesString.toString().getBytes("UTF-8"));
		writeToFile(rules, rulesString.toString().getBytes("UTF-8"));

	}

	/**
	 * Method used to update the given file with a collection of elements and
	 * the header for that file
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param file
	 *            the Google Cloud Services file that contains the fares
	 * @since 26 / feb / 2016
	 */
	private void updateLargeFile(GcsFilename file, Collection<? extends GTFSElement> elements, String header)
			throws IOException {
		logger.debug("updating file, entries: " + elements.size());
		String fileName = "TEMP";
		StringBuilder sb = new StringBuilder(MAX_CAPACITY);
		sb.append(header);
		int current = 1;
		GcsFilename temp = new GcsFilename(BUCKET_NAME, fileName + current);

		for (GTFSElement s : elements) {

			sb.append(s.toTxt());
			if (sb.length() + 200 > MAX_CAPACITY) {
				logger.debug("string buffer on max capacity, writing to temp file");
				writeToFile(temp, sb.toString().getBytes("UTF-8"));
				current += 1;
				temp = new GcsFilename(BUCKET_NAME, fileName + current);
				sb = new StringBuilder(MAX_CAPACITY);
			}

		}
		writeToFile(temp, sb.toString().getBytes("UTF-8"));

		int end = current;
		GcsOutputChannel outputChannel = gcsService.createOrReplace(file, GcsFileOptions.getDefaultInstance());
		for (current = 1; current <= end; current++) {
			temp = new GcsFilename(BUCKET_NAME, "TEMP" + current);
			outputChannel.write(ByteBuffer.wrap(readFromFile(temp)));
			gcsService.delete(temp);
		}
		outputChannel.close();

	}

	/**
	 * This method computes the harvesian distance between two gps location
	 * points in metres.
	 * 
	 * @author JJMS(juanjo@krieger.mx)
	 * @see [Harvesian Distance](http://en.wikipedia.org/wiki/Haversine_formula)
	 * @param location1
	 *            the location 1 to get distance from
	 * @param location2
	 *            the location 2 to get distance to
	 * @since 26 / feb / 2016
	 * @return the double distance between two gps location in metres.
	 */
	private static Double getHarvesineDistance(GPSLocation location1, GPSLocation location2) {

		double lat1 = location1.getLatitude();
		double lon1 = location1.getLongitude();
		double lat2 = location2.getLatitude();
		double lon2 = location2.getLongitude();
		double latDistance = toRadians(lat2 - lat1);
		double lonDistance = toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRadians(lat1))
				* Math.cos(toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = EarthConstants.EARTH_RADIOUS * c * 1000;

		return distance;

	}

	/**
	 * This method converts a longitud in meters, to radians.
	 * 
	 * @author JJMS(juanjo@krieger.mx)
	 * @param meters
	 * @since 26 / feb / 2016
	 * @return the equivalent in radiant to this distance.
	 */
	public static double toRadians(double meters) {
		return meters * Math.PI / 180;
	}

	/**
	 * Class that defines the main behaviour of elements that will be placed on
	 * a GTFS file
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static abstract class GTFSElement {
		/**
		 * Method to get the representation of this object as part of a GTFS
		 * file
		 * 
		 * @return the txt representation of this element
		 */
		public abstract String toTxt();

	}

	/**
	 * Class that defines the attributes needed for ROUTES_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class RouteGtfs extends GTFSElement {

		public String routeId;
		public String shortName;
		public String longName;
		public String description;
		public int type = 3;

		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(routeId).append(",").append(shortName).append(",")
					.append(longName.replaceAll(",", "").replaceAll("\"", "")).append(",")
					.append(description).append(",").append(type)
					.append("\n");
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RouteGtfs other = (RouteGtfs) obj;
			if (routeId == null) {
				if (other.routeId != null)
					return false;
			} else if (!routeId.equals(other.routeId))
				return false;
			return true;
		}

	}

	/**
	 * Class that defines the attributes needed for STOPS_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class Stop extends GTFSElement {
		public String stopId;
		public String name;
		public String description;
		public double lat;
		public double lon;

		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(stopId).append(",").append(name).append(",")
					.append(description.replaceAll(",", "").replaceAll("\"", "")).append(",").append(lat).append(",")
					.append(lon).append("\n");
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Stop other = (Stop) obj;
			if (stopId == null) {
				if (other.stopId != null)
					return false;
			} else if (!stopId.equals(other.stopId))
				return false;
			return true;
		}

	}

	/**
	 * Class that defines the attributes needed for STOPTIMES_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class StopTime extends GTFSElement {
		public String tripId;
		public String arrivalTime = "";
		// public String departureTime = ""; //COMMENTED FOR MEMORY EFFICIENCY
		public String stopId;
		public int seq;

		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(tripId).append(",").append(arrivalTime).append(",").append(arrivalTime).append(",") // DEPARTURE
																											// TIME
					.append(stopId).append(",").append(seq).append(",").append("0,") // PICKUP
					.append("0,") // DROP
					.append("0\n"); // TIMEPOINT
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
			result = prime * result + ((tripId == null) ? 0 : tripId.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StopTime other = (StopTime) obj;
			if (stopId == null) {
				if (other.stopId != null)
					return false;
			} else if (!stopId.equals(other.stopId))
				return false;
			if (tripId == null) {
				if (other.tripId != null)
					return false;
			} else if (!tripId.equals(other.tripId))
				return false;
			return true;
		}

	}

	/**
	 * Class that defines the attributes needed for TRIPS_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class Trip extends GTFSElement {
		public String routeId;
		public String serviceId;
		public String tripId;
		public String headsign = "";
		public String direction = "0";
		public String shapeId = "";

		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(routeId).append(",").append(serviceId).append(",").append(tripId).append(",")
					.append(headsign.replaceAll(",", "").replaceAll("\"", "")).append(",").append(direction).append(",")
					.append(shapeId).append("\n");
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((tripId == null) ? 0 : tripId.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Trip other = (Trip) obj;
			if (tripId == null) {
				if (other.tripId != null)
					return false;
			} else if (!tripId.equals(other.tripId))
				return false;
			return true;
		}

	}

	/**
	 * Class that defines the attributes needed for SHAPES_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class Shape extends GTFSElement {
		public String id;
		public double lat;
		public double lon;
		public int seq;

		@Override
		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(id).append(",").append(lat).append(",").append(lon).append(",").append(seq).append("\n");
			return sb.toString();

		}

	}

	/**
	 * Class that defines the attributes needed for CALENDAR_TXT
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class CalendarGtfs extends GTFSElement {
		public String serviceId;
		public int monday = 1;
		public int tuesday = 1;
		public int wednesday = 1;
		public int thursday = 1;
		public int friday = 1;
		public int saturday = 1;
		public int sunday = 1;
		public String startDate = "20100101";
		public String endDate = "20160801";

		public String toTxt() {
			StringBuilder sb = new StringBuilder();
			sb.append(serviceId).append(",").append(monday).append(",").append(tuesday).append(",").append(wednesday)
					.append(",").append(thursday).append(",").append(friday).append(",").append(saturday).append(",")
					.append(sunday).append(",").append(startDate).append(",").append(endDate).append("\n");
			return sb.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CalendarGtfs other = (CalendarGtfs) obj;
			if (serviceId == null) {
				if (other.serviceId != null)
					return false;
			} else if (!serviceId.equals(other.serviceId))
				return false;
			return true;
		}

	}

	/**
	 * Immutable class that defines the way a time for GTFS file is handled It
	 * allows hours greater than 24:00 to comply with the GTFS standard
	 * 
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 22 / feb / 2016
	 */
	private static class GtfsTime {
		public final int hour;
		public final int minute;
		public final int second;

		public GtfsTime(int hour, int minute, int second) {
			super();
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		/**
		 * Method used to add seconds to the time
		 *
		 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
		 * @param seconds
		 *            the seconds that will be added
		 * @since 22 / feb / 2016
		 */
		public GtfsTime addSeconds(double seconds) {
			int newSecond = (int) seconds + second;
			int newMinute = minute;
			int newHour = hour;
			if (newSecond >= 60) {
				newMinute += newSecond / 60;
				newSecond = newSecond % 60;
				if (newMinute >= 60) {
					newHour += newMinute / 60;
					newMinute = newMinute % 60;
				}

			}

			GtfsTime copy = new GtfsTime(newHour, newMinute, newSecond);
			return copy;

		}

		/**
		 * Method used to add minutes to the time
		 *
		 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
		 * @param minutes
		 *            the minutes that will be added
		 * @since 22 / feb / 2016
		 */
		public GtfsTime addMinutes(int minutes) {
			int newMinute = minute + minutes;
			int newHour = hour;
			if (newMinute >= 60) {
				newHour += newMinute / 60;
				newMinute = newMinute % 60;
			}

			GtfsTime copy = new GtfsTime(newHour, newMinute, second);
			return copy;

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":"
					+ String.format("%02d", second);
		}

	}
	
	private static class Location implements Comparable<Location>{
		public double lat;
		public double lon;
		
		
		public Location(double lat, double lon) {
			super();
			this.lat = lat;
			this.lon = lon;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(lat);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(lon);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}
		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Location other = (Location) obj;
			
			double latDistance = toRadians(other.lat - lat);
			double lonDistance = toRadians(other.lon - lon);
			double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRadians(other.lat))
					* Math.cos(toRadians(other.lat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double distance = EarthConstants.EARTH_RADIOUS * c * 1000;
			
			if(Math.abs(distance) > 10)
				return false;

			
			return true;
		}
		@Override
		public int compareTo(Location other) {
			double latDistance = toRadians(other.lat - lat);
			double lonDistance = toRadians(other.lon - lon);
			double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRadians(other.lat))
					* Math.cos(toRadians(other.lat)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double distance = EarthConstants.EARTH_RADIOUS * c * 1000;
			if(Math.abs(distance) < 20)
				return 0;
			else if (other.lat < lat)
				return -1;
			else
				return 1;
		}
		
	}

}
