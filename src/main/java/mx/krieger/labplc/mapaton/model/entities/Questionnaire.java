package mx.krieger.labplc.mapaton.model.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;
import mx.krieger.labplc.mapaton.model.wrappers.QuestionnaireWrapper;

public class Questionnaire extends PersistentEntity{
	
	private int security;
	private int fullness;
	private int cleanness;
	private int value;
	private String notes;
	
	@Index
	private int rating;
	
	@Index
	private Ref<RegisteredTrail> trail;
	
	
	public Questionnaire(QuestionnaireWrapper param) {
		super();
		this.security = param.getSecurity();
		this.fullness = param.getFullness();
		this.rating = param.getRating();
		this.notes = param.getNotes();
		this.cleanness = param.getCleanness();
		this.value = param.getValue();
		this.trail = Ref.create(Key.create(RegisteredTrail.class, param.getTrailId()));
	}
	
	
	public Questionnaire() {
		super();
	}


	public int getSecurity() {
		return security;
	}
	public void setSecurity(int security) {
		this.security = security;
	}
	public int getFullness() {
		return fullness;
	}
	public void setFullness(int fullness) {
		this.fullness = fullness;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Ref<RegisteredTrail> getTrail() {
		return trail;
	}
	public void setTrail(Ref<RegisteredTrail> trail) {
		this.trail = trail;
	}


	public int getCleanness() {
		return cleanness;
	}


	public void setCleanness(int cleanness) {
		this.cleanness = cleanness;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public String getNotes() {
		return notes;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	

}
