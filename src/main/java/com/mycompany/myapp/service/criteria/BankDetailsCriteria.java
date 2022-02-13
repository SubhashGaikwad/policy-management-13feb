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
 * Criteria class for the {@link com.mycompany.myapp.domain.BankDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.BankDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bank-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BankDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter branch;

    private StringFilter branchCode;

    private LongFilter city;

    private LongFilter contactNo;

    private StringFilter ifcCode;

    private StringFilter account;

    private StringFilter accountType;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter policyId;

    private Boolean distinct;

    public BankDetailsCriteria() {}

    public BankDetailsCriteria(BankDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.branchCode = other.branchCode == null ? null : other.branchCode.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.ifcCode = other.ifcCode == null ? null : other.ifcCode.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BankDetailsCriteria copy() {
        return new BankDetailsCriteria(this);
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

    public StringFilter getBranchCode() {
        return branchCode;
    }

    public StringFilter branchCode() {
        if (branchCode == null) {
            branchCode = new StringFilter();
        }
        return branchCode;
    }

    public void setBranchCode(StringFilter branchCode) {
        this.branchCode = branchCode;
    }

    public LongFilter getCity() {
        return city;
    }

    public LongFilter city() {
        if (city == null) {
            city = new LongFilter();
        }
        return city;
    }

    public void setCity(LongFilter city) {
        this.city = city;
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

    public StringFilter getIfcCode() {
        return ifcCode;
    }

    public StringFilter ifcCode() {
        if (ifcCode == null) {
            ifcCode = new StringFilter();
        }
        return ifcCode;
    }

    public void setIfcCode(StringFilter ifcCode) {
        this.ifcCode = ifcCode;
    }

    public StringFilter getAccount() {
        return account;
    }

    public StringFilter account() {
        if (account == null) {
            account = new StringFilter();
        }
        return account;
    }

    public void setAccount(StringFilter account) {
        this.account = account;
    }

    public StringFilter getAccountType() {
        return accountType;
    }

    public StringFilter accountType() {
        if (accountType == null) {
            accountType = new StringFilter();
        }
        return accountType;
    }

    public void setAccountType(StringFilter accountType) {
        this.accountType = accountType;
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
        final BankDetailsCriteria that = (BankDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(branchCode, that.branchCode) &&
            Objects.equals(city, that.city) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(ifcCode, that.ifcCode) &&
            Objects.equals(account, that.account) &&
            Objects.equals(accountType, that.accountType) &&
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
            name,
            branch,
            branchCode,
            city,
            contactNo,
            ifcCode,
            account,
            accountType,
            lastModified,
            lastModifiedBy,
            policyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (branch != null ? "branch=" + branch + ", " : "") +
            (branchCode != null ? "branchCode=" + branchCode + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (ifcCode != null ? "ifcCode=" + ifcCode + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (accountType != null ? "accountType=" + accountType + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
