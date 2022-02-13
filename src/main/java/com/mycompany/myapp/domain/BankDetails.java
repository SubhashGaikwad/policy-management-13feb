package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankDetails.
 */
@Entity
@Table(name = "bank_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "branch")
    private String branch;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "city")
    private Long city;

    @Column(name = "contact_no")
    private Long contactNo;

    @Column(name = "ifc_code")
    private String ifcCode;

    @Column(name = "account")
    private String account;

    @Column(name = "account_type")
    private String accountType;

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
    @OneToOne(mappedBy = "bankDetails")
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BankDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BankDetails name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return this.branch;
    }

    public BankDetails branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranchCode() {
        return this.branchCode;
    }

    public BankDetails branchCode(String branchCode) {
        this.setBranchCode(branchCode);
        return this;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public Long getCity() {
        return this.city;
    }

    public BankDetails city(Long city) {
        this.setCity(city);
        return this;
    }

    public void setCity(Long city) {
        this.city = city;
    }

    public Long getContactNo() {
        return this.contactNo;
    }

    public BankDetails contactNo(Long contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public String getIfcCode() {
        return this.ifcCode;
    }

    public BankDetails ifcCode(String ifcCode) {
        this.setIfcCode(ifcCode);
        return this;
    }

    public void setIfcCode(String ifcCode) {
        this.ifcCode = ifcCode;
    }

    public String getAccount() {
        return this.account;
    }

    public BankDetails account(String account) {
        this.setAccount(account);
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public BankDetails accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public BankDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public BankDetails lastModifiedBy(String lastModifiedBy) {
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
            this.policy.setBankDetails(null);
        }
        if (policy != null) {
            policy.setBankDetails(this);
        }
        this.policy = policy;
    }

    public BankDetails policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankDetails)) {
            return false;
        }
        return id != null && id.equals(((BankDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetails{" +
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
