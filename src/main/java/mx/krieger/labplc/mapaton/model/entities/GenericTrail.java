/**
 * 28 Jul 2015 - 17:26:37
 */
package mx.krieger.labplc.mapaton.model.entities;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import mx.krieger.internal.apicommons.model.entities.PersistentEntity;

/**
 * This entity class persists the information of a trail.
 * @author JJMS (juanjo@krieger.mx)
 * @since 16 / feb / 2016
 * @version 1.0.0.0
 */
@Entity
public class GenericTrail extends PersistentEntity{
	@Index protected Ref<Route> route;
	@Index protected Ref<Platform> origin;
	@Index protected Ref<Platform> destination;
	@Index protected Ref<Branch> branch;
	@Index protected boolean isRegistered = false;

	/**
	 * This is the default constructor used to create an empty instance of a
	 * known trail.
	 */
	public GenericTrail(){
		super();
	}

	/**
	 * This is the [] constructor used to create a [] instance of [].
	 * @param route
	 * @param origin
	 * @param destination
	 * @param branch
	 */
	public GenericTrail(
		Ref<Route> route, Ref<Platform> origin, Ref<Platform> destination,
		Ref<Branch> branch){
		super();
		this.route = route;
		this.origin = origin;
		this.destination = destination;
		this.branch = branch;
	}

	/**
	 * @return the route
	 */
	public Ref<Route> getRoute(){
		return route;
	}

	/**
	 * @param route
	 *            the route to set
	 */
	public void setRoute(Ref<Route> route){
		this.route = route;
	}

	/**
	 * @return the origin
	 */
	public Ref<Platform> getOrigin(){
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(Ref<Platform> origin){
		this.origin = origin;
	}

	/**
	 * @return the destination
	 */
	public Ref<Platform> getDestination(){
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(Ref<Platform> destination){
		this.destination = destination;
	}

	/**
	 * @return the branch
	 */
	public Ref<Branch> getBranch(){
		return branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(Ref<Branch> branch){
		this.branch = branch;
	}

	/**
	 * @return the isRegistered
	 */
	public boolean isRegistered(){
		return isRegistered;
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("GenericTrail [");
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
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result
			+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + (isRegistered ? 1231 : 1237);
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
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
		if(!(obj instanceof GenericTrail)){
			return false;
		}
		GenericTrail other = (GenericTrail) obj;
		if(branch == null){
			if(other.branch != null){
				return false;
			}
		}else if(!branch.equals(other.branch)){
			return false;
		}
		if(destination == null){
			if(other.destination != null){
				return false;
			}
		}else if(!destination.equals(other.destination)){
			return false;
		}
		if(isRegistered != other.isRegistered){
			return false;
		}
		if(origin == null){
			if(other.origin != null){
				return false;
			}
		}else if(!origin.equals(other.origin)){
			return false;
		}
		if(route == null){
			if(other.route != null){
				return false;
			}
		}else if(!route.equals(other.route)){
			return false;
		}
		return true;
	}

	public String getName(){
		StringBuilder builder = new StringBuilder()
			.append(this.origin.get().getStation().getName()).append(" - ")
			.append(this.destination.get().getStation().getName());
		return builder.toString();
	}

}
