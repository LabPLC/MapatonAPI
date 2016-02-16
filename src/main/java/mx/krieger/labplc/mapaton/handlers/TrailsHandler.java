/**
 * 28 Jul 2015 - 18:46:26
 */
package mx.krieger.labplc.mapaton.handlers;

import static mx.krieger.labplc.mapaton.utils.OfyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.ReadPolicy.Consistency;
import com.googlecode.objectify.cmd.Query;

import mx.krieger.internal.apicommons.exceptions.APIException;
import mx.krieger.internal.commons.utils.dateandtime.utils.TimeStampUtils;
import mx.krieger.internal.commons.utils.location.beans.GPSLocation;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.labplc.mapaton.model.entities.GenericTrail;
import mx.krieger.labplc.mapaton.model.entities.RawTrailPoint;
import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;
import mx.krieger.labplc.mapaton.model.entities.SnappedTrailPoint;
import mx.krieger.labplc.mapaton.model.wrappers.CursorParameter;
import mx.krieger.labplc.mapaton.model.wrappers.PointData;
import mx.krieger.labplc.mapaton.model.wrappers.TrailDetails;
import mx.krieger.labplc.mapaton.model.wrappers.TrailPointWrapper;
import mx.krieger.labplc.mapaton.model.wrappers.TrailPointsRequestParameter;
import mx.krieger.labplc.mapaton.model.wrappers.TrailPointsResult;
import mx.krieger.labplc.mapaton.model.wrappers.UserTrailsResponse;
import mx.krieger.labplc.mapaton.utils.CursorHelper;

/**
 * This class hadnles all functionaily related to trials.
 * @author Juanjo (juanjo@krieger.mx)
 * @since 28 Jul 2015 - 18:46:26
 * @version v1.2.1.0
 */
public class TrailsHandler{
	
	private static Logger logger = new Logger(TrailsHandler.class);

	/**
	 * This method retrieves a trail with the specified id.
	 * @author JJMS (juanjo@krieger.mx)
	 * @since 11 Aug 2015 - 15:18:58
	 * @version 1.0.0.0
	 * @param trailId
	 *            The id of the trail.
	 * @return The trail object stored.
	 * @throws TrailNotFoundException
	 *             If the trail is not registered in the data storage.
	 */
	public static RegisteredTrail getTrailById(Long trailId)
		throws TrailNotFoundException{
		logger.debug("Finding mapped trail by id: " + trailId);
		RegisteredTrail trail = ofy().cache(false)
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
	 * @since 11 Aug 2015 - 15:18:58
	 * @version 1.0.0.0
	 * @param trailId
	 *            The id of the trail.
	 * @return The trail object stored.
	 * @throws TrailNotFoundException
	 *             If the trail is not registered in the data storage.
	 */
	public static GenericTrail getGenericTrailById(Long trailId)
		throws TrailNotFoundException{
		logger.debug("Finding mapped trail by id: " + trailId);
		GenericTrail trail = ofy().cache(false)
			.consistency(Consistency.STRONG).load().type(GenericTrail.class)
			.id(trailId).now();
		if(trail == null){
			throw new TrailNotFoundException(
				"Lo sentimos, no hemos podido encontrar el recorrido que solicitaste.");
		}
		logger.debug("Mapped trail found: " + trail);
		return trail;
	}


	public static ArrayList<GenericTrail> getSimilarTrails(
		GenericTrail trail, TrailType type){

		logger.debug(
			"Getting similar trails to " + trail+" filtered by "+type);
		
		ArrayList<GenericTrail> trails = new ArrayList<GenericTrail>();
		
		if(type==TrailType.GENERIC || type==TrailType.BOTH){
			List<GenericTrail> genericTrails = ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(GenericTrail.class)
				.filter("origin in", trail.getOrigin().get().getStation().getPlatforms())
				.filter("destination in ", trail.getDestination().get().getStation().getPlatforms())
				.list();
			trails.addAll(genericTrails);
		}
		
		if(type==TrailType.REGISTERED|| type==TrailType.BOTH){
			List<RegisteredTrail> registeredTrails = ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class)
				.filter("origin in", trail.getOrigin().get().getStation().getPlatforms())
				.filter("destination in ", trail.getDestination().get().getStation().getPlatforms())
				.list();
			trails.addAll(registeredTrails);
		}
		
		
		logger.debug("Trails similar to the trail: "
			+ Arrays.toString(trails.toArray()));

		return trails;
	}

