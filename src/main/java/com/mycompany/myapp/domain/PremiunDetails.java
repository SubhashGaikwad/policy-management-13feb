package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PremiunDetails.
 */
@Entity
@Table(name = "premiun_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PremiunDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "premium")
    private Long premium;

    @Column(name = "other_loading")
    private Long otherLoading;

    @Column(name = "other_discount")
    private Long otherDiscount;

    @Column(name = "add_on_premium")
    private Long addOnPremium;

    @Column(name = "liability_premium")
    private Long liabilityPremium;

    @Column(name = "od_premium")
    private Long odPremium;

    @Column(name = "personal_accident_discount")
    private Boolean personalAccidentDiscount;

    @Column(name = "personal_accident")
    private Long personalAccident;

    @Column(name = "gross_premium")
    private Long grossPremium;

    @Column(name = "gst")
    private Long gst;

    @Column(name = "net_premium")
    private Long netPremium;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(
        value = { "agency", "company", "product", "premiunDetails", "vehicleClass", "bankDetails", "nominees", "members", "policyUsers" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "premiunDetails")
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PremiunDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPremium() {
        return this.premium;
    }

    public PremiunDetails premium(Long premium) {
        this.setPremium(premium);
        return this;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public Long getOtherLoading() {
        return this.otherLoading;
    }

    public PremiunDetails otherLoading(Long otherLoading) {
        this.setOtherLoading(otherLoading);
        return this;
    }

    public void setOtherLoading(Long otherLoading) {
        this.otherLoading = otherLoading;
    }

    public Long getOtherDiscount() {
        return this.otherDiscount;
    }

    public PremiunDetails otherDiscount(Long otherDiscount) {
        this.setOtherDiscount(otherDiscount);
        return this;
    }

    public void setOtherDiscount(Long otherDiscount) {
        this.otherDiscount = otherDiscount;
    }

    public Long getAddOnPremium() {
        return this.addOnPremium;
    }

    public PremiunDetails addOnPremium(Long addOnPremium) {
        this.setAddOnPremium(addOnPremium);
        return this;
    }

    public void setAddOnPremium(Long addOnPremium) {
        this.addOnPremium = addOnPremium;
    }

    public Long getLiabilityPremium() {
        return this.liabilityPremium;
    }

    public PremiunDetails liabilityPremium(Long liabilityPremium) {
        this.setLiabilityPremium(liabilityPremium);
        return this;
    }

    public void setLiabilityPremium(Long liabilityPremium) {
        this.liabilityPremium = liabilityPremium;
    }

    public Long getOdPremium() {
        return this.odPremium;
    }

    public PremiunDetails odPremium(Long odPremium) {
        this.setOdPremium(odPremium);
        return this;
    }

    public void setOdPremium(Long odPremium) {
        this.odPremium = odPremium;
    }

    public Boolean getPersonalAccidentDiscount() {
        return this.personalAccidentDiscount;
    }

    public PremiunDetails personalAccidentDiscount(Boolean personalAccidentDiscount) {
        this.setPersonalAccidentDiscount(personalAccidentDiscount);
        return this;
    }

    public void setPersonalAccidentDiscount(Boolean personalAccidentDiscount) {
        this.personalAccidentDiscount = personalAccidentDiscount;
    }

    public Long getPersonalAccident() {
        return this.personalAccident;
    }

    public PremiunDetails personalAccident(Long personalAccident) {
        this.setPersonalAccident(personalAccident);
        return this;
    }

    public void setPersonalAccident(Long personalAccident) {
        this.personalAccident = personalAccident;
    }

    public Long getGrossPremium() {
        return this.grossPremium;
    }

    public PremiunDetails grossPremium(Long grossPremium) {
        this.setGrossPremium(grossPremium);
        return this;
    }

    public void setGrossPremium(Long grossPremium) {
        this.grossPremium = grossPremium;
    }

    public Long getGst() {
        return this.gst;
    }

    public PremiunDetails gst(Long gst) {
        this.setGst(gst);
        return this;
    }

    public void setGst(Long gst) {
        this.gst = gst;
    }

    public Long getNetPremium() {
        return this.netPremium;
    }

    public PremiunDetails netPremium(Long netPremium) {
        this.setNetPremium(netPremium);
        return this;
    }

    public void setNetPremium(Long netPremium) {
        this.netPremium = netPremium;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public PremiunDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PremiunDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy policy) {
        if (this.policy != null) {
            this.policy.setPremiunDetails(null);
        }
        if (policy != null) {
            policy.setPremiunDetails(this);
        }
        this.policy = policy;
    }

    public PremiunDetails policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremiunDetails)) {
            return false;
        }
        return id != null && id.equals(((PremiunDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PremiunDetails{" +
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
