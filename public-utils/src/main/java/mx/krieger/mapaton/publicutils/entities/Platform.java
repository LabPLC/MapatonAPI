/**
 * 19 Aug 2015 - 01:11:50
 */
package mx.krieger.mapaton.publicutils.entities;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.IndexedGPSLocation;
import mx.krieger.internal.apicommons.model.entities.PersistentEntity;

/**
 * This class is used to persist platforms.
 *
 * @author JJMS (juanjo@krieger.mx)
 * @version 1.0.0.0
 * @since 19 Aug 2015 - 01:11:50
 */
@Entity
@Cache
public class Platform extends PersistentEntity {

	@Index
	private Ref<Station> station;
	
	@Index
	private String name;
	
	private String description;
	
	@Index
	private IndexedGPSLocation location;
	
	@Index
	private GeoPt geopt;

	private int createdStatus;
	
	
	public static class Status{
		
		public static final int CATALOGUE = 0;
		public static final int TRAIL = 1;
		public static final int TRAIL_VERIFIED = 2;
	}

	
	/**
	 * Instantiates a new platform.
	 */
	public Platform() {
		super();
	}

	/**
	 * Instantiates a new platform.
	 *
	 * @param station the station
	 * @param name the name
	 * @param description the description
	 * @param location the location
	 */
	public Platform(Ref<Station> station, String name, String description, IndexedGPSLocation location) {
		super();
		this.station=station;
		this.name = name;
		this.description = description;
		this.location = location;
		this.geopt = new GeoPt(location.getLatitude().floatValue(), location.getLongitude().floatValue());
	}

	/**
	 * Gets the station.
	 *
	 * @return the station
	 */
	public Station getStation() {
		return station.get();
	}
	
	/**
	 * Gets the ref station.
	 *
	 * @return the station
	 */
	public Ref<Station> getRefStation() {
		return station;
	}

	/**
	 * Sets the station.
	 *
	 * @param station            the station to set
	 */
	public void setStation(Station station) {
		this.station = Ref.create(station);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public IndexedGPSLocation getLocation() {
		return location;

	}

	/**
	 * Sets the location.
	 *
	 * @param location            the location to set
	 */
	public void setLocation(IndexedGPSLocation location) {
		this.location = location;
		this.geopt = new GeoPt(location.getLatitude().floatValue(), location.getLongitude().floatValue());
	}

	/**
	 * Gets the created status.
	 *
	 * @return the createdStatus
	 */
	public int getCreatedStatus() {
		return createdStatus;
	}

	/**
	 * Sets the created status.
	 *
	 * @param createdStatus the createdStatus to set
	 */
	public void setCreatedStatus(int createdStatus) {
		this.createdStatus = createdStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Platform [");
		if (station != null) {
			builder.append("station=");
			if (station.get() != null)
				builder.append(station.get());
			else
				builder.append("null");
			builder.append(", ");
		}
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (description != null) {
			builder.append("description=");
			builder.append(description);
			builder.append(", ");
		}
		if (location != null) {
			builder.append("location=");
			builder.append(location);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (creationDate != null) {
			builder.append("creationDate=");
			builder.append(creationDate);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((station == null) ? 0 : station.hashCode());
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
		if (!(obj instanceof Platform)) {
			return false;
		}
		Platform other = (Platform) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (station == null) {
			if (other.station != null) {
				return false;
			}
		} else if (!station.equals(other.station)) {
			return false;
		}
		return true;
	}

}
