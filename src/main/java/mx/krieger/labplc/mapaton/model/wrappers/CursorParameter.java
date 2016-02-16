package mx.krieger.labplc.mapaton.model.wrappers;

public class CursorParameter {
	protected String cursor;
	protected int numberOfElements = 10;

	
	
	public CursorParameter() {
		super();
	}

	public CursorParameter(String cursor, int numberOfElements) {
		super();
		this.cursor = cursor;
		this.numberOfElements = numberOfElements;
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

	/**
	 * @return the numberOfElements
	 */
	public int getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * @param numberOfElements the numberOfElements to set
	 */
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	
	
	

}
