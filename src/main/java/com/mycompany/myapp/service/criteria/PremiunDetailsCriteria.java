package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.PremiunDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PremiunDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /premiun-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PremiunDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter premium;

    private LongFilter otherLoading;

    private LongFilter otherDiscount;

    private LongFilter addOnPremium;

    private LongFilter liabilityPremium;

    private LongFilter odPremium;

    private BooleanFilter personalAccidentDiscount;

    private LongFilter personalAccident;

    private LongFilter grossPremium;

    private LongFilter gst;

    private LongFilter netPremium;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter policyId;

    private Boolean distinct;

    public PremiunDetailsCriteria() {}

    public PremiunDetailsCriteria(PremiunDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.premium = other.premium == null ? null : other.premium.copy();
        this.otherLoading = other.otherLoading == null ? null : other.otherLoading.copy();
        this.otherDiscount = other.otherDiscount == null ? null : other.otherDiscount.copy();
        this.addOnPremium = other.addOnPremium == null ? null : other.addOnPremium.copy();
        this.liabilityPremium = other.liabilityPremium == null ? null : other.liabilityPremium.copy();
        this.odPremium = other.odPremium == null ? null : other.odPremium.copy();
        this.personalAccidentDiscount = other.personalAccidentDiscount == null ? null : other.personalAccidentDiscount.copy();
        this.personalAccident = other.personalAccident == null ? null : other.personalAccident.copy();
        this.grossPremium = other.grossPremium == null ? null : other.grossPremium.copy();
        this.gst = other.gst == null ? null : other.gst.copy();
        this.netPremium = other.netPremium == null ? null : other.netPremium.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PremiunDetailsCriteria copy() {
        return new PremiunDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPremium() {
        return premium;
    }

    public LongFilter premium() {
        if (premium == null) {
            premium = new LongFilter();
        }
        return premium;
    }

    public void setPremium(LongFilter premium) {
        this.premium = premium;
    }

    public LongFilter getOtherLoading() {
        return otherLoading;
    }

    public LongFilter otherLoading() {
        if (otherLoading == null) {
            otherLoading = new LongFilter();
        }
        return otherLoading;
    }

    public void setOtherLoading(LongFilter otherLoading) {
        this.otherLoading = otherLoading;
    }

    public LongFilter getOtherDiscount() {
        return otherDiscount;
    }

    public LongFilter otherDiscount() {
        if (otherDiscount == null) {
            otherDiscount = new LongFilter();
        }
        return otherDiscount;
    }

    public void setOtherDiscount(LongFilter otherDiscount) {
        this.otherDiscount = otherDiscount;
    }

    public LongFilter getAddOnPremium() {
        return addOnPremium;
    }

    public LongFilter addOnPremium() {
        if (addOnPremium == null) {
            addOnPremium = new LongFilter();
        }
        return addOnPremium;
    }

    public void setAddOnPremium(LongFilter addOnPremium) {
        this.addOnPremium = addOnPremium;
    }

    public LongFilter getLiabilityPremium() {
        return liabilityPremium;
    }

    public LongFilter liabilityPremium() {
        if (liabilityPremium == null) {
            liabilityPremium = new LongFilter();
        }
        return liabilityPremium;
    }

    public void setLiabilityPremium(LongFilter liabilityPremium) {
        this.liabilityPremium = liabilityPremium;
    }

    public LongFilter getOdPremium() {
        return odPremium;
    }

    public LongFilter odPremium() {
        if (odPremium == null) {
            odPremium = new LongFilter();
        }
        return odPremium;
    }

    public void setOdPremium(LongFilter odPremium) {
        this.odPremium = odPremium;
    }

    public BooleanFilter getPersonalAccidentDiscount() {
        return personalAccidentDiscount;
    }

    public BooleanFilter personalAccidentDiscount() {
        if (personalAccidentDiscount == null) {
            personalAccidentDiscount = new BooleanFilter();
        }
        return personalAccidentDiscount;
    }

    public void setPersonalAccidentDiscount(BooleanFilter personalAccidentDiscount) {
        this.personalAccidentDiscount = personalAccidentDiscount;
    }

    public LongFilter getPersonalAccident() {
        return personalAccident;
    }

    public LongFilter personalAccident() {
        if (personalAccident == null) {
            personalAccident = new LongFilter();
        }
        return personalAccident;
    }

    public void setPersonalAccident(LongFilter personalAccident) {
        this.personalAccident = personalAccident;
    }

    public LongFilter getGrossPremium() {
        return grossPremium;
    }

    public LongFilter grossPremium() {
        if (grossPremium == null) {
            grossPremium = new LongFilter();
        }
        return grossPremium;
    }

    public void setGrossPremium(LongFilter grossPremium) {
        this.grossPremium = grossPremium;
    }

    public LongFilter getGst() {
        return gst;
    }

    public LongFilter gst() {
        if (gst == null) {
            gst = new LongFilter();
        }
        return gst;
    }

    public void setGst(LongFilter gst) {
        this.gst = gst;
    }

    public LongFilter getNetPremium() {
        return netPremium;
    }

    public LongFilter netPremium() {
        if (netPremium == null) {
            netPremium = new LongFilter();
        }
        return netPremium;
    }

    public void setNetPremium(LongFilter netPremium) {
        this.netPremium = netPremium;
    }

    public StringFilter getLastModified() {
        return lastModified;
    }

    public StringFilter lastModified() {
        if (lastModified == null) {
            lastModified = new StringFilter();
        }
        return lastModified;
    }

    public void setLastModified(StringFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getPolicyId() {
        return policyId;
    }

    public LongFilter policyId() {
        if (policyId == null) {
            policyId = new LongFilter();
        }
        return policyId;
    }

    public void setPolicyId(LongFilter policyId) {
        this.policyId = policyId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PremiunDetailsCriteria that = (PremiunDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(premium, that.premium) &&
            Objects.equals(otherLoading, that.otherLoading) &&
            Objects.equals(otherDiscount, that.otherDiscount) &&
            Objects.equals(addOnPremium, that.addOnPremium) &&
            Objects.equals(liabilityPremium, that.liabilityPremium) &&
            Objects.equals(odPremium, that.odPremium) &&
            Objects.equals(personalAccidentDiscount, that.personalAccidentDiscount) &&
            Objects.equals(personalAccident, that.personalAccident) &&
            Objects.equals(grossPremium, that.grossPremium) &&
            Objects.equals(gst, that.gst) &&
            Objects.equals(netPremium, that.netPremium) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(policyId, that.policyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            premium,
            otherLoading,
            otherDiscount,
            addOnPremium,
            liabilityPremium,
            odPremium,
            personalAccidentDiscount,
            personalAccident,
            grossPremium,
            gst,
            netPremium,
            lastModified,
            lastModifiedBy,
            policyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PremiunDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (premium != null ? "premium=" + premium + ", " : "") +
            (otherLoading != null ? "otherLoading=" + otherLoading + ", " : "") +
            (otherDiscount != null ? "otherDiscount=" + otherDiscount + ", " : "") +
            (addOnPremium != null ? "addOnPremium=" + addOnPremium + ", " : "") +
            (liabilityPremium != null ? "liabilityPremium=" + liabilityPremium + ", " : "") +
            (odPremium != null ? "odPremium=" + odPremium + ", " : "") +
            (personalAccidentDiscount != null ? "personalAccidentDiscount=" + personalAccidentDiscount + ", " : "") +
            (personalAccident != null ? "personalAccident=" + personalAccident + ", " : "") +
            (grossPremium != null ? "grossPremium=" + grossPremium + ", " : "") +
            (gst != null ? "gst=" + gst + ", " : "") +
            (netPremium != null ? "netPremium=" + netPremium + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
