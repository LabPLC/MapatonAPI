package mx.krieger.mapaton.publicapi.model.wrappers;

import java.util.Date;

import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.commons.utils.location.beans.GPSLocation;

/**
 * The Class PointData.
 * This class has the information about a given point
 */
public class PointData{
	private GPSLocation location;
	@Index
	private Date timeStamp;

	/**
	 * Instantiates a new point data.
	 */
	public PointData(){
		super();
	}

	/**
	 * Instantiates a new point data.
	 *
	 * @param location the location
	 * @param timeStamp the time stamp
	 */
	public PointData(GPSLocation location, Date timeStamp){
		super();
		this.location = location;
		this.timeStamp = timeStamp;
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of PointData from the contents of a pointWrapper.
	 *
	 * @param pointWrapper the point wrapper
	 */
	public PointData(TrailPointWrapper pointWrapper){
		super();
		this.location=pointWrapper.getLocation();
		this.timeStamp=pointWrapper.getTimeStamp().toCalendar().getTime();
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public GPSLocation getLocation(){
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location            the location to set
	 */
	public void setLocation(GPSLocation location){
		this.location = location;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the timeStamp
	 */
	public Date getTimeStamp(){
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp            the timeStamp to set
	 */
	public void setTimeStamp(Date timeStamp){
		this.timeStamp = timeStamp;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("PointData [");
		if(location != null) {
			builder.append("location=");
			builder.append(location);
			builder.append(", ");
		}
		if(timeStamp != null) {
			builder.append("timeStamp=");
			builder.append(timeStamp);
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
			+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
			+ ((timeStamp == null) ? 0 : timeStamp.hashCode());
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
		if(!(obj instanceof PointData)) {
			return false;
		}
		PointData other = (PointData) obj;
		if(location == null) {
			if(other.location != null) {
				return false;
			}
		}else if(!location.equals(other.location)) {
			return false;
		}
		if(timeStamp == null) {
			if(other.timeStamp != null) {
				return false;
			}
		}else if(!timeStamp.equals(other.timeStamp)) {
			return false;
		}
		return true;
	}

}
