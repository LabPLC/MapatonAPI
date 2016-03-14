package mx.krieger.labplc.mapaton.handlers;

import static mx.krieger.labplc.mapaton.utils.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import mx.krieger.internal.commons.utils.logging.Logger;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.labplc.mapaton.model.entities.Questionnaire;
import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;
import mx.krieger.labplc.mapaton.model.entities.RouteStats;
import mx.krieger.labplc.mapaton.model.wrappers.QuestionnaireWrapper;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsParameter;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsResponse;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStatsWrapper;
import mx.krieger.labplc.mapaton.utils.CursorHelper;

public class QuestionnaireHandler {
	private Logger logger = new Logger(QuestionnaireHandler.class);
	
	public void register(QuestionnaireWrapper param) throws TrailNotFoundException{
		RegisteredTrail trail = TrailsHandler.getTrailById(param.getTrailId());
		Key<Questionnaire> key = ofy().save().entity(new Questionnaire(param, Ref.create(trail))).now();
		Questionnaire q = ofy().load().key(key).now();
		RouteStats stats = ofy().load().type(RouteStats.class).filter("trail", q.getTrail()).first().now();
		
		if(stats == null) {
			stats = new RouteStats();
			stats.setTrail(q.getTrail());

		}
		stats.addRating(q.getRating());
		
		ofy().save().entity(stats).now();
	}
	public QuestionnaireWrapper get(long trailId){
		return new QuestionnaireWrapper(ofy().load().type(Questionnaire.class).id(trailId).now());
	}
	public RouteStatsWrapper getStats(long trailId){
		logger.debug("getting stats for " + trailId);
		
		RouteStats stats=  ofy().load().type(RouteStats.class).filter("trail", 
				Ref.create(Key.create(RegisteredTrail.class, trailId))).first().now();
		RouteStatsWrapper wrapper = new RouteStatsWrapper();
		if(stats == null) {
			return wrapper;
		}
		wrapper.setRating(stats.getTotalRating()/stats.getTotalElements());
		wrapper.setOriginStation(stats.getTrail().get().getOrigin().get().getStation().getName());
		wrapper.setDestinyStation(stats.getTrail().get().getDestination().get().getStation().getName());
		return wrapper;
	}
	public RouteStatsResponse getAllStats(RouteStatsParameter parameter){
		logger.debug("getting all stats");
		//logger.info(ofy().load().type(RouteStats.class).count() + " number oif stats");
		List<RouteStatsWrapper> result = new ArrayList<>();
		Query<RouteStats> query = ofy().load().type(RouteStats.class).order((parameter.isDescending() ?"-":"") + "totalRating");
		query = CursorHelper.processCursor(query, parameter);

		RouteStatsResponse response = new RouteStatsResponse();
		
			
		QueryResultIterator<RouteStats> it = query.iterator();
		while(it.hasNext()){
			RouteStats stats = it.next();
			RouteStatsWrapper wrapper = new RouteStatsWrapper();
			wrapper.setRating(stats.getTotalRating()/stats.getTotalElements());
			wrapper.setOriginStation(stats.getTrail().get().getOrigin().get().getStation().getName());
			wrapper.setDestinyStation(stats.getTrail().get().getDestination().get().getStation().getName());
			wrapper.setId(stats.getTrail().get().getId());

			result.add(wrapper);	
			logger.debug("adding stat");
		}
		response.setCursor(it.getCursor().toWebSafeString());
		response.setItems(result);
		return response;
	}
}
