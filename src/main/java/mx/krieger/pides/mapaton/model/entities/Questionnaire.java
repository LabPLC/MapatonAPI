package mx.krieger.pides.mapaton.model.entities;

import java.util.Date;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;
import mx.krieger.pides.mapaton.model.wrappers.QuestionnaireWrapper;

@Entity
public class Questionnaire extends PersistentEntity{
	
	private int[] security;
	private int fullness;
	private int[] state;
	private int[] transitRegulation;
	private int transportType;
	private Date timeTaken;
	private String notes;
	
	@Index
	private int rating;
	
	@Index
	private Ref<RegisteredTrail> trail;

	
	
	public Questionnaire(int[] security, int fullness, int[] state, int[] transitRegulation, int transportType,
			Date timeTaken, String notes, int rating, Ref<RegisteredTrail> trail) {
		super();
		this.security = security;
		this.fullness = fullness;
		this.state = state;
		this.transitRegulation = transitRegulation;
		this.transportType = transportType;
		this.timeTaken = timeTaken;
		this.notes = notes;
		this.rating = rating;
		this.trail = trail;
	}

	public Questionnaire() {
		super();
	}

	public Questionnaire(QuestionnaireWrapper param, Ref<RegisteredTrail> trail) {
		super();
		this.security = param.getSecurity();
		this.fullness = param.getFullness();
		this.state = param.getState();
		this.transitRegulation = param.getTransitRegulation();
		this.transportType = param.getTransportType();
		this.timeTaken = param.getTimeTaken();
		this.notes = param.getNotes();
		this.rating = param.getRating();
		this.trail = trail;
	}

	/**
	 * @return the security
	 */
	public int[] getSecurity() {
		return security;
	}

	/**
	 * @param security the security to set
	 */
	public void setSecurity(int[] security) {
		this.security = security;
	}

	/**
	 * @return the fullness
	 */
	public int getFullness() {
		return fullness;
	}

	/**
	 * @param fullness the fullness to set
	 */
	public void setFullness(int fullness) {
		this.fullness = fullness;
	}

	/**
	 * @return the state
	 */
	public int[] getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int[] state) {
		this.state = state;
	}

	/**
	 * @return the transitRegulation
	 */
	public int[] getTransitRegulation() {
		return transitRegulation;
	}

	/**
	 * @param transitRegulation the transitRegulation to set
	 */
	public void setTransitRegulation(int[] transitRegulation) {
		this.transitRegulation = transitRegulation;
	}

	/**
	 * @return the transportType
	 */
	public int getTransportType() {
		return transportType;
	}

	/**
	 * @param transportType the transportType to set
	 */
	public void setTransportType(int transportType) {
		this.transportType = transportType;
	}

	/**
	 * @return the timeTaken
	 */
	public Date getTimeTaken() {
		return timeTaken;
	}

	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(Date timeTaken) {
		this.timeTaken = timeTaken;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
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
