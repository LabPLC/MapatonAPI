package mx.krieger.pides.mapaton.model.wrappers;

/**
 * The Class CursorParameter.
 * this is used to have a common super class that contains the elements needed when receiving
 * a request that should be paged. 
 */
public class CursorParameter {
	protected String cursor;
	protected int numberOfElements = 10;

	
	
	/**
	 * Instantiates a new cursor parameter.
	 */
	public CursorParameter() {
		super();
	}

	/**
	 * Instantiates a new cursor parameter.
	 *
	 * @param cursor the cursor
	 * @param numberOfElements the number of elements
	 */
	public CursorParameter(String cursor, int numberOfElements) {
		super();
		this.cursor = cursor;
		this.numberOfElements = numberOfElements;
	}

	/**
	 * Gets the cursor, this can be empty to start from the beginning, or a cursor sent to the client to start from a position.
	 *
	 * @return the cursor for pagination
	 */
	public String getCursor() {
		return cursor;
	}

	/**
	 * Sets the cursor.
	 *
	 * @param cursor the cursor to set
	 */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	/**
	 * Gets the number of elements.
	 *
	 * @return the numberOfElements
	 */
	public int getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * Sets the number of elements.
	 *
	 * @param numberOfElements the numberOfElements to set
	 */
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	
	
	

}
