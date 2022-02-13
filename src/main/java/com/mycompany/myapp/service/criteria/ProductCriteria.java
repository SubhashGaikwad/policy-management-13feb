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
 * Criteria class for the {@link com.mycompany.myapp.domain.Product} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private LongFilter planNo;

    private StringFilter uinNo;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter productDetailsId;

    private LongFilter policyId;

    private LongFilter companyId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.planNo = other.planNo == null ? null : other.planNo.copy();
        this.uinNo = other.uinNo == null ? null : other.uinNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.productDetailsId = other.productDetailsId == null ? null : other.productDetailsId.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public LongFilter getPlanNo() {
        return planNo;
    }

    public LongFilter planNo() {
        if (planNo == null) {
            planNo = new LongFilter();
        }
        return planNo;
    }

    public void setPlanNo(LongFilter planNo) {
        this.planNo = planNo;
    }

    public StringFilter getUinNo() {
        return uinNo;
    }

    public StringFilter uinNo() {
        if (uinNo == null) {
            uinNo = new StringFilter();
        }
        return uinNo;
    }

    public void setUinNo(StringFilter uinNo) {
        this.uinNo = uinNo;
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

    public LongFilter getProductDetailsId() {
        return productDetailsId;
    }

    public LongFilter productDetailsId() {
        if (productDetailsId == null) {
            productDetailsId = new LongFilter();
        }
        return productDetailsId;
    }

    public void setProductDetailsId(LongFilter productDetailsId) {
        this.productDetailsId = productDetailsId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(planNo, that.planNo) &&
            Objects.equals(uinNo, that.uinNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(productDetailsId, that.productDetailsId) &&
            Objects.equals(policyId, that.policyId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, planNo, uinNo, lastModified, lastModifiedBy, productDetailsId, policyId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (planNo != null ? "planNo=" + planNo + ", " : "") +
            (uinNo != null ? "uinNo=" + uinNo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (productDetailsId != null ? "productDetailsId=" + productDetailsId + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
