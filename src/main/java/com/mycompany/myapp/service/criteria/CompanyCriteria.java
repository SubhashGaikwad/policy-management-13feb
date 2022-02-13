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
 * Criteria class for the {@link com.mycompany.myapp.domain.Company} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter address;

    private StringFilter branch;

    private StringFilter brnachCode;

    private StringFilter email;

    private StringFilter imageUrl;

    private StringFilter contactNo;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter companyTypeId;

    private LongFilter productId;

    private LongFilter addressId;

    private LongFilter policyId;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.brnachCode = other.brnachCode == null ? null : other.brnachCode.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.companyTypeId = other.companyTypeId == null ? null : other.companyTypeId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getBranch() {
        return branch;
    }

    public StringFilter branch() {
        if (branch == null) {
            branch = new StringFilter();
        }
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
    }

    public StringFilter getBrnachCode() {
        return brnachCode;
    }

    public StringFilter brnachCode() {
        if (brnachCode == null) {
            brnachCode = new StringFilter();
        }
        return brnachCode;
    }

    public void setBrnachCode(StringFilter brnachCode) {
        this.brnachCode = brnachCode;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StringFilter getContactNo() {
        return contactNo;
    }

    public StringFilter contactNo() {
        if (contactNo == null) {
            contactNo = new StringFilter();
        }
        return contactNo;
    }

    public void setContactNo(StringFilter contactNo) {
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

    public LongFilter getCompanyTypeId() {
        return companyTypeId;
    }

    public LongFilter companyTypeId() {
        if (companyTypeId == null) {
            companyTypeId = new LongFilter();
        }
        return companyTypeId;
    }

    public void setCompanyTypeId(LongFilter companyTypeId) {
        this.companyTypeId = companyTypeId;
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

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(address, that.address) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(brnachCode, that.brnachCode) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(companyTypeId, that.companyTypeId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(policyId, that.policyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            address,
            branch,
            brnachCode,
            email,
            imageUrl,
            contactNo,
            lastModified,
            lastModifiedBy,
            companyTypeId,
            productId,
            addressId,
            policyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (branch != null ? "branch=" + branch + ", " : "") +
            (brnachCode != null ? "brnachCode=" + brnachCode + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (companyTypeId != null ? "companyTypeId=" + companyTypeId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
