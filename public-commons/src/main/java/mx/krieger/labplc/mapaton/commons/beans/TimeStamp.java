/**
 * 30 Jul 2015 - 17:23:00
 */
package mx.krieger.labplc.mapaton.commons.beans;

import mx.krieger.internal.commons.utils.dateandtime.beans.Date;
import mx.krieger.internal.commons.utils.dateandtime.beans.Time;

/**
 * <p>This class extends the TimeStamp used in the commons of kriegers.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>30 Jul 2015 - 17:23:00</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class TimeStamp
	extends mx.krieger.internal.commons.utils.dateandtime.beans.TimeStamp{

	/**
	 * This is the default constructor used to create an empty instance of the
	 * super class TimeStamp.
	 */
	public TimeStamp(){
		super();
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * super class TimeStamp.
	 * @param time
	 * @param date
	 */
	public TimeStamp(Time time, Date date){
		super(time, date);
	}

}
