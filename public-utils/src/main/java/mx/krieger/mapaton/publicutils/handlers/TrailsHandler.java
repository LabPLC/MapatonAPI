/**
 * 28 Jul 2015 - 18:46:26
 */
package mx.krieger.mapaton.publicutils.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.ReadPolicy.Consistency;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import mx.krieger.internal.commons.utils.dateandtime.utils.TimeStampUtils;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.enums.RegisteredTrailStatusEnum;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.mapaton.publicutils.wrappers.*;
import mx.krieger.mapaton.publicutils.utils.CursorHelper;
import mx.krieger.mapaton.publicutils.utils.OfyService;
import mx.krieger.mapaton.publicutils.entities.GenericTrail;
import mx.krieger.mapaton.publicutils.entities.RawTrailPoint;
import mx.krieger.mapaton.publicutils.entities.RegisteredTrail;
import mx.krieger.mapaton.publicutils.entities.SnappedTrailPoint;
import mx.krieger.mapaton.publicutils.entities.Station;

/**
 * This class hadnles all functionaily related to trails.
 * @author Juanjo (juanjo@krieger.mx)
 * @since 16 / feb / 2016
 * @version v1.2.1.0
 */
public class TrailsHandler{
	
	private static Logger logger = new Logger(TrailsHandler.class);

	/**
	 * This method retrieves a trail with the specified id.
	 * @author JJMS (juanjo@krieger.mx)
	 * @since 16 / feb / 2016
	 * @version 1.0.0.0
	 * @param trailId
	 *            The id of the trail.
	 * @return The trail object stored.
	 * @throws TrailNotFoundException
	 *             If the trail is not registered in the data storage.
	 */
	public static RegisteredTrail getTrailById(Long trailId) throws TrailNotFoundException{
		logger.debug("Finding mapped trail by id: " + trailId);
		RegisteredTrail trail = OfyService.ofy().cache(false)
			.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
			.id(trailId).now();
		if(trail == null){
			throw new TrailNotFoundException(
				"Lo sentimos, no hemos podido encontrar el recorrido que solicitaste.");
		}
		logger.debug("Mapped trail found: " + trail);
		return trail;
	}

	/**
	 * This method retrieves a trail with the specified id.
	 * @author JJMS (juanjo@krieger.mx)
	 * @since 16 / feb / 2016
	 * @version 1.0.0.0
	 * @param trailId
	 *            The id of the trail.
	 * @return The trail object stored.
	 * @throws TrailNotFoundException
	 *             If the trail is not registered in the data storage.
	 */
	public static GenericTrail getGenericTrailById(Long trailId) throws TrailNotFoundException{
		logger.debug("Finding mapped trail by id: " + trailId);
		GenericTrail trail = OfyService.ofy().cache(false)
			.consistency(Consistency.STRONG).load().type(GenericTrail.class)
			.id(trailId).now();
		if(trail == null){
			throw new TrailNotFoundException(
				"Lo sentimos, no hemos podido encontrar el recorrido que solicitaste.");
		}
		logger.debug("Mapped trail found: " + trail);
		return trail;
	}

