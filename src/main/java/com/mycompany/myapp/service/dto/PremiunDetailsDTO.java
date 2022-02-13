package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PremiunDetails} entity.
 */
public class PremiunDetailsDTO implements Serializable {

    private Long id;

    private Long premium;

    private Long otherLoading;

    private Long otherDiscount;

    private Long addOnPremium;

    private Long liabilityPremium;

    private Long odPremium;

    private Boolean personalAccidentDiscount;

    private Long personalAccident;

    private Long grossPremium;

    private Long gst;

    private Long netPremium;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPremium() {
        return premium;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public Long getOtherLoading() {
        return otherLoading;
    }

    public void setOtherLoading(Long otherLoading) {
        this.otherLoading = otherLoading;
    }

    public Long getOtherDiscount() {
        return otherDiscount;
    }

    public void setOtherDiscount(Long otherDiscount) {
        this.otherDiscount = otherDiscount;
    }

    public Long getAddOnPremium() {
        return addOnPremium;
    }

    public void setAddOnPremium(Long addOnPremium) {
        this.addOnPremium = addOnPremium;
    }

    public Long getLiabilityPremium() {
        return liabilityPremium;
    }

    public void setLiabilityPremium(Long liabilityPremium) {
        this.liabilityPremium = liabilityPremium;
    }

    public Long getOdPremium() {
        return odPremium;
    }

    public void setOdPremium(Long odPremium) {
        this.odPremium = odPremium;
    }

    public Boolean getPersonalAccidentDiscount() {
        return personalAccidentDiscount;
    }

    public void setPersonalAccidentDiscount(Boolean personalAccidentDiscount) {
        this.personalAccidentDiscount = personalAccidentDiscount;
    }

    public Long getPersonalAccident() {
        return personalAccident;
    }

    public void setPersonalAccident(Long personalAccident) {
        this.personalAccident = personalAccident;
    }

    public Long getGrossPremium() {
        return grossPremium;
    }

    public void setGrossPremium(Long grossPremium) {
        this.grossPremium = grossPremium;
    }

    public Long getGst() {
        return gst;
    }

    public void setGst(Long gst) {
        this.gst = gst;
    }

    public Long getNetPremium() {
        return netPremium;
    }

    public void setNetPremium(Long netPremium) {
        this.netPremium = netPremium;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremiunDetailsDTO)) {
            return false;
        }

        PremiunDetailsDTO premiunDetailsDTO = (PremiunDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, premiunDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PremiunDetailsDTO{" +
            "id=" + getId() +
            ", premium=" + getPremium() +
            ", otherLoading=" + getOtherLoading() +
            ", otherDiscount=" + getOtherDiscount() +
            ", addOnPremium=" + getAddOnPremium() +
            ", liabilityPremium=" + getLiabilityPremium() +
            ", odPremium=" + getOdPremium() +
            ", personalAccidentDiscount='" + getPersonalAccidentDiscount() + "'" +
            ", personalAccident=" + getPersonalAccident() +
            ", grossPremium=" + getGrossPremium() +
            ", gst=" + getGst() +
            ", netPremium=" + getNetPremium() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