	public UserTrailsResponse getAllTrails(CursorParameter parameter) {

		logger.debug("Getting user trails...");

		ArrayList<TrailDetails> result = new ArrayList<TrailDetails>();
		Query<RegisteredTrail> query = ofy().cache(false)
				.consistency(Consistency.STRONG).load().type(RegisteredTrail.class);
		
		query = CursorHelper.processCursor(query, parameter);
			
		QueryResultIterator<RegisteredTrail> it = query.iterator();
		while(it.hasNext()){
			RegisteredTrail t = it.next();
			result.add(new TrailDetails(t, t.getCreationDate()));
		}
		
		
		UserTrailsResponse theResult = new UserTrailsResponse(result, it.getCursor().toWebSafeString());

		// logger.debug("Getting all trails... "+result);

		return theResult;
	}
	

	public ArrayList<GPSLocation> getMostRecentPoints(Integer numberOfElements){
		logger.debug(
			"Getting the latest " + numberOfElements + " points ins trails");

		List<RawTrailPoint> points = ofy().cache(false)
			.consistency(Consistency.STRONG).load().type(RawTrailPoint.class)
			.limit(numberOfElements).list();

		ArrayList<GPSLocation> result = new ArrayList<GPSLocation>();

		for(RawTrailPoint point : points){
			result.add(point.getPointData().getLocation());
		}

		logger.debug("Most recent points: " + result);
		return result;
	}

	public ArrayList<PointData> getTrailPoints(Long trailId){
		logger.debug("Getting points for trail " + trailId);
		List<RawTrailPoint> points = ofy().load().type(RawTrailPoint.class)
			.filter("trailId", trailId).list();
		ArrayList<PointData> result = new ArrayList<PointData>();
		for(RawTrailPoint point : points){
			result.add(point.getPointData());
		}
		return result;
	}
	
	/**
	 * This method returns the array of points of a trail
	 * @author JJMS (juanjo@krieger.mx)
	 * @since 11 Aug 2015 - 17:03:52
	 * @version 1.0.0.0
	 * @param trail
	 *            The id the id of the trail to search for.
	 * @return The arrayList of points of the trail
	 * @throws TrailNotFoundException
	 *             If the trail is not found
	 */
	public TrailPointsResult getRawPointsByTrail(
		TrailPointsRequestParameter parameter) throws TrailNotFoundException{
	
		logger.debug("Getting raw points for trail " + parameter);

		TrailPointsResult result = new TrailPointsResult();
		ArrayList<TrailPointWrapper> points = new ArrayList<TrailPointWrapper>();
		Query<RawTrailPoint> query = ofy().load().type(RawTrailPoint.class)
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
		result.setEncodedCursor(cursor.toWebSafeString());

		logger.debug("Raw points of the trail: " + result);

		return result;
	}

	/**
	 * This method returns the snapped points for trail.
	 * @author Juanjo (juanjo@krieger.mx) 
	 * @since 18 Nov 2015 - 19:28:35
	 * @param parameter The object contaiing all the parameters for the request.
	 * @return The list of points
	 * @throws TrailNotFoundException 
	 * @throws APIException 
	 */
	public static TrailPointsResult getSnappedPointsByTrail(
		TrailPointsRequestParameter parameter) throws TrailNotFoundException, APIException{
		
		logger.debug("Getting snapped points for trail " + parameter);

		TrailPointsResult result = new TrailPointsResult();
		ArrayList<TrailPointWrapper> points = new ArrayList<TrailPointWrapper>();
		
		Query<SnappedTrailPoint> query = ofy().load().type(SnappedTrailPoint.class)
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
		result.setEncodedCursor(cursor.toWebSafeString());

		logger.debug("Number of Snapped points "+points.size());
		logger.debug("Snapped points of the trail: " + result);

		return result;
	}
	
	public enum TrailType{
		REGISTERED, GENERIC, BOTH;
	}
	
	
}
