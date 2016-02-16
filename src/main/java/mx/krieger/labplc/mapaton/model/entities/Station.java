/**
 * 28 Jul 2015 - 17:29:21
 */
package mx.krieger.labplc.mapaton.model.entities;

import static mx.krieger.labplc.mapaton.utils.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.IndexedGPSLocation;
import mx.krieger.internal.apicommons.model.entities.PersistentEntity;


/**
 * This class persist the data of a station
 * 
 * @author JJMS (juanjo@krieger.mx)
 * @since 19 Aug 2015 - 01:42:30
 * @version 1.0.0.0
 */
@Entity
@Cache
public class Station extends PersistentEntity {
	@Index
	private String name;
	private String displayName;
	private String description;
	@Index
	private IndexedGPSLocation location;
	@Index
	private int magnitude;
	@Index
	private GeoPt geopt;
	
	private int createdStatus;
	
	
	public static class Status{
		public static final int CATALOGUE = 0;
		public static final int TRAIL = 1;
		public static final int TRAIL_VERIFIED = 2;
	}

	/**
	 * This is the default constructor used to create an empty instance of a
	 * Station.
	 */
	public Station() {
		super();
	}

	/**
	 * This is the overloaded constructor used to create a complete instance of
	 * a station.
	 * 
	 * @param name
	 * @param description
	 * @param location
	 * @param knownTrails
	 */
	public Station(String name, String description, IndexedGPSLocation location) {
		super();
		this.displayName = name;
		this.name = name.toUpperCase().trim();
		this.description = description;
		this.location = location;
		if (location != null) {
			this.geopt = new GeoPt(location.getLatitude().floatValue(), location.getLongitude().floatValue());
		}
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the geopt
	 */
	public GeoPt getGeopt() {
		return geopt;
	}

	/**
	 * @param geopt
	 *            the geopt to set
	 */
	public void setGeopt(GeoPt geopt) {
		this.geopt = geopt;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the location
	 */
	public IndexedGPSLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(IndexedGPSLocation location) {
		this.location = location;
		this.geopt = new GeoPt(location.getLatitude().floatValue(), location.getLongitude().floatValue());
	}

	/**
	 * @return the magnitude
	 */
	public int getMagnitude() {
		return magnitude;
	}

	/**
	 * @param magnitude
	 *            the magnitude to set
	 */
	public void setMagnitude(int magnitude) {
		this.magnitude = magnitude;
	}

	
	
	/**
	 * @return the createdStatus
	 */
	public int getCreatedStatus() {
		return createdStatus;
	}

	/**
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
		builder.append("Station [");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
			builder.append(", ");
		}
		if (displayName != null) {
			builder.append("displayName=");
			builder.append(displayName);
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
		builder.append("magnitude=");
		builder.append(magnitude);
		builder.append(", ");
		if (geopt != null) {
			builder.append("geopt=");
			builder.append(geopt);
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
		result = prime * result + ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result + ((geopt == null) ? 0 : geopt.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + magnitude;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Station)) {
			return false;
		}
		Station other = (Station) obj;
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (displayName == null) {
			if (other.displayName != null) {
				return false;
			}
		} else if (!displayName.equals(other.displayName)) {
			return false;
		}
		if (geopt == null) {
			if (other.geopt != null) {
				return false;
			}
		} else if (!geopt.equals(other.geopt)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (magnitude != other.magnitude) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
	public ArrayList<Ref<Platform>> getPlatforms(){
		ArrayList<Ref<Platform>> result =null;
		List<Key<Platform>> platforms = ofy().load().type(Platform.class).filter("station", Ref.create(Key.create(Station.class, this.id))).keys().list();
		if(platforms!=null){
			result= new ArrayList<Ref<Platform>>();
			for(Key<Platform> platform : platforms){
				result.add(Ref.create(platform));
			}
		}
		
		return result;
	}

}
