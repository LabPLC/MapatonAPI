package mx.krieger.mapaton.publicapi.model.wrappers;

import java.util.ArrayList;

import mx.krieger.internal.commons.utils.strings.SpecialCharacters;
import mx.krieger.mapaton.publicapi.model.entities.RegisteredTrail;

/**
 * The Class TrailWrapper.
 * This class wraps the main elements of a trail that is stored in the datastore;
 */
public class TrailWrapper {

	protected Long trailId;
	protected Long routeId;
	protected String routeName;
	protected Long originPlatformId;
	protected String originPlatformName;
	protected Long originStationId;
	protected String originStationName;
	protected Long destinationPlatformId;
	protected String destinationPlatformName;
	protected Long destinationStationId;
	protected String destinationStationName;
	protected Long branchId;
	protected String branchName;
	protected ArrayList<PointData> points;
	protected String transportType;
	protected double maxTariff;
	protected String photoUrl;
	protected String schedule;
	protected String notes;

	/**
	 * Instantiates a new trail wrapper.
	 */
	public TrailWrapper() {
		super();
	}


	/**
	 * Instantiates a new trail wrapper.
	 *
	 * @param trailId the trail id
	 * @param routeId the route id
	 * @param routeName the route name
	 * @param originPlatformId the origin platform id
	 * @param originPlatformName the origin platform name
	 * @param originStationId the origin station id
	 * @param originStationName the origin station name
	 * @param destinationPlatformId the destination platform id
	 * @param destinationPlatformName the destination platform name
	 * @param destinationStationId the destination station id
	 * @param destinationStationName the destination station name
	 * @param branchId the branch id
	 * @param branchName the branch name
	 * @param points the points
	 * @param transportType the transport type
	 * @param maxTariff the max tariff
	 * @param photoUrl the photo url
	 * @param schedule the schedule
	 * @param notes the notes
	 */
	public TrailWrapper(Long trailId, Long routeId, String routeName, Long originPlatformId, String originPlatformName,
			Long originStationId, String originStationName, Long destinationPlatformId, String destinationPlatformName,
			Long destinationStationId, String destinationStationName, Long branchId, String branchName,
			ArrayList<PointData> points, String transportType, double maxTariff, String photoUrl, String schedule,
			String notes) {
		super();
		this.trailId = trailId;
		this.routeId = routeId;
		this.routeName = routeName;
		this.originPlatformId = originPlatformId;
		this.originPlatformName = originPlatformName;
		this.originStationId = originStationId;
		this.originStationName = originStationName;
		this.destinationPlatformId = destinationPlatformId;
		this.destinationPlatformName = destinationPlatformName;
		this.destinationStationId = destinationStationId;
		this.destinationStationName = destinationStationName;
		this.branchId = branchId;
		this.branchName = branchName;
		this.points = points;
		this.transportType = transportType;
		this.maxTariff = maxTariff;
		this.photoUrl = photoUrl;
		this.schedule = schedule;
		this.notes = notes;
	}
	
	

	/**
	 * Instantiates a new trail wrapper.
	 *
	 * @param mappedTrail the mapped trail
	 */
	public TrailWrapper(RegisteredTrail mappedTrail){
		super();
		this.trailId = mappedTrail.getId();

		try {
			this.routeId = mappedTrail.getRoute().get().getId();
			this.routeName = mappedTrail.getRoute().get().getName();
		} catch (NullPointerException e) {
		}

		try {
			this.originPlatformId = mappedTrail.getOrigin().get().getId();
			this.originPlatformName = mappedTrail.getOrigin().get().getName();
		} catch (NullPointerException e) {
		}

		try {
			this.originStationId = mappedTrail.getOrigin().get().getStation().getId();
			this.originStationName = mappedTrail.getOrigin().get().getStation().getName();
		} catch (NullPointerException e) {
		}

		try {
			this.destinationPlatformId = mappedTrail.getDestination().get().getId();
			this.destinationPlatformName = mappedTrail.getDestination().get().getName();
		} catch (NullPointerException e) {
		}

		try {
			this.destinationStationId = mappedTrail.getDestination().get().getStation().getId();
			this.destinationStationName = mappedTrail.getDestination().get().getStation().getName();
		} catch (NullPointerException e) {
		}

		try {
			this.branchId = mappedTrail.getBranch().get().getId();
			this.branchName = mappedTrail.getBranch() == null || mappedTrail.getBranch().get().getName().trim().isEmpty()
					? "No especificado" : mappedTrail.getBranch().get().getName();
		} catch (NullPointerException e) {
		}

		
//		this.points = mappedTrail.getPoints();
		this.points = new ArrayList<>();

		this.transportType = mappedTrail.getTransportType().label;

		this.maxTariff = mappedTrail.getMaxTariff();
		this.photoUrl = mappedTrail.getPhotoUrl();
		this.schedule = mappedTrail.getSchedule();
		this.notes = mappedTrail.getNotes() == null || mappedTrail.getNotes().trim().isEmpty()
				? new StringBuilder("No se registr").append(SpecialCharacters.o_ACUTE.unicode).append(" ninguna nota").toString()
				: mappedTrail.getNotes();
	}

