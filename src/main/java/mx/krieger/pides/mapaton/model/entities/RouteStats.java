package mx.krieger.pides.mapaton.model.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;

@Entity
public class RouteStats extends PersistentEntity{

	@Index
	private float totalRating;
	private int totalElements;
	@Index
	private Ref<RegisteredTrail> trail;
	/**
	 * @return the totalRating
	 */
	public float getTotalRating() {
		return totalRating;
	}
	/**
	 * @param totalRating the totalRating to set
	 */
	public void setTotalRating(float totalRating) {
		this.totalRating = totalRating;
	}
	/**
	 * @param totalRating the totalRating to set
	 */
	public void addRating(float totalRating) {
		this.totalRating += totalRating;
		totalElements +=1;
	}
	/**
	 * @return the totalElements
	 */
	public int getTotalElements() {
		return totalElements;
	}
	/**
	 * @param totalElements the totalElements to set
	 */
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	/**
	 * @return the trail
	 */
	public Ref<RegisteredTrail> getTrail() {
		return trail;
	}
	/**
	 * @param trail the trail to set
	 */
	public void setTrail(Ref<RegisteredTrail> trail) {
		this.trail = trail;
	}

	


}
