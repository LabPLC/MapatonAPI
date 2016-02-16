package mx.krieger.labplc.mapaton.model.wrappers;

/**
 * The Class CursorResponse.
 * This is a super class to be able to send a cursor as a response in any given response that is paginated
 */
public class CursorResponse{
	protected String cursor;
	

	/**
	 * Instantiates a new cursor response.
	 */
	public CursorResponse() {
		super();
	}

	/**
	 * Instantiates a new cursor response.
	 *
	 * @param cursor the cursor
	 */
	public CursorResponse(String cursor) {
		super();
		this.cursor = cursor;
	}

	/**
	 * Gets the cursor.
	 *
	 * @return the cursor for the next page of elements
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

	
	

}
