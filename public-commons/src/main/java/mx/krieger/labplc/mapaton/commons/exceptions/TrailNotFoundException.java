/**
 * 23 Jul 2015 - 23:00:22
 */
package mx.krieger.labplc.mapaton.commons.exceptions;

/**
 * <p>This exception is thrown when the action in the access services is not
 * valid.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>23 Jul 2015 - 23:00:22</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class TrailNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor used to create an empty exception.
	 */
	public TrailNotFoundException(){}

	/**
	 * This is the message constructor used to specify the message to be shown
	 * as part of the exception.
	 * @param message
	 */
	public TrailNotFoundException(String message){
		super(message);
	}

}
