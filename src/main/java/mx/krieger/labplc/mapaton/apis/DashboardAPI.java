package mx.krieger.labplc.mapaton.apis;

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
import mx.krieger.labplc.mapaton.model.wrappers.UserTrailsResponse;

/**
 * This class is used to manage the contents of the application through the
 * dashboard.
 * @author JJMS (juanjo@krieger.mx)
 * @since 24 Aug 2015 - 11:51:23
 * @version 1.0.0.0
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

	private Logger logger = new Logger(DashboardAPI.class);

	


	@ApiMethod(path = "getAllTrails", name = "getAllTrails", httpMethod = HttpMethod.POST)
	public UserTrailsResponse getAllTrails(CursorParameter parameter) throws TrailNotFoundException{
		logger.debug("Getting all trails for all user ");

		UserTrailsResponse result = new TrailsHandler().getAllTrails(parameter);
		logger.debug("All mapped trails ");

		return result;
	}


	@ApiMethod(name = "getTrailDetails", httpMethod = HttpMethod.POST)
	public TrailDetails getTrailDetails(@Named("trailId") Long trailId)
		throws TrailNotFoundException{
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
	 * @author Juanjo (juanjo@krieger.mx) 
	 * @since 18 Nov 2015 - 19:25:56
	 * @param parameter
	 * @return
	 * @throws TrailNotFoundException
	 * @throws APIException 
	 */
	@ApiMethod(path = "getTrailSnappedPoints", name = "getTrailSnappedPoints", httpMethod = HttpMethod.POST)
	public TrailPointsResult getTrailSnappedPoints(TrailPointsRequestParameter parameter) throws TrailNotFoundException, APIException {
		logger.debug("Getting snapped points for a trail with parameters: " + parameter);
		TrailPointsResult result = TrailsHandler.getSnappedPointsByTrail(parameter);
		logger.debug("Points of the trail finished ");
		return result;
	}
	
	/**
	 * This method gets the list of raw points for a trail.
	 * @author Juanjo (juanjo@krieger.mx) 
	 * @since 11 Aug 2015 - 17:03:52
	 * @param parameter
	 * @return
	 * @throws TrailNotFoundException
	 */
	@ApiMethod(path = "getTrailRawPoints", name = "getTrailRawPoints", httpMethod = HttpMethod.POST)
	public TrailPointsResult getTrailRawPoints(TrailPointsRequestParameter parameter) throws TrailNotFoundException {
		logger.debug("Getting raw points for a trail with parameters: " + parameter);
		TrailPointsResult result = new TrailsHandler().getRawPointsByTrail(parameter);
		logger.debug("Points of the trail finished ");
		return result;
	}

}
