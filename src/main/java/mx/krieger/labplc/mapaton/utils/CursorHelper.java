package mx.krieger.labplc.mapaton.utils;

import com.google.appengine.api.datastore.Cursor;
import com.googlecode.objectify.cmd.Query;

import mx.krieger.labplc.mapaton.model.wrappers.CursorParameter;

public class CursorHelper {

	public static<T> Query<T> processCursor(Query<T> query, CursorParameter parameter){
		query = query.limit(parameter.getNumberOfElements());
		if(parameter.getCursor() != null && !parameter.getCursor().isEmpty()){
			query = query.startAt(Cursor.fromWebSafeString(parameter.getCursor()));
		}
		
		return query;
	}
}
