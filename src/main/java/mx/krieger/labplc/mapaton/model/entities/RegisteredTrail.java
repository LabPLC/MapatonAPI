/**
 * 28 Jul 2015 - 17:26:37
 */
package mx.krieger.labplc.mapaton.model.entities;

import java.util.ArrayList;
import java.util.Date;

import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Subclass;

import mx.krieger.labplc.mapaton.commons.enums.TransportType;
import mx.krieger.labplc.mapaton.commons.exceptions.TrailNotFoundException;
import mx.krieger.labplc.mapaton.handlers.TrailsHandler;
import mx.krieger.labplc.mapaton.model.wrappers.PointData;

/**
 * This entity class persists the information of a trail.
 * @author JJMS (juanjo@krieger.mx)
 * @since 19 Aug 2015 - 01:23:34
 * @version 1.0.0.0
 */
@Subclass(index = true)
public class RegisteredTrail extends GenericTrail{
	@Index private TransportType transportType;
	private double maxTariff;
	private String photoUrl;
	private String schedule;
	private String notes;
	private double totalMinutes;
	private double totalMeters;
	@Index
	private int trailStatus;
	private String invalidStatusReason;

	
	private Date revisionDate;

	
	/**
	 * This is the default constructor used to
	 * create an empty instance of NewTrail.java
	 */
	public RegisteredTrail(){
		super();
		this.isRegistered = true;
	}



	
	/**
	 * @return the transportType
	 */
	public TransportType getTransportType(){
		return transportType;
	}

	/**
	 * @param transportType
	 *            the transportType to set
	 */
	public void setTransportType(TransportType transportType){
		this.transportType = transportType;
	}

	/**
	 * @return the maxTariff
	 */
	public double getMaxTariff(){
		return maxTariff;
	}

	/**
	 * @param maxTariff
	 *            the maxTariff to set
	 */
	public void setMaxTariff(double maxTariff){
		this.maxTariff = maxTariff;
	}

	/**
	 * @return the photoUrl
	 */
	public String getPhotoUrl(){
		return photoUrl;
	}

	/**
	 * @param photoUrl
	 *            the photoUrl to set
	 */
	public void setPhotoUrl(String photoUrl){
		this.photoUrl = photoUrl;
	}

	/**
	 * @return the schedule
	 */
	public String getSchedule(){
		return schedule;
	}

	/**
	 * @param schedule
	 *            the schedule to set
	 */
	public void setSchedule(String schedule){
		this.schedule = schedule;
	}

	/**
	 * @return the notes
	 */
	public String getNotes(){
		return notes;
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes){
		this.notes = notes;
	}

	/**
	 * @return the totalMinutes
	 */
	public double getTotalMinutes(){
		return totalMinutes;
	}

	/**
	 * @param totalMinutes
	 *            the totalMinutes to set
	 */
	public void setTotalMinutes(double totalMinutes){
		this.totalMinutes = totalMinutes;
	}

	/**
	 * @return the totalMeters
	 */
	public double getTotalMeters(){
		return totalMeters;
	}

	/**
	 * @param totalMeters
	 *            the totalMeters to set
	 */
	public void setTotalMeters(double totalMeters){
		this.totalMeters = totalMeters;
	}

	/**
	 * @return the trailStatus
	 */
	public int getTrailStatus(){
		return trailStatus;
	}

	/**
	 * @param trailStatus
	 *            the trailStatus to set
	 */
	public void setTrailStatus(int trailStatus){
		this.trailStatus = trailStatus;
	}

	
	/**
	 * @return the invalidStatusReason
	 */
	public String getInvalidStatusReason() {
		return invalidStatusReason;
	}

	/**
	 * @param invalidStatusReason the invalidStatusReason to set
	 */
	public void setInvalidStatusReason(String invalidStatusReason) {
		this.invalidStatusReason = invalidStatusReason;
	}

	/**
	 * @return the revisionDate
	 */
	public Date getRevisionDate() {
		return revisionDate;
	}

	/**
	 * @param revisionDate the revisionDate to set
	 */
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("RegisteredTrail [");
		
		if(transportType != null){
			builder.append("transportType=");
			builder.append(transportType);
			builder.append(", ");
		}
		builder.append("maxTariff=");
		builder.append(maxTariff);
		builder.append(", ");
		if(photoUrl != null){
			builder.append("photoUrl=");
			builder.append(photoUrl);
			builder.append(", ");
		}
		if(schedule != null){
			builder.append("schedule=");
			builder.append(schedule);
			builder.append(", ");
		}
		if(notes != null){
			builder.append("notes=");
			builder.append(notes);
			builder.append(", ");
		}
		builder.append("totalMinutes=");
		builder.append(totalMinutes);
		builder.append(", totalMeters=");
		builder.append(totalMeters);
		builder.append(", trailStatus=");
		builder.append(trailStatus);
		builder.append(", ");
		if(route != null){
			builder.append("route=");
			builder.append(route);
			builder.append(", ");
		}
		if(origin != null){
			builder.append("origin=");
			builder.append(origin);
			builder.append(", ");
		}
		if(destination != null){
			builder.append("destination=");
			builder.append(destination);
			builder.append(", ");
		}
		if(branch != null){
			builder.append("branch=");
			builder.append(branch);
			builder.append(", ");
		}
		builder.append("isRegistered=");
		builder.append(isRegistered);
		builder.append(", ");
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
		long temp;
		temp = Double.doubleToLongBits(maxTariff);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result
			+ ((photoUrl == null) ? 0 : photoUrl.hashCode());
		result = prime * result
			+ ((schedule == null) ? 0 : schedule.hashCode());
		temp = Double.doubleToLongBits(totalMeters);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(totalMinutes);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + trailStatus;
		result = prime * result
			+ ((transportType == null) ? 0 : transportType.hashCode());
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
		if(!(obj instanceof RegisteredTrail)){
			return false;
		}
		RegisteredTrail other = (RegisteredTrail) obj;
		if(Double.doubleToLongBits(maxTariff) != Double
			.doubleToLongBits(other.maxTariff)){
			return false;
		}
		if(notes == null){
			if(other.notes != null){
				return false;
			}
		}else if(!notes.equals(other.notes)){
			return false;
		}
		if(photoUrl == null){
			if(other.photoUrl != null){
				return false;
			}
		}else if(!photoUrl.equals(other.photoUrl)){
			return false;
		}
		if(schedule == null){
			if(other.schedule != null){
				return false;
			}
		}else if(!schedule.equals(other.schedule)){
			return false;
		}
		if(Double.doubleToLongBits(totalMeters) != Double
			.doubleToLongBits(other.totalMeters)){
			return false;
		}
		if(Double.doubleToLongBits(totalMinutes) != Double
			.doubleToLongBits(other.totalMinutes)){
			return false;
		}
		if(trailStatus != other.trailStatus){
			return false;
		}
		if(transportType != other.transportType){
			return false;
		}
		return true;
	}

}