	/**
	 * This method returns the all the trails that have been registered in the competition of MapatonCDMX
	 * paginated by parameter.numberOfElements and parameter.cursor to define where to start and how many elements to get.
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 16 / feb / 2016
	 * @param parameter The object containing all the parameters for the request.
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public TrailListResponse getAllTrails(CursorParameter parameter) {

		logger.debug("Getting user trails...");

		ArrayList<TrailDetails> result = new ArrayList<TrailDetails>();
		Query<RegisteredTrail> query = OfyService.ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class);
		
		query = CursorHelper.processCursor(query, parameter);
			
		QueryResultIterator<RegisteredTrail> it = query.iterator();
		while(it.hasNext()){
			RegisteredTrail t = it.next();
			result.add(new TrailDetails(t, t.getCreationDate()));
		}
		
		
		TrailListResponse theResult = new TrailListResponse(result, it.getCursor().toWebSafeString());

		// logger.debug("Getting all trails... "+result);

		return theResult;
	}

	/**
	 * This method returns the all the trails that have been registered in the competition of MapatonCDMX
	 * paginated by parameter.numberOfElements and parameter.cursor to define where to start and how many elements to get.
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 16 / feb / 2016
	 * @param parameter The object containing all the parameters for the request.
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public TrailListResponse getAllValidTrails(CursorParameter parameter) {

		logger.debug("Getting user trails...");

		ArrayList<TrailDetails> result = new ArrayList<TrailDetails>();
		Query<RegisteredTrail> query = OfyService.ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
				.filter("trailStatus", RegisteredTrailStatusEnum.VALID);
		
		query = CursorHelper.processCursor(query, parameter);
			
		QueryResultIterator<RegisteredTrail> it = query.iterator();
		while(it.hasNext()){
			RegisteredTrail t = it.next();
			result.add(new TrailDetails(t, t.getCreationDate()));
		}
		
		
		TrailListResponse theResult = new TrailListResponse(result, it.getCursor().toWebSafeString());

		// logger.debug("Getting all trails... "+result);

		return theResult;
	}

	/**
	 * This method returns the all the trails that have been registered in the competition of MapatonCDMX
	 * paginated by parameter.numberOfElements and parameter.cursor to define where to start and how many elements to get.
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 16 / feb / 2016
	 * @param parameter The object containing all the parameters for the request.
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public TrailListResponse getAllGtfsTrails(CursorParameter parameter) {

		logger.debug("Getting user trails...");

		ArrayList<TrailDetails> result = new ArrayList<TrailDetails>();
		Query<RegisteredTrail> query = OfyService.ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
				.filter("gtfsStatus", RegisteredTrail.GtfsStatus.VALID);
		
		query = CursorHelper.processCursor(query, parameter);
			
		QueryResultIterator<RegisteredTrail> it = query.iterator();
		while(it.hasNext()){
			RegisteredTrail t = it.next();
			result.add(new TrailDetails(t, t.getCreationDate()));
		}
		
		
		TrailListResponse theResult = new TrailListResponse(result, it.getCursor().toWebSafeString());

		// logger.debug("Getting all trails... "+result);

		return theResult;
	}
	

	/**
	 * This method returns the all the trails that have been registered in the competition of MapatonCDMX
	 * paginated by parameter.numberOfElements and parameter.cursor to define where to start and how many elements to get.
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 25 / feb / 2016
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public ArrayList<Long> getAllValidTrailsIds() {

		logger.debug("Getting user trails...");
		

		ArrayList<Long> result = new ArrayList<>();
		List<Key<RegisteredTrail>> trails = OfyService.ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
				.filter("trailStatus", RegisteredTrailStatusEnum.VALID).keys().list();
					
		for(Key<RegisteredTrail> t : trails){
			result.add(t.getId());
		}
		
		return result;
	}

	

	/**
	 * This method returns the all the trails that have been registered in the competition of MapatonCDMX
	 * paginated by parameter.numberOfElements and parameter.cursor to define where to start and how many elements to get.
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 25 / feb / 2016
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public ArrayList<Long> getAllValidGtfsTrailsIds() {

		logger.debug("Getting user trails...");
		

		ArrayList<Long> result = new ArrayList<>();
		List<Key<RegisteredTrail>> trails = OfyService.ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
				.filter("gtfsStatus", RegisteredTrail.GtfsStatus.VALID).keys().list();
					
		for(Key<RegisteredTrail> t : trails){
			result.add(t.getId());
		}
		
		
		
		return result;
	}
	
	/**
	 * This method returns the array of points of a trail
	 * @author JJMS (juanjo@krieger.mx)
	 * @since 16 / feb / 2016
	 * @version 1.0.0.0
	 * @param parameter
	 *            The id the id of the trail to search for.
	 * @return The arrayList of points of the trail
	 * @throws TrailNotFoundException
	 *             If the trail is not found
	 */
	public TrailPointsResult getRawPointsByTrail(TrailPointsRequestParameter parameter) throws TrailNotFoundException{
	
		logger.debug("Getting raw points for trail " + parameter);

		TrailPointsResult result = new TrailPointsResult();
		ArrayList<TrailPointWrapper> points = new ArrayList<TrailPointWrapper>();
		Query<RawTrailPoint> query = OfyService.ofy().load().type(RawTrailPoint.class)
			.filter("trailId", parameter.getTrailId())
			.order("pointData.timeStamp");
		
		query = CursorHelper.processCursor(query, parameter);


		QueryResultIterator<RawTrailPoint> pointsIterator = query.iterator();

		while(pointsIterator.hasNext()){
			RawTrailPoint point = pointsIterator.next();
			points.add(new TrailPointWrapper(point.getPointData().getLocation(),
				TimeStampUtils.dateToTimestamp(point.getPointData().getTimeStamp())));
		}

		Cursor cursor = pointsIterator.getCursor();

		result.setPoints(points);
		result.setCursor(cursor.toWebSafeString());

		logger.debug("Raw points of the trail: " + result);

		return result;
	}

