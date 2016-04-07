package mx.krieger.mapaton.publicutils.utils;

import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.cmd.Query;

import mx.krieger.mapaton.publicutils.wrappers.CursorParameter;

/**
 * This class is used to help manage the cursor requests
 *
 * @author Rodrigo Cabrera (rodrigo.cp@krieger.mx)
 * @version 1.0.0.0
 * @since 16 / feb / 2016
 */
public class CursorHelper {

	/**
	 * Process cursor.
	 * process a cursor request by adding it to the original query and 
	 * limiting the number of elements by the requested parameter
	 *
	 * @param <T> the generic type, typically an Entity class 
	 * @param query the query that will be processed
	 * @param parameter the parameter that contains the cursor and number of elements
	 * @return the query
	 */
	public static<T> Query<T> processCursor(Query<T> query, CursorParameter parameter){
		query = query.limit(parameter.getNumberOfElements());
		if(parameter.getCursor() != null && !parameter.getCursor().isEmpty()){
			query = query.startAt(Cursor.fromWebSafeString(parameter.getCursor()));
		}
		
		return query;
	}
}
