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
 * Criteria class for the {@link com.mycompany.myapp.domain.ProductDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProductDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /product-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter details;

    private StringFilter featurs;

    private StringFilter activationDate;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter productTypeId;

    private LongFilter productId;

    private Boolean distinct;

    public ProductDetailsCriteria() {}

    public ProductDetailsCriteria(ProductDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.details = other.details == null ? null : other.details.copy();
        this.featurs = other.featurs == null ? null : other.featurs.copy();
        this.activationDate = other.activationDate == null ? null : other.activationDate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.productTypeId = other.productTypeId == null ? null : other.productTypeId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductDetailsCriteria copy() {
        return new ProductDetailsCriteria(this);
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

    public StringFilter getDetails() {
        return details;
    }

    public StringFilter details() {
        if (details == null) {
            details = new StringFilter();
        }
        return details;
    }

    public void setDetails(StringFilter details) {
        this.details = details;
    }

    public StringFilter getFeaturs() {
        return featurs;
    }

    public StringFilter featurs() {
        if (featurs == null) {
            featurs = new StringFilter();
        }
        return featurs;
    }

    public void setFeaturs(StringFilter featurs) {
        this.featurs = featurs;
    }

    public StringFilter getActivationDate() {
        return activationDate;
    }

    public StringFilter activationDate() {
        if (activationDate == null) {
            activationDate = new StringFilter();
        }
        return activationDate;
    }

    public void setActivationDate(StringFilter activationDate) {
        this.activationDate = activationDate;
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

    public LongFilter getProductTypeId() {
        return productTypeId;
    }

    public LongFilter productTypeId() {
        if (productTypeId == null) {
            productTypeId = new LongFilter();
        }
        return productTypeId;
    }

    public void setProductTypeId(LongFilter productTypeId) {
        this.productTypeId = productTypeId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
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
        final ProductDetailsCriteria that = (ProductDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(details, that.details) &&
            Objects.equals(featurs, that.featurs) &&
            Objects.equals(activationDate, that.activationDate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(productTypeId, that.productTypeId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, featurs, activationDate, lastModified, lastModifiedBy, productTypeId, productId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (details != null ? "details=" + details + ", " : "") +
            (featurs != null ? "featurs=" + featurs + ", " : "") +
            (activationDate != null ? "activationDate=" + activationDate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (productTypeId != null ? "productTypeId=" + productTypeId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
