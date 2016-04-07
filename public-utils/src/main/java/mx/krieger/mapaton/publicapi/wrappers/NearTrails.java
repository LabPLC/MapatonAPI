package mx.krieger.mapaton.publicapi.wrappers;

import mx.krieger.mapaton.publicapi.model.entities.RegisteredTrail;

/**
 * This class wraps the fields required by the near trails.
 * Created by JuanJose on 06/04/2016.
 */
public class NearTrails {
    private Long trailId;
    private String branchName;
    private String originName;
    private String destinationName;

    public NearTrails() {
    }

    public NearTrails(Long trailId, String branchName, String originName, String destinationName) {
        this.trailId = trailId;
        this.branchName = branchName;
        this.originName = originName;
        this.destinationName = destinationName;
    }

    public NearTrails(RegisteredTrail registeredTrail) {
        this.trailId = registeredTrail.getId();
        this.branchName = registeredTrail.getBranch().getValue().getName();
        this.originName = registeredTrail.getOrigin().get().getStation().getDisplayName();
        this.destinationName = registeredTrail.getDestination().get().getStation().getDisplayName();
    }

    public Long getTrailId() {
        return trailId;
    }

    public void setTrailId(Long trailId) {
        this.trailId = trailId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NearTrails{");
        sb.append(super.toString());
        sb.append("trailId=").append(trailId);
        sb.append(", branchName='").append(branchName).append('\'');
        sb.append(", originName='").append(originName).append('\'');
        sb.append(", destinationName='").append(destinationName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NearTrails that = (NearTrails) o;

        if (getTrailId() != null ? !getTrailId().equals(that.getTrailId()) : that.getTrailId() != null) return false;
        if (getBranchName() != null ? !getBranchName().equals(that.getBranchName()) : that.getBranchName() != null)
            return false;
        if (getOriginName() != null ? !getOriginName().equals(that.getOriginName()) : that.getOriginName() != null)
            return false;
        return getDestinationName() != null ? getDestinationName().equals(that.getDestinationName()) : that.getDestinationName() == null;

    }

    @Override
    public int hashCode() {
        int result = getTrailId() != null ? getTrailId().hashCode() : 0;
        result = 31 * result + (getBranchName() != null ? getBranchName().hashCode() : 0);
        result = 31 * result + (getOriginName() != null ? getOriginName().hashCode() : 0);
        result = 31 * result + (getDestinationName() != null ? getDestinationName().hashCode() : 0);
        return result;
    }
}
