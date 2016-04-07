/**
 * 28 Jul 2015 - 18:13:00
 */
package mx.krieger.labplc.mapaton.commons.enums;

import mx.krieger.internal.commons.utils.strings.SpecialCharacters;

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
 *          <td>28 Jul 2015 - 18:13:00</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td><ul>
 *          <li>creation</li>
 *          </ul></td>
 *          </tr>
 *          </tbody></table></p>
 */
public enum TransportType {
    AUTOBUS("Autob"+SpecialCharacters.u_ACUTE.unicode+"s"),
    MICROBUS("Microb"+SpecialCharacters.u_ACUTE.unicode+"s"), 
    VAGONETA_COMBI("Vagoneta/Combi");
    
    public String label;

    /**
     * This is the [default/overloaded/wrapper] constructor used to [create/wrap/unwrap] [an empty/a complete] instance of TransportType.java
     * @param label
     */
    private TransportType(String label) {
	this.label = label;
    }
    
}
