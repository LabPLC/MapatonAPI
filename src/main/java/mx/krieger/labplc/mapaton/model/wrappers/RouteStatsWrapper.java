package mx.krieger.labplc.mapaton.model.wrappers;

public class RouteStatsWrapper {
	private String originStation;
	private String destinyStation;
	private float rating;
	
	
	public RouteStatsWrapper(String originStation, String destinyStation, float rating) {
		super();
		this.originStation = originStation;
		this.destinyStation = destinyStation;
		this.rating = rating;
	}
	public RouteStatsWrapper() {
		super();
	}
	/**
	 * @return the originStation
	 */
	public String getOriginStation() {
		return originStation;
	}
	/**
	 * @param originStation the originStation to set
	 */
	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}
	/**
	 * @return the destinyStation
	 */
	public String getDestinyStation() {
		return destinyStation;
	}
	/**
	 * @param destinyStation the destinyStation to set
	 */
	public void setDestinyStation(String destinyStation) {
		this.destinyStation = destinyStation;
	}
	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(float rating) {
		this.rating = rating;
	}
	

}
