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
 * Criteria class for the {@link com.mycompany.myapp.domain.Address} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter area;

    private StringFilter landmark;

    private StringFilter taluka;

    private StringFilter district;

    private StringFilter state;

    private LongFilter pincode;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter policyUsersId;

    private LongFilter companyId;

    private Boolean distinct;

    public AddressCriteria() {}

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.landmark = other.landmark == null ? null : other.landmark.copy();
        this.taluka = other.taluka == null ? null : other.taluka.copy();
        this.district = other.district == null ? null : other.district.copy();
        this.state = other.state == null ? null : other.state.copy();
        this.pincode = other.pincode == null ? null : other.pincode.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.policyUsersId = other.policyUsersId == null ? null : other.policyUsersId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
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

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter getLandmark() {
        return landmark;
    }

    public StringFilter landmark() {
        if (landmark == null) {
            landmark = new StringFilter();
        }
        return landmark;
    }

    public void setLandmark(StringFilter landmark) {
        this.landmark = landmark;
    }

    public StringFilter getTaluka() {
        return taluka;
    }

    public StringFilter taluka() {
        if (taluka == null) {
            taluka = new StringFilter();
        }
        return taluka;
    }

    public void setTaluka(StringFilter taluka) {
        this.taluka = taluka;
    }

    public StringFilter getDistrict() {
        return district;
    }

    public StringFilter district() {
        if (district == null) {
            district = new StringFilter();
        }
        return district;
    }

    public void setDistrict(StringFilter district) {
        this.district = district;
    }

    public StringFilter getState() {
        return state;
    }

    public StringFilter state() {
        if (state == null) {
            state = new StringFilter();
        }
        return state;
    }

    public void setState(StringFilter state) {
        this.state = state;
    }

    public LongFilter getPincode() {
        return pincode;
    }

    public LongFilter pincode() {
        if (pincode == null) {
            pincode = new LongFilter();
        }
        return pincode;
    }

    public void setPincode(LongFilter pincode) {
        this.pincode = pincode;
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

    public LongFilter getPolicyUsersId() {
        return policyUsersId;
    }

    public LongFilter policyUsersId() {
        if (policyUsersId == null) {
            policyUsersId = new LongFilter();
        }
        return policyUsersId;
    }

    public void setPolicyUsersId(LongFilter policyUsersId) {
        this.policyUsersId = policyUsersId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final AddressCriteria that = (AddressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(area, that.area) &&
            Objects.equals(landmark, that.landmark) &&
            Objects.equals(taluka, that.taluka) &&
            Objects.equals(district, that.district) &&
            Objects.equals(state, that.state) &&
            Objects.equals(pincode, that.pincode) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(policyUsersId, that.policyUsersId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            area,
            landmark,
            taluka,
            district,
            state,
            pincode,
            lastModified,
            lastModifiedBy,
            policyUsersId,
            companyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (landmark != null ? "landmark=" + landmark + ", " : "") +
            (taluka != null ? "taluka=" + taluka + ", " : "") +
            (district != null ? "district=" + district + ", " : "") +
            (state != null ? "state=" + state + ", " : "") +
            (pincode != null ? "pincode=" + pincode + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (policyUsersId != null ? "policyUsersId=" + policyUsersId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
