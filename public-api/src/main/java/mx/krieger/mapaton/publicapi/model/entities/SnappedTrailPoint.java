package mx.krieger.mapaton.publicapi.model.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.IndexedGPSLocation;
import mx.krieger.internal.apicommons.model.entities.PersistentEntity;

/**
 * This class Keeps the cleaned points for a trail.
 *
 * @author Juanjo (juanjo@krieger.mx)
 * @version v0.0.0.0
 * @since 16 / feb / 2016
 */
@Entity
public class SnappedTrailPoint extends PersistentEntity{

	@Index private Long trailId;
	
	@Index private IndexedGPSLocation location;
	
	@Index private int position;

	/**
	 * Instantiates a new snapped trail point.
	 *
	 * @param trailId the trail id
	 * @param location the location
	 */
	public SnappedTrailPoint(Long trailId, IndexedGPSLocation location){
		super();
		this.trailId = trailId;
		this.location = location;
	}
	
	

	/**
	 * Instantiates a new snapped trail point.
	 */
	public SnappedTrailPoint() {
		super();
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
	 * Gets the trail id.
	 *
	 * @return the trailId
	 */
	public Long getTrailId(){
		return trailId;
	}

	/**
	 * Sets the trail id.
	 *
	 * @param trailId            the trailId to set
	 */
	public void setTrailId(Long trailId){
		this.trailId = trailId;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public IndexedGPSLocation getLocation(){
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location            the location to set
	 */
	public void setLocation(IndexedGPSLocation location){
		this.location = location;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("SnappedTrailPoint [");
		if(trailId != null){
			builder.append("trailId=");
			builder.append(trailId);
			builder.append(", ");
		}
		if(location != null){
			builder.append("location=");
			builder.append(location);
			builder.append(", ");
		}
		if(id != null){
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if(creationDate != null){
			builder.append("creationDate=");
			builder.append(creationDate);
			builder.append(", ");
		}
		if(status != null){
			builder.append("status=");
			builder.append(status);
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
		int result = super.hashCode();
		result = prime * result
			+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((trailId == null) ? 0 : trailId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object) */
	@Override
	public boolean equals(Object obj){
		if(this == obj){
			return true;
		}
		if(!super.equals(obj)){
			return false;
		}
		if(!(obj instanceof SnappedTrailPoint)){
			return false;
		}
		SnappedTrailPoint other = (SnappedTrailPoint) obj;
		if(location == null){
			if(other.location != null){
				return false;
			}
		}else if(!location.equals(other.location)){
			return false;
		}
		if(trailId == null){
			if(other.trailId != null){
				return false;
			}
		}else if(!trailId.equals(other.trailId)){
			return false;
		}
		return true;
	}

}
