package mx.krieger.labplc.mapaton.model.wrappers;

public class CursorResponse{
	private String cursor;
	

	public CursorResponse() {
		super();
	}

	public CursorResponse(String cursor) {
		super();
		this.cursor = cursor;
	}

	/**
	 * @return the cursor
	 */
	public String getCursor() {
		return cursor;
	}

	/**
	 * @param cursor the cursor to set
	 */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	
	

}
