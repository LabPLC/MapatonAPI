/**
 * 22 Jul 2015 - 21:52:43
 */
package mx.krieger.labplc.mapaton.commons.exceptions;

/**
 * <p>This exception it thrown when some of the data sent does not contain the
 * appropriated format.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>22 Jul 2015 - 21:52:43</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class InvalidDataFormatException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor used to create an empty exception.
	 */
	public InvalidDataFormatException(){}

	/**
	 * This is the message constructor used to specify the message to be shown
	 * as part of the exception.
	 * @param message
	 */
	public InvalidDataFormatException(String message){
		super(message);
	}

}
