package mx.krieger.mapaton.publicutils.wrappers;

import java.util.List;

/**
 * The Class TrailListResponse.
 * This class is a paginated response of the trails that are registered in the datastore
 */
public class TrailListResponse extends CursorResponse {
	private List<TrailDetails> trails;

	
	
	/**
	 * Instantiates a new trail list response.
	 *
	 * @param trails the trails
	 * @param cursor the cursor
	 */
	public TrailListResponse(List<TrailDetails> trails, String cursor) {
		super(cursor);
		this.trails = trails;
	}

	/**
	 * Gets the trails.
	 *
	 * @return the trails
	 */
	public List<TrailDetails> getTrails() {
		return trails;
	}

	/**
	 * Sets the trails.
	 *
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
