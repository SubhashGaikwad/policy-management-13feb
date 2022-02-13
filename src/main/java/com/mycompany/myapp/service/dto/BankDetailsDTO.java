package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BankDetails} entity.
 */
public class BankDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private String branch;

    private String branchCode;

    private Long city;

    private Long contactNo;

    private String ifcCode;

    private String account;

    private String accountType;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public String getIfcCode() {
        return ifcCode;
    }

    public void setIfcCode(String ifcCode) {
        this.ifcCode = ifcCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
        if (!(o instanceof BankDetailsDTO)) {
            return false;
        }

        BankDetailsDTO bankDetailsDTO = (BankDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bankDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetailsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", branch='" + getBranch() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            ", city=" + getCity() +
            ", contactNo=" + getContactNo() +
            ", ifcCode='" + getIfcCode() + "'" +
            ", account='" + getAccount() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
