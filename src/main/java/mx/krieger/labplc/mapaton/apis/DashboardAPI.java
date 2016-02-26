package mx.krieger.labplc.mapaton.apis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;

import mx.krieger.internal.apicommons.exceptions.APIException;
import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.labplc.mapaton.handlers.TrailsHandler;
import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;
import mx.krieger.labplc.mapaton.model.wrappers.CursorParameter;
import mx.krieger.labplc.mapaton.model.wrappers.TrailDetails;
import mx.krieger.labplc.mapaton.model.wrappers.TrailPointsRequestParameter;
import mx.krieger.labplc.mapaton.model.wrappers.TrailPointsResult;
import mx.krieger.labplc.mapaton.tasks.GtfsGenerationTask;
import mx.krieger.labplc.mapaton.model.wrappers.TrailListResponse;

/**
 * This class is used to manage the contents of the application through the
 * dashboard.
 *
 * @author JJMS (juanjo@krieger.mx)
 * @version 1.0.0.0
 * @since 16 / feb / 2016
 */
@Api(
	name = "dashboardAPI",
	canonicalName = "dashboardAPI",
	title = "Dashboard API",
	description = "This API exposes the services required by the mapaton dashboard of mapaton",
	authLevel = AuthLevel.NONE,
	namespace = @ApiNamespace(
		ownerDomain = "labplc.krieger.mx",
		ownerName = "krieger",
		packagePath = "clients") )
public class DashboardAPI{

	/** The logger. */
	private Logger logger = new Logger(DashboardAPI.class);

	


	/**
	 * Gets a number of trails from the datastore, can be paginated using a cursor (send empty to start from the beginning)
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param parameter the cursor to know where to start from, and number of elements needed
	 * @return the number of trails from the cursor sent
	 * @since 16 / feb / 2016
	 */
	@ApiMethod(path = "getAllTrails", name = "getAllTrails", httpMethod = HttpMethod.POST)
	public TrailListResponse getAllTrails(CursorParameter parameter){
		logger.debug("Getting all trails for all user ");

		TrailListResponse result = new TrailsHandler().getAllTrails(parameter);
		logger.debug("All mapped trails ");

		return result;
	}


	/**
	 * Gets a specific trail details.
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param trailId the trail id
	 * @return the trail details
	 * @throws TrailNotFoundException the trail not found exception
	 * @since 16 / feb / 2016
	 */
	@ApiMethod(name = "getTrailDetails", httpMethod = HttpMethod.POST)
	public TrailDetails getTrailDetails(@Named("trailId") Long trailId) throws TrailNotFoundException{
		logger.debug("Getting details for trail " + trailId);
		RegisteredTrail trail = TrailsHandler.getTrailById(trailId);
		TrailDetails result = new TrailDetails(trail, trail.getCreationDate());
		result.setTotalMeters(trail.getTotalMeters());
		result.setTotalMinutes(trail.getTotalMinutes());
		logger.debug("trail details finished " );
		return result;
	}
	
	/**
	 * This method gets the list of snapped points for a trail.
	 *
	 * @author Juanjo (juanjo@krieger.mx)
	 * @param parameter the parameter which contains the trail Id, a cursor and the number of elements for pagination
	 * @return the trail snapped points by the google SnapToRoad API
	 * @throws TrailNotFoundException the trail not found exception
	 * @since 18 / Nov / 2015
	 */
	@ApiMethod(path = "getTrailSnappedPoints", name = "getTrailSnappedPoints", httpMethod = HttpMethod.POST)
	public TrailPointsResult getTrailSnappedPoints(TrailPointsRequestParameter parameter) throws TrailNotFoundException {
		logger.debug("Getting snapped points for a trail with parameters: " + parameter);
		TrailPointsResult result = TrailsHandler.getSnappedPointsByTrail(parameter);
		logger.debug("Points of the trail finished ");
		return result;
	}
	
	/**
	 * This method gets the list of raw points for a trail.
	 *
	 * @author Juanjo (juanjo@krieger.mx)
	 * @param parameter the parameter which contains the trail Id, a cursor and the number of elements for pagination
	 * @return the trail raw points as the users registered them
	 * @throws TrailNotFoundException the trail not found exception
	 * @since 11 / Aug / 2015
	 */
	@ApiMethod(path = "getTrailRawPoints", name = "getTrailRawPoints", httpMethod = HttpMethod.POST)
	public TrailPointsResult getTrailRawPoints(TrailPointsRequestParameter parameter) throws TrailNotFoundException {
		logger.debug("Getting raw points for a trail with parameters: " + parameter);
		TrailPointsResult result = new TrailsHandler().getRawPointsByTrail(parameter);
		logger.debug("Points of the trail finished ");
		return result;
	}


	/**
	 * Register the gtfs creation task.
	 *
	 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
	 * @param password the simple password to avoid excess of petitions
	 * @param trailIds the trail ids to be added to the GTFS
	 * @since 19 / feb / 2016
	 */
	@ApiMethod(name = "registerGtfsTask", path = "registerGtfsTask", httpMethod = HttpMethod.POST)
	public void registerGtfsTask(@Named("password") String password, @Named("trailIds") String trailIds) throws TrailNotFoundException{
		logger.debug("registering gtfs task");
		if(password.equals("MAPATON")){
			HashMap<String, String> params = new HashMap<>();
			params.put(GtfsGenerationTask.Params.trailIds.name(), trailIds);
			
			new GtfsGenerationTask().enqueue(params);
		}
		
		logger.debug("registration finished");
	}
	@ApiMethod(name = "registerGtfsFullTask", path = "registerGtfsFullTask", httpMethod = HttpMethod.POST)
	public void registerGtfsFullTask(@Named("password") String password) throws TrailNotFoundException{
		logger.debug("registering gtfs task");
		if(password.equals("MAPATON")){
			ArrayList<Long> ids = new TrailsHandler().getAllValidTrailsIds();
			HashMap<String, String> params = new HashMap<>();
			String trailIds = Arrays.toString(ids.toArray()).replace("[", "").replace("]", "").replace(" ", "");
			params.put(GtfsGenerationTask.Params.trailIds.name(), trailIds);
			
			new GtfsGenerationTask().enqueue(params);
		}
		
		logger.debug("registration finished");
	}
	

}
