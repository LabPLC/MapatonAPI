package mx.krieger.labplc.mapaton.commons.beans;

/**
 * <p>This class extends the Date class used in the commons of Krieger.</p>
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>history:<br>
 *          <table border="1">
 *          <thead><tr>
 *          <th width="15%">Date</th><th width="30%">Author</th><th
 *          width="55%">Comment</th>
 *          </tr></thead><tbody>
 *          <tr>
 *          <td>30 Jul 2015 - 17:25:48</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public class Date
	extends mx.krieger.internal.commons.utils.dateandtime.beans.Date{

	/**
	 * This is the default constructor used to create an empty instance of the
	 * super class Date.
	 */
	public Date(){
		super();
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * super class Date.
	 * @param day
	 * @param month
	 * @param year
	 */
	public Date(int day, int month, int year){
		super(day, month, year);
	}

}
