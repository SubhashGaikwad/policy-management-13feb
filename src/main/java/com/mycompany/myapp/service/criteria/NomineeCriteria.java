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
 * Criteria class for the {@link com.mycompany.myapp.domain.Nominee} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.NomineeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /nominees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NomineeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter name;

    private StringFilter relation;

    private LongFilter nomineePercentage;

    private LongFilter contactNo;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter policyId;

    private Boolean distinct;

    public NomineeCriteria() {}

    public NomineeCriteria(NomineeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.relation = other.relation == null ? null : other.relation.copy();
        this.nomineePercentage = other.nomineePercentage == null ? null : other.nomineePercentage.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NomineeCriteria copy() {
        return new NomineeCriteria(this);
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

    public LongFilter getName() {
        return name;
    }

    public LongFilter name() {
        if (name == null) {
            name = new LongFilter();
        }
        return name;
    }

    public void setName(LongFilter name) {
        this.name = name;
    }

    public StringFilter getRelation() {
        return relation;
    }

    public StringFilter relation() {
        if (relation == null) {
            relation = new StringFilter();
        }
        return relation;
    }

    public void setRelation(StringFilter relation) {
        this.relation = relation;
    }

    public LongFilter getNomineePercentage() {
        return nomineePercentage;
    }

    public LongFilter nomineePercentage() {
        if (nomineePercentage == null) {
            nomineePercentage = new LongFilter();
        }
        return nomineePercentage;
    }

    public void setNomineePercentage(LongFilter nomineePercentage) {
        this.nomineePercentage = nomineePercentage;
    }

    public LongFilter getContactNo() {
        return contactNo;
    }

    public LongFilter contactNo() {
        if (contactNo == null) {
            contactNo = new LongFilter();
        }
        return contactNo;
    }

    public void setContactNo(LongFilter contactNo) {
        this.contactNo = contactNo;
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
        final NomineeCriteria that = (NomineeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(relation, that.relation) &&
            Objects.equals(nomineePercentage, that.nomineePercentage) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(policyId, that.policyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, relation, nomineePercentage, contactNo, lastModified, lastModifiedBy, policyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NomineeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (relation != null ? "relation=" + relation + ", " : "") +
            (nomineePercentage != null ? "nomineePercentage=" + nomineePercentage + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
