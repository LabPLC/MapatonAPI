package mx.krieger.labplc.mapaton.apis;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;

import mx.krieger.labplc.mapaton.handlers.QuestionnaireHandler;
import mx.krieger.labplc.mapaton.model.wrappers.QuestionnaireWrapper;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStats;

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
	public RouteStats getStats(@Named("id") Long id){
		return new QuestionnaireHandler().getStats(id);

	}
	public List<QuestionnaireWrapper> getAllQuestionnaires(@Named("sortOrder") @Nullable String sortOrder){
		boolean sort = sortOrder!=null;
		sort = sort?!sortOrder.equals("desc"):sort;
		return new QuestionnaireHandler().getAll(sort);
	}
}