	/**
	 * Gets the trail id.
	 *
	 * @return the trailId
	 */
	public Long getTrailId() {
		return trailId;
	}

	/**
	 * Sets the trail id.
	 *
	 * @param trailId            the trailId to set
	 */
	public void setTrailId(Long trailId) {
		this.trailId = trailId;
	}

	/**
	 * Gets the route id.
	 *
	 * @return the routeId
	 */
	public Long getRouteId() {
		return routeId;
	}

	/**
	 * Sets the route id.
	 *
	 * @param routeId            the routeId to set
	 */
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	/**
	 * Gets the route name.
	 *
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * Sets the route name.
	 *
	 * @param routeName            the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * Gets the origin platform id.
	 *
	 * @return the originPlatformId
	 */
	public Long getOriginPlatformId() {
		return originPlatformId;
	}

	/**
	 * Sets the origin platform id.
	 *
	 * @param originPlatformId            the originPlatformId to set
	 */
	public void setOriginPlatformId(Long originPlatformId) {
		this.originPlatformId = originPlatformId;
	}

	/**
	 * Gets the origin platform name.
	 *
	 * @return the originPlatformName
	 */
	public String getOriginPlatformName() {
		return originPlatformName;
	}

	/**
	 * Sets the origin platform name.
	 *
	 * @param originPlatformName            the originPlatformName to set
	 */
	public void setOriginPlatformName(String originPlatformName) {
		this.originPlatformName = originPlatformName;
	}

	/**
	 * Gets the origin station id.
	 *
	 * @return the originStationId
	 */
	public Long getOriginStationId() {
		return originStationId;
	}

	/**
	 * Sets the origin station id.
	 *
	 * @param originStationId            the originStationId to set
	 */
	public void setOriginStationId(Long originStationId) {
		this.originStationId = originStationId;
	}

	/**
	 * Gets the origin station name.
	 *
	 * @return the originStationName
	 */
	public String getOriginStationName() {
		return originStationName;
	}

	/**
	 * Sets the origin station name.
	 *
	 * @param originStationName            the originStationName to set
	 */
	public void setOriginStationName(String originStationName) {
		this.originStationName = originStationName;
	}

	/**
	 * Gets the destination platform id.
	 *
	 * @return the destinationPlatformId
	 */
	public Long getDestinationPlatformId() {
		return destinationPlatformId;
	}

	/**
	 * Sets the destination platform id.
	 *
	 * @param destinationPlatformId            the destinationPlatformId to set
	 */
	public void setDestinationPlatformId(Long destinationPlatformId) {
		this.destinationPlatformId = destinationPlatformId;
	}

	/**
	 * Gets the destination platform name.
	 *
	 * @return the destinationPlatformName
	 */
	public String getDestinationPlatformName() {
		return destinationPlatformName;
	}

	/**
	 * Sets the destination platform name.
	 *
	 * @param destinationPlatformName            the destinationPlatformName to set
	 */
	public void setDestinationPlatformName(String destinationPlatformName) {
		this.destinationPlatformName = destinationPlatformName;
	}

	/**
	 * Gets the destination station id.
	 *
	 * @return the destinationStationId
	 */
	public Long getDestinationStationId() {
		return destinationStationId;
	}

	/**
	 * Sets the destination station id.
	 *
	 * @param destinationStationId            the destinationStationId to set
	 */
	public void setDestinationStationId(Long destinationStationId) {
		this.destinationStationId = destinationStationId;
	}

	/**
	 * Gets the destination station name.
	 *
	 * @return the destinationStationName
	 */
	public String getDestinationStationName() {
		return destinationStationName;
	}

	/**
	 * Sets the destination station name.
	 *
	 * @param destinationStationName            the destinationStationName to set
	 */
	public void setDestinationStationName(String destinationStationName) {
		this.destinationStationName = destinationStationName;
	}

	/**
	 * Gets the branch id.
	 *
	 * @return the branchId
	 */
	public Long getBranchId() {
		return branchId;
	}

	/**
	 * Sets the branch id.
	 *
	 * @param branchId            the branchId to set
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * Gets the branch name.
	 *
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * Sets the branch name.
	 *
	 * @param branchName            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * Gets the transport type.
	 *
	 * @return the transportType
	 */
	public String getTransportType() {
		return transportType;
	}

	/**
	 * Sets the transport type.
	 *
	 * @param transportType            the transportType to set
	 */
	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	/**
	 * Gets the max tariff.
	 *
	 * @return the maxTariff
	 */
	public double getMaxTariff() {
		return maxTariff;
	}

