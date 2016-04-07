package mx.krieger.labplc.pides.model.wrappers;

import java.util.ArrayList;

/**
 * The Class TrailPointsResult.
 * This class is a paginated response that contains the registered points of a trail;
 */
public class TrailPointsResult extends CursorResponse{

	private ArrayList<TrailPointWrapper> points;

	/**
	 * Instantiates a new trail points result.
	 */
	public TrailPointsResult(){
		super();
	}

	/**
	 * Instantiates a new trail points result.
	 *
	 * @param points the points
	 */
	public TrailPointsResult(ArrayList<TrailPointWrapper> points){
		super();
		this.points = points;
	}

	
	/**
	 * Gets the points.
	 *
	 * @return the points
	 */
	public ArrayList<TrailPointWrapper> getPoints(){
		return points;
	}

	/**
	 * Sets the points.
	 *
	 * @param points            the points to set
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
