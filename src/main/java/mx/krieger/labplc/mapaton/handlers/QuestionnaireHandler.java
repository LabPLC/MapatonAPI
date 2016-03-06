package mx.krieger.labplc.mapaton.handlers;

import static mx.krieger.labplc.mapaton.utils.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

import mx.krieger.labplc.mapaton.model.entities.Questionnaire;
import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;
import mx.krieger.labplc.mapaton.model.wrappers.QuestionnaireWrapper;
import mx.krieger.labplc.mapaton.model.wrappers.RouteStats;

public class QuestionnaireHandler {

	public void register(QuestionnaireWrapper param){
		ofy().save().entity(new Questionnaire(param));
	}
	public QuestionnaireWrapper get(long trailId){
		return new QuestionnaireWrapper(ofy().load().type(Questionnaire.class).id(trailId).now());
	}
	public RouteStats getStats(long trailId){
		RouteStats stats= new RouteStats();
		List<Questionnaire> qs = ofy().load().type(Questionnaire.class).filter("trail", 
				Ref.create(Key.create(RegisteredTrail.class, trailId))).list();
		for(Questionnaire q : qs){
			stats.addCleanness(q.getCleanness());
			stats.addFullness(q.getFullness());
			stats.addRating(q.getRating());
			stats.addSecurity(q.getSecurity());
			stats.addValue(q.getValue());
		}
		stats.normalize(qs.size());
		return stats;
	}
	public List<QuestionnaireWrapper> getAll(boolean asc){
		List<QuestionnaireWrapper> questionnaries = new ArrayList<>();
		List<Questionnaire> qs = ofy().load().type(Questionnaire.class).order((asc?"":"-") + "rating").list();
		for(Questionnaire q : qs){
			questionnaries.add(new QuestionnaireWrapper(q));
		}
		
		return questionnaries;
	}
}
