/**
 * 29 Jul 2015 - 09:56:57
 */
package mx.krieger.pides.mapaton.model.wrappers;

/**
 * <p>
 * This class ... .
 * </p>
 * 
 * @author JJMS(juanjo@krieger.mx)
 * @version 1.0.0.0
 *          <p>
 *          history:<br>
 *          <table border="1">
 *          <thead>
 *          <tr>
 *          <th width="15%">Date</th>
 *          <th width="30%">Author</th>
 *          <th width="55%">Comment</th>
 *          </tr>
 *          </thead><tbody>
 *          <tr>
 *          <td>29 Jul 2015 - 09:56:57</td>
 *          <td>JJMS (juanjo@krieger.mx)</td>
 *          <td>
 *          <ul>
 *          <li>creation</li>
 *          </ul>
 *          </td>
 *          </tr>
 *          </tbody>
 *          </table>
 *          </p>
 */
public class SearchByKeywordParameter {
	String keyword;
	int numberOfResults;

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 */
	public SearchByKeywordParameter() {
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * 
	 * @param keyword
	 * @param numberOfResults
	 */
	public SearchByKeywordParameter(String keyword, int numberOfResults) {
		super();
		this.keyword = keyword;
		this.numberOfResults = numberOfResults;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the numberOfResults
	 */
	public int getNumberOfResults() {
		return numberOfResults;
	}

	/**
	 * @param numberOfResults
	 *            the numberOfResults to set
	 */
	public void setNumberOfResults(int numberOfResults) {
		this.numberOfResults = numberOfResults;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StationKeyboard [");
		if (keyword != null) {
			builder.append("keyword=");
			builder.append(keyword);
			builder.append(", ");
		}
		builder.append("numberOfResults=");
		builder.append(numberOfResults);
		builder.append("]");
		return builder.toString();
	}

}
