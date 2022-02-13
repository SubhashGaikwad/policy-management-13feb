package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ProductDetails.
 */
@Entity
@Table(name = "product_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "details")
    private String details;

    @Column(name = "featurs")
    private String featurs;

    @NotNull
    @Column(name = "activation_date", nullable = false)
    private String activationDate;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "productDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private ProductType productType;

    @JsonIgnoreProperties(value = { "productDetails", "policy", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "productDetails")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ProductDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return this.details;
    }

    public ProductDetails details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFeaturs() {
        return this.featurs;
    }

    public ProductDetails featurs(String featurs) {
        this.setFeaturs(featurs);
        return this;
    }

    public void setFeaturs(String featurs) {
        this.featurs = featurs;
    }

    public String getActivationDate() {
        return this.activationDate;
    }

    public ProductDetails activationDate(String activationDate) {
        this.setActivationDate(activationDate);
        return this;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public ProductDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public ProductDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public ProductDetails productType(ProductType productType) {
        this.setProductType(productType);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        if (this.product != null) {
            this.product.setProductDetails(null);
        }
        if (product != null) {
            product.setProductDetails(this);
        }
        this.product = product;
    }

    public ProductDetails product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetails)) {
            return false;
        }
        return id != null && id.equals(((ProductDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetails{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", featurs='" + getFeaturs() + "'" +
            ", activationDate='" + getActivationDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
