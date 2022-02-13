package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "plan_no")
    private Long planNo;

    @Column(name = "uin_no")
    private String uinNo;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "productType", "product" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductDetails productDetails;

    @JsonIgnoreProperties(
        value = { "agency", "company", "product", "premiunDetails", "vehicleClass", "bankDetails", "nominees", "members", "policyUsers" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "product")
    private Policy policy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "companyType", "products", "addresses", "policy" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPlanNo() {
        return this.planNo;
    }

    public Product planNo(Long planNo) {
        this.setPlanNo(planNo);
        return this;
    }

    public void setPlanNo(Long planNo) {
        this.planNo = planNo;
    }

    public String getUinNo() {
        return this.uinNo;
    }

    public Product uinNo(String uinNo) {
        this.setUinNo(uinNo);
        return this;
    }

    public void setUinNo(String uinNo) {
        this.uinNo = uinNo;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Product lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ProductDetails getProductDetails() {
        return this.productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public Product productDetails(ProductDetails productDetails) {
        this.setProductDetails(productDetails);
        return this;
    }

    public Policy getPolicy() {
        return this.policy;
    }

    public void setPolicy(Policy policy) {
        if (this.policy != null) {
            this.policy.setProduct(null);
        }
        if (policy != null) {
            policy.setProduct(this);
        }
        this.policy = policy;
    }

    public Product policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Product company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", planNo=" + getPlanNo() +
            ", uinNo='" + getUinNo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
