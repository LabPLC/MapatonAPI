package mx.krieger.labplc.mapaton.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.joda.time.LocalTime;

import com.google.appengine.tools.cloudstorage.GcsFileMetadata;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;
import com.google.appengine.tools.cloudstorage.GcsFilename;
import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.google.appengine.tools.cloudstorage.GcsOutputChannel;
import com.google.appengine.tools.cloudstorage.GcsService;
import com.google.appengine.tools.cloudstorage.GcsServiceFactory;
import com.google.appengine.tools.cloudstorage.RetryParams;
import com.google.common.net.MediaType;

import mx.krieger.internal.commons.utils.location.GpsLocationUtils;
import mx.krieger.internal.commons.utils.location.beans.GPSLocation;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.labplc.mapaton.handlers.TrailsHandler;
import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;
import mx.krieger.labplc.mapaton.model.entities.Route;
import mx.krieger.labplc.mapaton.model.wrappers.PointData;

public class GTFSHelper {
	public static final String GTFS_FILE = "mapatonGTFS.zip";
	public static final String BUCKET_NAME = "mapaton-public-gtfs";
	
	private static Logger logger = new Logger(GTFSHelper.class);
	
	private static final double AVERAGE_SPEED = 14.5; // km/h
	private static final double AVERAGE_SPEED2 = 4.027778; // m/s
	private static final int STOP_DISTANCE = 200;
	
	private static final String AGENCY_HEADER = "agency_id, agency_name,agency_url,agency_timezone,agency_phone,agency_lang\n";
	private static final String STOPS_HEADER = "stop_id,stop_name,stop_desc,stop_lat,stop_lon,stop_url,location_type,parent_station\n";
	private static final String TRIPS_HEADER = "route_id,service_id,trip_id,trip_headsign,shape_id\n";
	private static final String ROUTES_HEADER = "route_id,route_short_name,route_long_name,route_desc,route_type\n";
	private static final String STOPTIMES_HEADER = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,pickup_type,drop_off_type,timepoint\n";
	private static final String CALENDAR_HEADER = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date\n";
	private static final String CALENDAR_DATES_HEADER = "service_id,date,exception_type\n";
	private static final String FARE_ATTRIBUTES_HEADER = "fare_id,price,currency_type,payment_method,transfers,transfer_duration\n";
	private static final String FARE_RULES_HEADER = "fare_id,route_id,origin_id,destination_id,contains_id\n";
	private static final String SHAPES_HEADER = "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled\n";
	private static final String FREQUENCIES_HEADER = "trip_id,start_time,end_time,headway_secs\n";
	
	
	private static final String AGENCY_TXT = "agency.txt";
	private static final String STOPS_TXT = "stops.txt";
	private static final String TRIPS_TXT = "trips.txt";
	private static final String ROUTES_TXT = "routes.txt";
	private static final String STOPTIMES_TXT = "stop_times.txt";
	private static final String CALENDAR_TXT = "calendar.txt";
	private static final String CALENDAR_DATES_TXT = "calendar_dates.txt";
	private static final String FARE_ATTRIBUTES_TXT = "fare_attributes.txt";
	private static final String FARE_RULES_TXT = "fare_rules.txt";
	private static final String SHAPES_TXT = "shapes.txt";
	private static final String FREQUENCIES_TXT = "frequencies.txt";
	
	
	
	private final GcsService gcsService = GcsServiceFactory.createGcsService(RetryParams.getDefaultInstance());
	private List<RouteGtfs> routes = new ArrayList<>();
	private List<Stop> stops = new ArrayList<>();
	private List<StopTime> stoptimes = new ArrayList<>();
	private List<Trip> trips = new ArrayList<>();
	private List<CalendarGtfs> calendars = new ArrayList<>();
	
	
	

	private void writeToFile(GcsFilename fileName, byte[] content) throws IOException {
		GcsOutputChannel outputChannel = gcsService.createOrReplace(fileName, GcsFileOptions.getDefaultInstance());
		outputChannel.write(ByteBuffer.wrap(content));
		outputChannel.close();
	}

	private byte[] readFromFile(GcsFilename fileName) throws IOException { 
		int fileSize = (int) gcsService.getMetadata(fileName).getLength();
		ByteBuffer result = ByteBuffer.allocate(fileSize);
		try (GcsInputChannel readChannel = gcsService.openReadChannel(fileName, 0)) {
			readChannel.read(result);
		}
		return result.array();
	}
	
