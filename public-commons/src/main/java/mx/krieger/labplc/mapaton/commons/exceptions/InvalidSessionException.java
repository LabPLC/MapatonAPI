package mx.krieger.labplc.mapaton.commons.exceptions;

public class InvalidSessionException extends Exception{

	private static final long serialVersionUID = 1L;

	public InvalidSessionException() {
		super();
	}

	public InvalidSessionException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidSessionException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidSessionException(String message) {
		super(message);
	}

	public InvalidSessionException(Throwable cause) {
		super(cause);
	}

}
