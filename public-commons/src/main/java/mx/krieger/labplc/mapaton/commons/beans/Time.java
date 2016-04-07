/**
 * 30 Jul 2015 - 17:26:37
 */
package mx.krieger.labplc.mapaton.commons.beans;

/**
 * <p>This class ... .</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>30 Jul 2015 - 17:26:37</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class Time
	extends mx.krieger.internal.commons.utils.dateandtime.beans.Time{

	/**
	 * This is the default constructor used to create an empty instance of the
	 * super class Time.
	 */
	public Time(){
		super();
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * super class Time.
	 * @param hour
	 * @param minute
	 * @param second
	 */
	public Time(int hour, int minute, int second){
		super(hour, minute, second);
	}

}
