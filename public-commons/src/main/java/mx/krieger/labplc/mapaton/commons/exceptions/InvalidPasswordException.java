/**
 * 22 Jul 2015 - 21:53:55
 */
package mx.krieger.labplc.mapaton.commons.exceptions;

/**
 * <p>This exception is thrown when the password of a registered user is
 * invalid.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>22 Jul 2015 - 21:53:55</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class InvalidPasswordException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor used to create an empty exception.
	 */
	public InvalidPasswordException(){}

	/**
	 * This is the message constructor used to specify the message to be shown
	 * as part of the exception.
	 * @param message
	 */
	public InvalidPasswordException(String message){
		super(message);
	}

}