	/**
	 * This method returns the snapped points for trail.
	 * @author Juanjo (juanjo@krieger.mx) 
	 * @since 16 / feb / 2016
	 * @param parameter The object contaiing all the parameters for the request.
	 * @return The list of points
	 * @throws TrailNotFoundException 
	 */
	public static TrailPointsResult getSnappedPointsByTrail(TrailPointsRequestParameter parameter) throws TrailNotFoundException{
		
		logger.debug("Getting snapped points for trail " + parameter);

		TrailPointsResult result = new TrailPointsResult();
		ArrayList<TrailPointWrapper> points = new ArrayList<TrailPointWrapper>();
		
		Query<SnappedTrailPoint> query = OfyService.ofy().load().type(SnappedTrailPoint.class)
			.filter("trailId", parameter.getTrailId())
			.order("position");
		
		

		query = CursorHelper.processCursor(query, parameter);
		
		QueryResultIterator<SnappedTrailPoint> pointsIterator = query.iterator();

		while(pointsIterator.hasNext()){
			SnappedTrailPoint point = pointsIterator.next();
			points.add(new TrailPointWrapper(point.getLocation(), point.getPosition()));
		}

		Cursor cursor = pointsIterator.getCursor();

		result.setPoints(points);
		result.setCursor(cursor.toWebSafeString());

		logger.debug("Number of Snapped points "+points.size());
		logger.debug("Snapped points of the trail: " + result);

		return result;
	}
	
	/**
	 * This method returns the all the points of a trail registered in the competition of MapatonCDMX
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @since 19 / feb / 2016
	 * @param trailId The object containing all the parameters for the request.
	 * @return The list of trails and the cursor to be able to get the next N number of elements.
	 * @throws TrailNotFoundException 
	 */
	public ArrayList<PointData> getTrailPoints(Long trailId){
		logger.debug("Getting points for trail " + trailId);
		List<SnappedTrailPoint> points = OfyService.ofy().load().type(SnappedTrailPoint.class)
			.filter("trailId", trailId).order("position").list(); //pointData.timeStamp
		ArrayList<PointData> result = new ArrayList<PointData>();
		for(SnappedTrailPoint point : points){
			result.add(new PointData(point.getLocation(), null)); //getPointData());
		}
		return result;
	}
	
	
	public ArrayList<NearTrails> trailsNearPoint(AreaWrapper area) {
		logger.debug("Getting stations in area " + area);

		Set<Key<SnappedTrailPoint>> keys1 = new HashSet<>(OfyService.ofy().cache(true).load().type(SnappedTrailPoint.class)
				.filter("location.latitude <=", area.getNorthEastCorner().getLatitude())
				.filter("location.latitude >=", area.getSouthWestCorner().getLatitude())
				.keys().list());

		Set<Key<SnappedTrailPoint>> keys2 = new HashSet<>(OfyService.ofy().cache(true).load().type(SnappedTrailPoint.class)
				.filter("location.longitude <=", area.getNorthEastCorner().getLongitude())
				.filter("location.longitude >=", area.getSouthWestCorner().getLongitude())
				.keys().list());
		
		logger.info("keys 1 size : "  + keys1.size());
		logger.info("keys 2 size : "  + keys2.size());
		
		
		keys1.retainAll(keys2);
		logger.info("keys 1 size : "  + keys1.size());

        HashSet<Key<RegisteredTrail>> trailsKeys = new HashSet<>();

        ArrayList<NearTrails> result = new ArrayList<NearTrails>();

        HashSet registeredKeys = new HashSet(OfyService.ofy().cache(true).load().type(RegisteredTrail.class).keys().list());

		for(Key<SnappedTrailPoint> pointKey : keys1){
            Key<RegisteredTrail> trailKey = Key.create(RegisteredTrail.class, Ref.create(pointKey).get().getTrailId());
            if(registeredKeys.contains(trailKey)){
                if(trailsKeys.add(trailKey)){
                    result.add(new NearTrails(Ref.create(trailKey).get()));
                }
            }
        }

		logger.debug("Stations found for the area " + result);
		return result;
	}

	public ArrayList<TrailDetails> getTrailsByStationName(SearchByKeywordParameter parameter) {

		logger.debug("Getting list of statios by keyword: ");

		List<Station> stations = OfyService.ofy().cache(false).consistency(Consistency.STRONG).load().type(Station.class)
				.filter("name", parameter.getKeyword())
				.list();
		
		ArrayList<TrailDetails> result = new ArrayList<>();
		int i = 0;
		for(Station s:stations){
			List<RegisteredTrail> trails = OfyService.ofy().cache(false)
					.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
					.filter("origin in", s.getPlatforms()).list();
			for(RegisteredTrail trail : trails){
				if(i < parameter.getNumberOfResults()){
					i++;
					result.add(new TrailDetails(trail, trail.getCreationDate()));
				}
			}
		}
		
		return result;
		
	}
	
	
}