	/**
	 * Sets the max tariff.
	 *
	 * @param maxTariff            the maxTariff to set
	 */
	public void setMaxTariff(double maxTariff) {
		this.maxTariff = maxTariff;
	}

	/**
	 * Gets the photo url.
	 *
	 * @return the photoUrl
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

	/**
	 * Sets the photo url.
	 *
	 * @param photoUrl            the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/**
	 * Gets the schedule.
	 *
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * Sets the schedule.
	 *
	 * @param schedule            the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
		builder.append("UserTrail [");
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
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result + ((destinationPlatformId == null) ? 0 : destinationPlatformId.hashCode());
		result = prime * result + ((destinationPlatformName == null) ? 0 : destinationPlatformName.hashCode());
		result = prime * result + ((destinationStationId == null) ? 0 : destinationStationId.hashCode());
		result = prime * result + ((destinationStationName == null) ? 0 : destinationStationName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maxTariff);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((originPlatformId == null) ? 0 : originPlatformId.hashCode());
		result = prime * result + ((originPlatformName == null) ? 0 : originPlatformName.hashCode());
		result = prime * result + ((originStationId == null) ? 0 : originStationId.hashCode());
		result = prime * result + ((originStationName == null) ? 0 : originStationName.hashCode());
		result = prime * result + ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
		result = prime * result + ((routeName == null) ? 0 : routeName.hashCode());
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((trailId == null) ? 0 : trailId.hashCode());
		result = prime * result + ((transportType == null) ? 0 : transportType.hashCode());
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
		if (!(obj instanceof TrailWrapper)) {
			return false;
		}
		TrailWrapper other = (TrailWrapper) obj;
		if (branchId == null) {
			if (other.branchId != null) {
				return false;
			}
		} else if (!branchId.equals(other.branchId)) {
			return false;
		}
		if (branchName == null) {
			if (other.branchName != null) {
				return false;
			}
		} else if (!branchName.equals(other.branchName)) {
			return false;
		}
		if (destinationPlatformId == null) {
			if (other.destinationPlatformId != null) {
				return false;
			}
		} else if (!destinationPlatformId.equals(other.destinationPlatformId)) {
			return false;
		}
		if (destinationPlatformName == null) {
			if (other.destinationPlatformName != null) {
				return false;
			}
		} else if (!destinationPlatformName.equals(other.destinationPlatformName)) {
			return false;
		}
		if (destinationStationId == null) {
			if (other.destinationStationId != null) {
				return false;
			}
		} else if (!destinationStationId.equals(other.destinationStationId)) {
			return false;
		}
		if (destinationStationName == null) {
			if (other.destinationStationName != null) {
				return false;
			}
		} else if (!destinationStationName.equals(other.destinationStationName)) {
			return false;
		}
		if (Double.doubleToLongBits(maxTariff) != Double.doubleToLongBits(other.maxTariff)) {
			return false;
		}
		if (notes == null) {
			if (other.notes != null) {
				return false;
			}
		} else if (!notes.equals(other.notes)) {
			return false;
		}
		if (originPlatformId == null) {
			if (other.originPlatformId != null) {
				return false;
			}
		} else if (!originPlatformId.equals(other.originPlatformId)) {
			return false;
		}
		if (originPlatformName == null) {
			if (other.originPlatformName != null) {
				return false;
			}
		} else if (!originPlatformName.equals(other.originPlatformName)) {
			return false;
		}
		if (originStationId == null) {
			if (other.originStationId != null) {
				return false;
			}
		} else if (!originStationId.equals(other.originStationId)) {
			return false;
		}
		if (originStationName == null) {
			if (other.originStationName != null) {
				return false;
			}
		} else if (!originStationName.equals(other.originStationName)) {
			return false;
		}
		if (photoUrl == null) {
			if (other.photoUrl != null) {
				return false;
			}
		} else if (!photoUrl.equals(other.photoUrl)) {
			return false;
		}
		if (points == null) {
			if (other.points != null) {
				return false;
			}
		} else if (!points.equals(other.points)) {
			return false;
		}
		if (routeId == null) {
			if (other.routeId != null) {
				return false;
			}
		} else if (!routeId.equals(other.routeId)) {
			return false;
		}
		if (routeName == null) {
			if (other.routeName != null) {
				return false;
			}
		} else if (!routeName.equals(other.routeName)) {
			return false;
		}
		if (schedule == null) {
			if (other.schedule != null) {
				return false;
			}
		} else if (!schedule.equals(other.schedule)) {
			return false;
		}
		if (trailId == null) {
			if (other.trailId != null) {
				return false;
			}
		} else if (!trailId.equals(other.trailId)) {
			return false;
		}
		if (transportType != other.transportType) {
			return false;
		}
		return true;
	}

}
