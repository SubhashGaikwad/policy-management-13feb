package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agency.
 */
@Entity
@Table(name = "agency")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "branch")
    private String branch;

    @Column(name = "brnach_code")
    private String brnachCode;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "company_type_id")
    private Long companyTypeId;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "contact_no")
    private String contactNo;

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
    @OneToOne(mappedBy = "agency")
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agency id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Agency name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public Agency address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBranch() {
        return this.branch;
    }

    public Agency branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBrnachCode() {
        return this.brnachCode;
    }

    public Agency brnachCode(String brnachCode) {
        this.setBrnachCode(brnachCode);
        return this;
    }

    public void setBrnachCode(String brnachCode) {
        this.brnachCode = brnachCode;
    }

    public String getEmail() {
        return this.email;
    }

    public Agency email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyTypeId() {
        return this.companyTypeId;
    }

    public Agency companyTypeId(Long companyTypeId) {
        this.setCompanyTypeId(companyTypeId);
        return this;
    }

    public void setCompanyTypeId(Long companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Agency imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public Agency contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Agency lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Agency lastModifiedBy(String lastModifiedBy) {
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
            this.policy.setAgency(null);
        }
        if (policy != null) {
            policy.setAgency(this);
        }
        this.policy = policy;
    }

    public Agency policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agency)) {
            return false;
        }
        return id != null && id.equals(((Agency) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agency{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", branch='" + getBranch() + "'" +
            ", brnachCode='" + getBrnachCode() + "'" +
            ", email='" + getEmail() + "'" +
            ", companyTypeId=" + getCompanyTypeId() +
            ", imageUrl='" + getImageUrl() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
