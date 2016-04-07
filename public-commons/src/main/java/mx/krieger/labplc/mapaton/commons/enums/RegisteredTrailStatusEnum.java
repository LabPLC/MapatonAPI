package mx.krieger.labplc.mapaton.commons.enums;

public class RegisteredTrailStatusEnum {
	public static final int VALID = 0;
	public static final int INVALID_TOO_SHORT = 1;
	public static final int INVALID_OUTSIDE_TIMEFRAME = 2;
	public static final int INVALID_WRONG_DATA = 3;
	

	private static final String[] baseStrings = new String[]{
			"válido",
			"inválido (distancia o tiempo muy corto)",
			"inválido (fue registrado fuera de tiempo)",
			"inválido (contiene datos incorrectos)"
	};
	
	public static String getString(int status){
		return baseStrings[status];
	}
	
}
