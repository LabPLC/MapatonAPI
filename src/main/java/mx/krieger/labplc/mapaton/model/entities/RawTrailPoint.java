/**
 * 30 Jul 2015 - 14:04:38
 */
package mx.krieger.labplc.mapaton.model.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;
import mx.krieger.labplc.mapaton.model.wrappers.PointData;

/**
 * This class stores the information of a point, timestamp and location.
 * @author Juanjo (juanjo@krieger.mx)
 * @since 30 Jul 2015 - 14:04:38
 * @version v1.0.0.0
 */
@Entity
public class RawTrailPoint extends PersistentEntity{
	@Index private Long trailId;
	@Index private PointData pointData;

	/**
	 * This is the default constructor used to create an empty instance of a
	 * point.
	 */
	public RawTrailPoint(){
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * @param trailId
	 * @param pointData
	 */
	public RawTrailPoint(Long trailId, PointData pointData){
		super();
		this.trailId = trailId;
		this.pointData = pointData;
	}

	/**
	 * @return the trailId
	 */
	public Long getTrailId(){
		return trailId;
	}

	/**
	 * @param trailId
	 *            the trailId to set
	 */
	public void setTrailId(Long trailId){
		this.trailId = trailId;
	}

	/**
	 * @return the pointData
	 */
	public PointData getPointData(){
		return pointData;
	}

	/**
	 * @param pointData
	 *            the pointData to set
	 */
	public void setPointData(PointData pointData){
		this.pointData = pointData;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("TrailPoint [");
		if(trailId != null){
			builder.append("trailId=");
			builder.append(trailId);
			builder.append(", ");
		}
		if(pointData != null){
			builder.append("pointData=");
			builder.append(pointData);
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
			+ ((pointData == null) ? 0 : pointData.hashCode());
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
		if(!(obj instanceof RawTrailPoint)){
			return false;
		}
		RawTrailPoint other = (RawTrailPoint) obj;
		if(pointData == null){
			if(other.pointData != null){
				return false;
			}
		}else if(!pointData.equals(other.pointData)){
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
