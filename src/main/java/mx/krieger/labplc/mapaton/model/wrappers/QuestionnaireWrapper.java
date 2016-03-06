package mx.krieger.labplc.mapaton.model.wrappers;

import mx.krieger.labplc.mapaton.model.entities.Questionnaire;

public class QuestionnaireWrapper {

	private int security;
	private int fullness;
	private int rating;
	private int cleanness;
	private int value;
	private String notes;
	private long trailId;
	

	public QuestionnaireWrapper(int security, int fullness, int rating, long trailId) {
		super();
		this.security = security;
		this.fullness = fullness;
		this.rating = rating;
		this.trailId = trailId;
	}

	public QuestionnaireWrapper(Questionnaire param) {
		super();
		this.security = param.getSecurity();
		this.fullness = param.getFullness();
		this.rating = param.getRating();		
		this.notes = param.getNotes();
		this.cleanness = param.getCleanness();
		this.value = param.getValue();

		this.trailId = param.getTrail().get().getId();
	}

	public QuestionnaireWrapper() {
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
	public long getTrailId() {
		return trailId;
	}
	public void setTrailId(long trailId) {
		this.trailId = trailId;
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
