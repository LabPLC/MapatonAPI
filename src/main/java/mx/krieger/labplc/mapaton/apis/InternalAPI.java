package mx.krieger.labplc.mapaton.apis;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;

import mx.krieger.labplc.mapaton.handlers.QuestionnaireHandler;
import mx.krieger.labplc.mapaton.model.wrappers.QuestionnaireWrapper;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsParameter;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsResponse;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsWrapper;

@Api(
		name = "internalAPI",
		canonicalName = "internalAPI",
		title = "Internal API",
		description = "This API exposes the services required by the mapaton dashboard of mapaton",
		authLevel = AuthLevel.NONE,
		namespace = @ApiNamespace(
			ownerDomain = "labplc.krieger.mx",
			ownerName = "krieger",
			packagePath = "clients") )

public class InternalAPI {
	
	@ApiMethod(path = "registerQuestionnaire", name = "registerQuestionnaire", httpMethod = HttpMethod.POST)
	public void registerQuestionnaire(QuestionnaireWrapper parameter){
		new QuestionnaireHandler().register(parameter);

	}
	@ApiMethod(path = "getQuestionnaire", name = "getQuestionnaire", httpMethod = HttpMethod.POST)
	public QuestionnaireWrapper getQuestionnaire(@Named("id") Long id){
		return new QuestionnaireHandler().get(id);

	}
	@ApiMethod(path = "getStats", name = "getStats", httpMethod = HttpMethod.POST)
	public RouteStatsWrapper getStats(@Named("trailId") Long trailId){
		return new QuestionnaireHandler().getStats(trailId);

	}
	
	@ApiMethod(path = "getAllStats", name = "getAllStats", httpMethod = HttpMethod.POST)
	public RouteStatsResponse getAllStats(RouteStatsParameter parameter){
		return new QuestionnaireHandler().getAllStats(parameter);
	}
}
