/**
 * 
 */
package mx.krieger.labplc.mapaton.model.wrappers;

import java.util.Date;

import mx.krieger.labplc.mapaton.model.entities.RegisteredTrail;

/**
 * The Class TrailDetails.
 * This class extends TrailWrapper to detail more about a trail
 */
public class TrailDetails extends TrailWrapper {

	private Date creationDate;
	private int trailStatus;
	private String invalidReason = "";

	private double totalMinutes;
	private double totalMeters;

	private int gtfsStatus;

	/**
	 * Instantiates a new trail details.
	 *
	 * @param mappedTrail the mapped trail
	 * @param creationDate the creation date
	 */
	public TrailDetails(RegisteredTrail mappedTrail, Date creationDate) {
		super(mappedTrail);
		this.trailStatus = mappedTrail.getTrailStatus();
		this.creationDate=(creationDate);
		this.gtfsStatus = mappedTrail.getGtfsStatus();
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	} 
	
	

	/**
	 * Gets the trail status.
	 *
	 * @return the trailStatus
	 */
	public int getTrailStatus() {
		return trailStatus;
	}

	/**
	 * Sets the trail status.
	 *
	 * @param trailStatus the trailStatus to set
	 */
	public void setTrailStatus(int trailStatus) {
		this.trailStatus = trailStatus;
	}

	/**
	 * Gets the invalid reason.
	 *
	 * @return the invalidReason
	 */
	public String getInvalidReason() {
		return invalidReason;
	}

	/**
	 * Sets the invalid reason.
	 *
	 * @param invalidReason the invalidReason to set
	 */
	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}
	
	

	/**
	 * Gets the total minutes.
	 *
	 * @return the totalMinutes
	 */
	public double getTotalMinutes() {
		return totalMinutes;
	}

	/**
	 * Sets the total minutes.
	 *
	 * @param totalMinutes the totalMinutes to set
	 */
	public void setTotalMinutes(double totalMinutes) {
		this.totalMinutes = totalMinutes;
	}

	/**
	 * Gets the total meters.
	 *
	 * @return the totalMeters
	 */
	public double getTotalMeters() {
		return totalMeters;
	}

	/**
	 * Sets the total meters.
	 *
	 * @param totalMeters the totalMeters to set
	 */
	public void setTotalMeters(double totalMeters) {
		this.totalMeters = totalMeters;
	}
	
	
	/**
	 * @return the gtfsStatus
	 */
	public int getGtfsStatus() {
		return gtfsStatus;
	}

	/**
	 * @param gtfsStatus the gtfsStatus to set
	 */
	public void setGtfsStatus(int gtfsStatus) {
		this.gtfsStatus = gtfsStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("TrailDetails [");
		if (creationDate != null) {
			builder.append("creationDate=");
			builder.append(creationDate);
			builder.append(", ");
		}
		if (trailId != null) {
			builder.append("trailId=");
			builder.append(trailId);
			builder.append(", ");
		}
		if (routeId != null) {
			builder.append("routeId=");
			builder.append(routeId);
			builder.append(", ");
		}
		if (routeName != null) {
			builder.append("routeName=");
			builder.append(routeName);
			builder.append(", ");
		}
		if (originPlatformId != null) {
			builder.append("originPlatformId=");
			builder.append(originPlatformId);
			builder.append(", ");
		}
		if (originPlatformName != null) {
			builder.append("originPlatformName=");
			builder.append(originPlatformName);
			builder.append(", ");
		}
		if (originStationId != null) {
			builder.append("originStationId=");
			builder.append(originStationId);
			builder.append(", ");
		}
		if (originStationName != null) {
			builder.append("originStationName=");
			builder.append(originStationName);
			builder.append(", ");
		}
		if (destinationPlatformId != null) {
			builder.append("destinationPlatformId=");
			builder.append(destinationPlatformId);
			builder.append(", ");
		}
		if (destinationPlatformName != null) {
			builder.append("destinationPlatformName=");
			builder.append(destinationPlatformName);
			builder.append(", ");
		}
		if (destinationStationId != null) {
			builder.append("destinationStationId=");
			builder.append(destinationStationId);
			builder.append(", ");
		}
		if (destinationStationName != null) {
			builder.append("destinationStationName=");
			builder.append(destinationStationName);
			builder.append(", ");
		}
		if (branchId != null) {
			builder.append("branchId=");
			builder.append(branchId);
			builder.append(", ");
		}
		if (branchName != null) {
			builder.append("branchName=");
			builder.append(branchName);
			builder.append(", ");
		}
		if (points != null) {
			builder.append("points=");
			builder.append(points.subList(0, Math.min(points.size(), maxLen)));
			builder.append(", ");
		}
		if (transportType != null) {
			builder.append("transportType=");
			builder.append(transportType);
			builder.append(", ");
		}
		builder.append("maxTariff=");
		builder.append(maxTariff);
		builder.append(", ");
		if (photoUrl != null) {
			builder.append("photoUrl=");
			builder.append(photoUrl);
			builder.append(", ");
		}
		if (schedule != null) {
			builder.append("schedule=");
			builder.append(schedule);
			builder.append(", ");
		}
		if (notes != null) {
			builder.append("notes=");
			builder.append(notes);
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
		int result = super.hashCode();
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		long temp = 0;
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof TrailDetails)) {
			return false;
		}
		TrailDetails other = (TrailDetails) obj;
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		
		return true;
	}

}
