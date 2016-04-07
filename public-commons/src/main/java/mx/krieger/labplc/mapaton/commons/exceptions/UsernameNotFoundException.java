/**
 * 22 Jul 2015 - 21:51:13
 */
package mx.krieger.labplc.mapaton.commons.exceptions;

/**
 * <p>This exception is thrown when a the username is not found in the
 * database.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>22 Jul 2015 - 21:51:13</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class UsernameNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor used to create an empty exception.
	 */
	public UsernameNotFoundException(){}

	/**
	 * This is the message constructor used to specify the message to be shown
	 * as part of the exception.
	 * @param message
	 */
	public UsernameNotFoundException(String message){
		super(message);
	}

}
