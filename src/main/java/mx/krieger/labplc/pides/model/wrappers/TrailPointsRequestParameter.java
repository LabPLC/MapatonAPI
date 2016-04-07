package mx.krieger.labplc.pides.model.wrappers;

/**
 * The Class TrailPointsRequestParameter.
 * This class is the parameter for getting the points of a registered trail;
 */
public class TrailPointsRequestParameter extends CursorParameter{

	private Long trailId;

	/**
	 * Instantiates a new trail points request parameter.
	 */
	public TrailPointsRequestParameter(){
		super();
	}

	/**
	 * Instantiates a new trail points request parameter.
	 *
	 * @param cursor the cursor
	 * @param trailId the trail id
	 * @param numberOfElements the number of elements
	 */
	public TrailPointsRequestParameter(
		String cursor, Long trailId, int numberOfElements){
		super();
		this.cursor = cursor;
		this.trailId = trailId;
		this.numberOfElements = numberOfElements;
	}

	/**
	 * Gets the cursor.
	 *
	 * @return the cursor
	 */
	public String getCursor(){
		return cursor;
	}

	/**
	 * Sets the cursor.
	 *
	 * @param cursor            the cursor to set
	 */
	public void setCursor(String cursor){
		this.cursor = cursor;
	}

	/**
	 * Gets the trail id.
	 *
	 * @return the trailId
	 */
	public Long getTrailId(){
		return trailId;
	}

	/**
	 * Sets the trail id.
	 *
	 * @param trailId            the trailId to set
	 */
	public void setTrailId(Long trailId){
		this.trailId = trailId;
	}

	/**
	 * Gets the number of elements.
	 *
	 * @return the numberOfElements
	 */
	public int getNumberOfElements(){
		return numberOfElements;
	}

	/**
	 * Sets the number of elements.
	 *
	 * @param numberOfElements            the numberOfElements to set
	 */
	public void setNumberOfElements(int numberOfElements){
		this.numberOfElements = numberOfElements;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TrailPointsRequestParameter [");
		if(cursor != null) {
			builder.append("cursor=");
			builder.append(cursor);
			builder.append(", ");
		}
		if(trailId != null) {
			builder.append("trailId=");
			builder.append(trailId);
			builder.append(", ");
		}
		builder.append("numberOfElements=");
		builder.append(numberOfElements);
		builder.append("]");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode() */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result
			+ ((trailId == null) ? 0 : trailId.hashCode());
		result = prime * result + numberOfElements;
		result = prime * result + ((cursor == null) ? 0 : cursor.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj){
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof TrailPointsRequestParameter)) {
			return false;
		}
		TrailPointsRequestParameter other = (TrailPointsRequestParameter) obj;
		if(trailId == null) {
			if(other.trailId != null) {
				return false;
			}
		}else if(!trailId.equals(other.trailId)) {
			return false;
		}
		if(numberOfElements != other.numberOfElements) {
			return false;
		}
		if(cursor == null) {
			if(other.cursor != null) {
				return false;
			}
		}else if(!cursor.equals(other.cursor)) {
			return false;
		}
		return true;
	}

}
