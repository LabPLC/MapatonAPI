package mx.krieger.labplc.mapaton.utils;

/**
 * <p>
 * This class contains the client IDs and scopes for allowed clients consuming
 * your API..
 * </p>
 * 
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>
 *          history:<br>
 *          <table border="1">
 *          <thead>
 *          <tr>
 *          <th width="15%">Date</th>
 *          <th width="30%">Author</th>
 *          <th width="55%">Comment</th>
 *          </tr>
 *          </thead><tbody>
 *          <tr>
 *          <td>22 Jul 2015 - 20:29:01</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td>
 *          <ul>
 *          <li>creation</li>
 *          </ul>
 *          </td>
 *          </tr>
 *          </tbody>
 *          </table>
 *          </p>
 */
public class Constants {
	public static final String WEB_CLIENT_ID = "replace this with your web client ID";
	public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
	public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
	public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;

	public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

	public static final String APPPLICATION_NAME = "mapaton-public";
	public static final String APPLICATION_BASE_URL = APPPLICATION_NAME + ".appspot.com";
	public static final String APPLICATION_URL = "http://" + APPLICATION_BASE_URL;

	public static final String DEFAULT_MAPATON_NOTIFICATION_NAME = "Mapatón";
	public static final String DEFAULT_MAPATON_IMAGE_URL = "";
	public static final long DEFAULT_DASHBOARD_USER_ID = 1L;
	
}
