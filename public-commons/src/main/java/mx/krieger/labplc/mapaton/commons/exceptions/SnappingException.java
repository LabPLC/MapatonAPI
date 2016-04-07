package mx.krieger.labplc.mapaton.commons.exceptions;


/**
 * This exception is thrown if there is any problem while trying to snap the points of a trail to a road.
 * @author Juanjo (juanjo@krieger.mx) 
 * @since 16 Nov 2015 - 12:03:16
 * @version v0.0.0.0
 */
public class SnappingException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor used to create an empty exception.
	 */
	public SnappingException(){}

	/**
	 * This is the message constructor used to specify the message to be shown
	 * as part of the exception.
	 * @param message
	 */
	public SnappingException(String message){
		super(message);
	}

}
