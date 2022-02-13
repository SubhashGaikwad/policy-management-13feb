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
 * Criteria class for the {@link com.mycompany.myapp.domain.Rider} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RiderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /riders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RiderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter commDate;

    private StringFilter sum;

    private StringFilter term;

    private StringFilter ppt;

    private LongFilter premium;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private Boolean distinct;

    public RiderCriteria() {}

    public RiderCriteria(RiderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.commDate = other.commDate == null ? null : other.commDate.copy();
        this.sum = other.sum == null ? null : other.sum.copy();
        this.term = other.term == null ? null : other.term.copy();
        this.ppt = other.ppt == null ? null : other.ppt.copy();
        this.premium = other.premium == null ? null : other.premium.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RiderCriteria copy() {
        return new RiderCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getCommDate() {
        return commDate;
    }

    public StringFilter commDate() {
        if (commDate == null) {
            commDate = new StringFilter();
        }
        return commDate;
    }

    public void setCommDate(StringFilter commDate) {
        this.commDate = commDate;
    }

    public StringFilter getSum() {
        return sum;
    }

    public StringFilter sum() {
        if (sum == null) {
            sum = new StringFilter();
        }
        return sum;
    }

    public void setSum(StringFilter sum) {
        this.sum = sum;
    }

    public StringFilter getTerm() {
        return term;
    }

    public StringFilter term() {
        if (term == null) {
            term = new StringFilter();
        }
        return term;
    }

    public void setTerm(StringFilter term) {
        this.term = term;
    }

    public StringFilter getPpt() {
        return ppt;
    }

    public StringFilter ppt() {
        if (ppt == null) {
            ppt = new StringFilter();
        }
        return ppt;
    }

    public void setPpt(StringFilter ppt) {
        this.ppt = ppt;
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
        final RiderCriteria that = (RiderCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(commDate, that.commDate) &&
            Objects.equals(sum, that.sum) &&
            Objects.equals(term, that.term) &&
            Objects.equals(ppt, that.ppt) &&
            Objects.equals(premium, that.premium) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, commDate, sum, term, ppt, premium, lastModified, lastModifiedBy, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RiderCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (commDate != null ? "commDate=" + commDate + ", " : "") +
            (sum != null ? "sum=" + sum + ", " : "") +
            (term != null ? "term=" + term + ", " : "") +
            (ppt != null ? "ppt=" + ppt + ", " : "") +
            (premium != null ? "premium=" + premium + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
