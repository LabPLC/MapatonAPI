package mx.krieger.labplc.mapaton.model.wrappers;

public class RouteStats {
	private float security;
	private float fullness;
	private float rating;
	private float cleanness;
	private float value;
	
	public void normalize(int size){
		security/=size;
		fullness/=size;
		rating/=size;
		cleanness/=size;
		value/=size;
	}
	
	public float getSecurity() {
		return security;
	}
	public float getFullness() {
		return fullness;
	}
	public float getRating() {
		return rating;
	}
	public float getCleanness() {
		return cleanness;
	}
	public float getValue() {
		return value;
	}
	public void setSecurity(float security) {
		this.security = security;
	}
	public void setFullness(float fullness) {
		this.fullness = fullness;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public void setCleanness(float cleanness) {
		this.cleanness = cleanness;
	}
	public void setValue(float value) {
		this.value = value;
	}
	

	public void addSecurity(float security) {
		this.security += security;
	}
	public void addFullness(float fullness) {
		this.fullness += fullness;
	}
	public void addRating(float rating) {
		this.rating += rating;
	}
	public void addCleanness(float cleanness) {
		this.cleanness += cleanness;
	}
	public void addValue(float value) {
		this.value += value;
	}
	
	
	
}