	private void zipFiles(final GcsFilename... filesToZip) throws IOException {

		GcsFilename targetZipFile = new  GcsFilename(BUCKET_NAME, GTFS_FILE);
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
	            try {
	                final GcsFileMetadata meta = gcsService.getMetadata(file);
	                if (meta == null) {
//	                    logger.warning(file.toString() + " NOT FOUND. Skipping.");
	                    continue;
	                }
	                //int fileSize = (int) meta.getLength();
	                //  LOGGER.fine("adding " + file.toString());
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
	
	public byte[] getGTFSZipFile() throws IOException{
		GcsFilename targetZipFile = new  GcsFilename(BUCKET_NAME, GTFS_FILE);
		return readFromFile(targetZipFile);
		 
	}
	
	public void generateGTFS(long[] trailIds) throws IOException{
		logger.debug("generating with ids " + Arrays.toString(trailIds));
		CalendarGtfs calendar = new CalendarGtfs();
		calendar.serviceId = "C1";
		calendars.add(calendar);
		int trailNumber = 0;
		for(long trailId: trailIds){
			trailNumber ++;
			RegisteredTrail trail;
			try {
				trail = TrailsHandler.getTrailById(trailId);
			} catch (TrailNotFoundException e) {
				logger.error("Trail not found with id : " + trailId);
				e.printStackTrace();
				continue;
			}
			Route r;
			if(trail.getRoute() != null){
				r = trail.getRoute().get();
			} else {
				r = new Route();
				r.setName("Ruta");
				r.setId((long)trailNumber);
			}
			RouteGtfs rg = new RouteGtfs();
			rg.routeId = r.getId() + "";
			rg.shortName = r.getName() + r.getId();
			rg.longName = trail.getOrigin().get().getStation().getName() + " - " + trail.getDestination().get().getStation().getName();
			routes.add(rg);
			logger.debug("route added");
			
			Trip trip = new Trip();
			trip.tripId = trail.getId() + "";
			if(trail.getBranch() != null){
				trip.headsign = trail.getBranch().get().getName();
			}
			trip.routeId = rg.routeId;
			trip.serviceId = calendar.serviceId;
			trips.add(trip);
			
			
			double distance = trail.getTotalMeters();
			
			int position = 0;
			int seconds = 0;
			LocalTime start = new LocalTime(6, 0);
			LocalTime current = new LocalTime(6,0);
			LocalTime end = new LocalTime(22,0);

			GPSLocation prev = null;
			List<PointData> points = trail.getPoints();
			int pointSize = points.size();
			int pointNumber = 0;
			for (int i = 0; i < pointSize; i ++){
//			for (PointData pd : points){
				pointNumber ++;
				PointData pd = points.get(i);
				GPSLocation curr = pd.getLocation();
				if(prev == null){
					prev = curr;
				} else {
					double dist = GpsLocationUtils.getDistanceBetweenPoints(prev, curr);
					if(dist > STOP_DISTANCE){ 
						prev = curr;
						seconds += dist / AVERAGE_SPEED2;
					} else {
						continue;
					}
				}
				position ++;
				Stop stop = new Stop();
				stop.lat = prev.getLatitude();
				stop.lon = prev.getLongitude();
				stop.stopId = trailNumber + " " + position;
				stop.name = rg.shortName + " " + stop.stopId;
				stop.description = rg.longName + " " + stop.stopId;

				
				StopTime stoptime = new StopTime();
				stoptime.stopId = stop.stopId;
				stoptime.seq = position;
				stoptime.tripId = trip.tripId;
				if(pointNumber == 1){
					stoptime.arrivalTime = "07:00:00";
					stoptime.departureTime = "07:00:00";					
				}
				if(pointNumber == pointSize){
					stoptime.arrivalTime = "09:00:00";
					stoptime.departureTime = "09:00:00";					
				}
				
				stops.add(stop);
				stoptimes.add(stoptime);
			}
			
			
		}
		
		try {
			updateGtfs();
		} catch (IOException e) {
			logger.error("No se pudo actualizar el gtfs");
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	public void generateAllStoptimes(){
		
	}
	
	private void updateGtfs() throws IOException{
		GcsFilename calsFile = new GcsFilename(BUCKET_NAME, CALENDAR_TXT);
		GcsFilename stopsFile = new GcsFilename(BUCKET_NAME, STOPS_TXT);
		GcsFilename routesFile = new GcsFilename(BUCKET_NAME, ROUTES_TXT);
		GcsFilename tripsFile = new GcsFilename(BUCKET_NAME, TRIPS_TXT);
		GcsFilename stoptimesFile = new GcsFilename(BUCKET_NAME, STOPTIMES_TXT);
		GcsFilename agencyFile = new GcsFilename(BUCKET_NAME, AGENCY_TXT);

		updateAgency(agencyFile);
		updateFile(stopsFile, stops, STOPS_HEADER);
		updateFile(calsFile, calendars, CALENDAR_HEADER);
		updateFile(tripsFile, trips, TRIPS_HEADER);
		updateFile(routesFile, routes, ROUTES_HEADER);
		updateFile(stoptimesFile, stoptimes, STOPTIMES_HEADER);
		
		zipFiles(agencyFile, calsFile, stopsFile, routesFile, tripsFile, stoptimesFile);
	}
	
	private void updateAgency(GcsFilename file) throws IOException{
		String contents = AGENCY_HEADER + "FunBus,The Fun Bus,http://www.thefunbus.org,America/Los_Angeles,(310) 555-0222,es";
		writeToFile(file, contents.getBytes());
	}
	private void updateFile(GcsFilename file, List<? extends Gtfize> elements, String header) throws IOException{
		StringBuilder sb = new StringBuilder(header);
		for	(Gtfize s : elements){
			sb.append(s.toTxt());
		}
		writeToFile(file, sb.toString().getBytes());
	}

	private static abstract class Gtfize{
		public abstract String toTxt();
		
	}
	private static class RouteGtfs extends Gtfize{

		public String routeId;
		public String shortName;
		public String longName;
		public int type = 3;
		
		public String toTxt(){
			StringBuilder sb = new StringBuilder();
			sb.append(routeId).append(",")
				.append(shortName).append(",")
				.append(longName).append(",")
				.append(shortName).append(" - ").append(longName).append(",")
				.append(type).append("\n");
			return sb.toString();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
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
			RouteGtfs other = (RouteGtfs) obj;
			if (routeId == null) {
				if (other.routeId != null)
					return false;
			} else if (!routeId.equals(other.routeId))
				return false;
			return true;
		}
		
	}
	private static class Stop extends Gtfize{
		public String stopId;
		public String name;
		public String description;
		public double lat;
		public double lon;
		public String url = "";
		public String type = "";
		public String parentStation = "";
		
		public String toTxt(){
			StringBuilder sb = new StringBuilder();
			sb.append(stopId).append(",")
				.append(name).append(",")
				.append(description).append(",")
				.append(lat).append(",")
				.append(lon).append(",")
				.append(url).append(",")
				.append(type).append(",")
				.append(parentStation).append("\n");
			return sb.toString();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((stopId == null) ? 0 : stopId.hashCode());
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
			Stop other = (Stop) obj;
			if (stopId == null) {
				if (other.stopId != null)
					return false;
			} else if (!stopId.equals(other.stopId))
				return false;
			return true;
		}
		
	}
	private static class StopTime extends Gtfize{
		public String tripId;
		public String arrivalTime = "";
		public String departureTime = "";
		public String stopId;
		public int seq;
		public int pickup = 0;
		public int drop = 0;
		public int timepoint = 0;
		
		public String toTxt(){
			StringBuilder sb = new StringBuilder();
			sb.append(tripId).append(",")
				.append(arrivalTime).append(",")
				.append(departureTime).append(",")
				.append(stopId).append(",")
				.append(seq).append(",")
				.append(pickup).append(",")
				.append(drop).append(",")
				.append(timepoint).append("\n");
			return sb.toString();
		}

		/* (non-Javadoc)
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
	private static class Trip extends Gtfize{
		public String routeId;
		public String serviceId;
		public String tripId;
		public String headsign="";
		public String shapeId="";
		
		public String toTxt(){
			StringBuilder sb = new StringBuilder();
			sb.append(routeId).append(",")
				.append(serviceId).append(",")
				.append(tripId).append(",")
				.append(headsign).append(",")
				.append(shapeId).append("\n");
			return sb.toString();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((tripId == null) ? 0 : tripId.hashCode());
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
			Trip other = (Trip) obj;
			if (tripId == null) {
				if (other.tripId != null)
					return false;
			} else if (!tripId.equals(other.tripId))
				return false;
			return true;
		}
		
	}
	private static class CalendarGtfs extends Gtfize{
		public String serviceId;
		public int monday=1;
		public int tuesday=1;
		public int wednesday=1;
		public int thursday=1;
		public int friday=1;
		public int saturday=1;
		public int sunday=1;
		public String startDate="20100101";
		public String endDate="20200101";
		
		
		public String toTxt(){
			StringBuilder sb = new StringBuilder();
			sb.append(serviceId).append(",")
				.append(monday).append(",")
				.append(tuesday).append(",")
				.append(wednesday).append(",")
				.append(thursday).append(",")
				.append(friday).append(",")
				.append(saturday).append(",")
				.append(sunday).append(",")
				.append(startDate).append(",")
				.append(endDate).append("\n");
			return sb.toString();
		}


		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
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
			CalendarGtfs other = (CalendarGtfs) obj;
			if (serviceId == null) {
				if (other.serviceId != null)
					return false;
			} else if (!serviceId.equals(other.serviceId))
				return false;
			return true;
		}
		
	}
	

}
