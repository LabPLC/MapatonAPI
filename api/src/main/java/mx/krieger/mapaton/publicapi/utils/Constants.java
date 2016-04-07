package mx.krieger.mapaton.publicapi.utils;

/**
 * <p>
 * This class contains the client IDs and scopes for allowed clients consuming
 * your API..
 * </p>
 * 
 * @author JJMS(juanjo@krieger.mx)
 */
public class Constants {
	public static final String WEB_CLIENT_ID = "replace this with your web client ID";
	public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
	public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
	public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;

	public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

	public static final String MODULE_NAME = "public-api-dot";
	public static final String APPPLICATION_NAME = MODULE_NAME+"mapaton-public";
	public static final String APPLICATION_BASE_URL = APPPLICATION_NAME + ".appspot.com";
	public static final String APPLICATION_URL = "http://" + APPLICATION_BASE_URL;

	public static final String DEFAULT_MAPATON_NOTIFICATION_NAME = "Mapatón";
	public static final String DEFAULT_MAPATON_IMAGE_URL = "";
	public static final long DEFAULT_DASHBOARD_USER_ID = 1L;
	
}
