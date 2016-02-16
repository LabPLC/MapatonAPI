/**
 * 30 Jul 2015 - 14:04:38
 */
package mx.krieger.labplc.mapaton.model.wrappers;

import mx.krieger.internal.commons.utils.dateandtime.beans.TimeStamp;
import mx.krieger.internal.commons.utils.location.beans.GPSLocation;


/**
 * The Class TrailPointWrapper.
 * This class contains the data of a given registered point, with the position it was originally registered as
 */
public class TrailPointWrapper {
	private GPSLocation location;
	private TimeStamp timeStamp;
	private int position;

	/**
	 * This is the default constructor used to create an empty instance of a
	 * point.
	 */
	public TrailPointWrapper() {
		super();
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * a point.
	 *
	 * @param location the location
	 * @param timeStamp the time stamp
	 */
	public TrailPointWrapper(GPSLocation location, TimeStamp timeStamp) {
		super();
		this.location = new GPSLocation(location.getLatitude(), location.getLongitude());
		this.timeStamp = timeStamp;
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * a point.
	 *
	 * @param location the location
	 * @param position the position
	 */
	public TrailPointWrapper(GPSLocation location, int position) {
		super();
		this.location = new GPSLocation(location.getLatitude(), location.getLongitude());
		this.position = position;
	}


	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * a point.
	 *
	 * @param location the location
	 * @param timeStamp the time stamp
	 * @param position the position
	 */
	public TrailPointWrapper(GPSLocation location, TimeStamp timeStamp, int position) {
		super();
		this.location = location;
		this.timeStamp = timeStamp;
		this.position = position;
	}

	
	
	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public GPSLocation getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location            the location to set
	 */
	public void setLocation(GPSLocation location) {
		this.location = location;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the timeStamp
	 */
	public TimeStamp getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp            the timeStamp to set
	 */
	public void setTimeStamp(TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrailPointWrapper [");
		if (location != null) {
			builder.append("location=");
			builder.append(location);
			builder.append(", ");
		}
		if (timeStamp != null) {
			builder.append("timeStamp=");
			builder.append(timeStamp);
		}
		builder.append("]");
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TrailPointWrapper)) {
			return false;
		}
		TrailPointWrapper other = (TrailPointWrapper) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (timeStamp == null) {
			if (other.timeStamp != null) {
				return false;
			}
		} else if (!timeStamp.equals(other.timeStamp)) {
			return false;
		}
		return true;
	}

}
