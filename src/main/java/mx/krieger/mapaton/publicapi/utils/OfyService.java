/**
 * 23 Jul 2015 - 17:08:01
 */
package mx.krieger.mapaton.publicapi.utils;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import mx.krieger.mapaton.publicapi.model.entities.Branch;
import mx.krieger.mapaton.publicapi.model.entities.GenericTrail;
import mx.krieger.mapaton.publicapi.model.entities.Platform;
import mx.krieger.mapaton.publicapi.model.entities.Questionnaire;
import mx.krieger.mapaton.publicapi.model.entities.RawTrailPoint;
import mx.krieger.mapaton.publicapi.model.entities.RegisteredTrail;
import mx.krieger.mapaton.publicapi.model.entities.Route;
import mx.krieger.mapaton.publicapi.model.entities.RouteStats;
import mx.krieger.mapaton.publicapi.model.entities.SnappedTrailPoint;
import mx.krieger.mapaton.publicapi.model.entities.Station;

/**
 * This class is used to manage entity classes within objectify and Google Data
 * Storage.
 * 
 * @author JJMS (juanjo@krieger.mx)
 * @since 19 Aug 2015 - 00:54:58
 * @version 1.0.0.0
 */
public class OfyService {
	static {
		factory().register(GenericTrail.class);
		factory().register(SnappedTrailPoint.class);
		factory().register(RegisteredTrail.class);
		factory().register(Branch.class);
		factory().register(RawTrailPoint.class);
		factory().register(Platform.class);
		factory().register(Route.class);
		factory().register(Station.class);
		factory().register(Questionnaire.class);
		factory().register(RouteStats.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}

}
