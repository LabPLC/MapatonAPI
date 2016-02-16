package mx.krieger.labplc.mapaton.model.wrappers;

import java.util.ArrayList;

public class TrailPointsResult{

	private String encodedCursor;
	private ArrayList<TrailPointWrapper> points;

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 */
	public TrailPointsResult(){
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * @param encodedCursor
	 * @param points
	 */
	public TrailPointsResult(String encodedCursor, ArrayList<TrailPointWrapper> points){
		super();
		this.encodedCursor = encodedCursor;
		this.points = points;
	}

	/**
	 * @return the encodedCursor
	 */
	public String getEncodedCursor(){
		return encodedCursor;
	}

	/**
	 * @param encodedCursor
	 *            the encodedCursor to set
	 */
	public void setEncodedCursor(String encodedCursor){
		this.encodedCursor = encodedCursor;
	}

	/**
	 * @return the points
	 */
	public ArrayList<TrailPointWrapper> getPoints(){
		return points;
	}

	/**
	 * @param points
	 *            the points to set
	 */
	public void setPoints(ArrayList<TrailPointWrapper> points){
		this.points = points;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("TrailPointsResult [");
		if(encodedCursor != null) {
			builder.append("encodedCursor=");
			builder.append(encodedCursor);
			builder.append(", ");
		}
		if(points != null) {
			builder.append("points=");
			builder.append(points.subList(0, Math.min(points.size(), maxLen)));
		}
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
			+ ((encodedCursor == null) ? 0 : encodedCursor.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
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
		if(!(obj instanceof TrailPointsResult)) {
			return false;
		}
		TrailPointsResult other = (TrailPointsResult) obj;
		if(encodedCursor == null) {
			if(other.encodedCursor != null) {
				return false;
			}
		}else if(!encodedCursor.equals(other.encodedCursor)) {
			return false;
		}
		if(points == null) {
			if(other.points != null) {
				return false;
			}
		}else if(!points.equals(other.points)) {
			return false;
		}
		return true;
	}

}
