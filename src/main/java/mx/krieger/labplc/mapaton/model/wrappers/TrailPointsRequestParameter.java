package mx.krieger.labplc.mapaton.model.wrappers;

public class TrailPointsRequestParameter extends CursorParameter{

	private Long trailId;

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 */
	public TrailPointsRequestParameter(){
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * @param cursor
	 * @param trailId
	 * @param numberOfElements
	 */
	public TrailPointsRequestParameter(
		String cursor, Long trailId, int numberOfElements){
		super();
		this.cursor = cursor;
		this.trailId = trailId;
		this.numberOfElements = numberOfElements;
	}

	/**
	 * @return the cursor
	 */
	public String getCursor(){
		return cursor;
	}

	/**
	 * @param cursor
	 *            the cursor to set
	 */
	public void setCursor(String cursor){
		this.cursor = cursor;
	}

	/**
	 * @return the trailId
	 */
	public Long getTrailId(){
		return trailId;
	}

	/**
	 * @param trailId
	 *            the trailId to set
	 */
	public void setTrailId(Long trailId){
		this.trailId = trailId;
	}

	/**
	 * @return the numberOfElements
	 */
	public int getNumberOfElements(){
		return numberOfElements;
	}

	/**
	 * @param numberOfElements
	 *            the numberOfElements to set
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
