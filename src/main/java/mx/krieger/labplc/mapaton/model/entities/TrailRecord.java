package mx.krieger.labplc.mapaton.model.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;

/**
 * This class holds the original data of a newly registered trail,
 * the element type, and the total score of the element. Elements can be either
 * users or teams.
 * @author Juanjo (juanjo@krieger.mx)
 * @since 21 Nov 2015 - 17:19:24
 * @version v3.0.0.0
 */
@Entity
public class TrailRecord extends PersistentEntity{

	@Index private Ref<RegisteredTrail> trail;
	private String originalTrail;

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 */
	public TrailRecord(){
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * @param trail
	 * @param originalTrail
	 */
	public TrailRecord(Ref<RegisteredTrail> trail, String originalTrail){
		super();
		this.trail = trail;
		this.originalTrail = originalTrail;
	}

	/**
	 * @return the trail
	 */
	public Ref<RegisteredTrail> getTrail(){
		return trail;
	}

	/**
	 * @param trail
	 *            the trail to set
	 */
	public void setTrail(Ref<RegisteredTrail> trail){
		this.trail = trail;
	}

	/**
	 * @return the originalTrail
	 */
	public String getOriginalTrail(){
		return originalTrail;
	}

	/**
	 * @param originalTrail
	 *            the originalTrail to set
	 */
	public void setOriginalTrail(String originalTrail){
		this.originalTrail = originalTrail;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TrailRecord [");
		if(trail != null){
			builder.append("trail=");
			builder.append(trail);
			builder.append(", ");
		}
		if(originalTrail != null){
			builder.append("originalTrail=");
			builder.append(originalTrail);
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
			+ ((originalTrail == null) ? 0 : originalTrail.hashCode());
		result = prime * result + ((trail == null) ? 0 : trail.hashCode());
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
		if(!(obj instanceof TrailRecord)){
			return false;
		}
		TrailRecord other = (TrailRecord) obj;
		if(originalTrail == null){
			if(other.originalTrail != null){
				return false;
			}
		}else if(!originalTrail.equals(other.originalTrail)){
			return false;
		}
		if(trail == null){
			if(other.trail != null){
				return false;
			}
		}else if(!trail.equals(other.trail)){
			return false;
		}
		return true;
	}

}
