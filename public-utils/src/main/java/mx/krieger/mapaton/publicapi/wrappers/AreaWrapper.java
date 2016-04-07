/**
 * 21 Aug 2015 - 00:52:28
 */
package mx.krieger.mapaton.publicapi.wrappers;

import mx.krieger.internal.commons.utils.location.beans.GPSLocation;

/**
 * This class wraps and area to search stations for.
 * 
 * @author JJMS (juanjo@krieger.mx)
 * @since 21 Aug 2015 - 00:52:28
 * @version 1.0.0.0
 */
public class AreaWrapper {

	private GPSLocation southWestCorner;
	private GPSLocation northEastCorner;

	/**
	 * This is the [default/overloaded/wrapper] constructor used to
	 * [create/wrap/unwrap] [an empty/a complete] instance of AreaWrapper.java
	 */
	public AreaWrapper() {
		super();
	}

	/**
	 * This is the [default/overloaded/wrapper] constructor used to
	 * [create/wrap/unwrap] [an empty/a complete] instance of AreaWrapper.java
	 * 
	 * @param southWestCorner
	 * @param northEastCorner
	 */
	public AreaWrapper(GPSLocation southWestCorner, GPSLocation northEastCorner) {
		super();
		this.southWestCorner = southWestCorner;
		this.northEastCorner = northEastCorner;
	}

	/**
	 * @return the southWestCorner
	 */
	public GPSLocation getSouthWestCorner() {
		return southWestCorner;
	}

	/**
	 * @param southWestCorner
	 *            the southWestCorner to set
	 */
	public void setSouthWestCorner(GPSLocation southWestCorner) {
		this.southWestCorner = southWestCorner;
	}

	/**
	 * @return the northEastCorner
	 */
	public GPSLocation getNorthEastCorner() {
		return northEastCorner;
	}

	/**
	 * @param northEastCorner
	 *            the northEastCorner to set
	 */
	public void setNorthEastCorner(GPSLocation northEastCorner) {
		this.northEastCorner = northEastCorner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AreaWrapper [");
		if (southWestCorner != null) {
			builder.append("southWestCorner=");
			builder.append(southWestCorner);
			builder.append(", ");
		}
		if (northEastCorner != null) {
			builder.append("northEastCorner=");
			builder.append(northEastCorner);
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
		result = prime * result + ((northEastCorner == null) ? 0 : northEastCorner.hashCode());
		result = prime * result + ((southWestCorner == null) ? 0 : southWestCorner.hashCode());
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
		if (!(obj instanceof AreaWrapper)) {
			return false;
		}
		AreaWrapper other = (AreaWrapper) obj;
		if (northEastCorner == null) {
			if (other.northEastCorner != null) {
				return false;
			}
		} else if (!northEastCorner.equals(other.northEastCorner)) {
			return false;
		}
		if (southWestCorner == null) {
			if (other.southWestCorner != null) {
				return false;
			}
		} else if (!southWestCorner.equals(other.southWestCorner)) {
			return false;
		}
		return true;
	}

}
