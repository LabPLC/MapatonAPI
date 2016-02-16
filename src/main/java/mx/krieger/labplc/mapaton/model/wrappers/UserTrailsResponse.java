package mx.krieger.labplc.mapaton.model.wrappers;

import java.util.List;

public class UserTrailsResponse extends CursorResponse {
	private List<TrailDetails> trails;

	
	
	public UserTrailsResponse(List<TrailDetails> trails, String cursor) {
		super(cursor);
		this.trails = trails;
	}

	/**
	 * @return the trails
	 */
	public List<TrailDetails> getTrails() {
		return trails;
	}

	/**
	 * @param trails the trails to set
	 */
	public void setTrails(List<TrailDetails> trails) {
		this.trails = trails;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserTrailsResponse [trails=" + trails + "]";
	}
	
	
	
}
