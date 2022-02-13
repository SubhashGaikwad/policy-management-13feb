package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ProductDetails} entity.
 */
public class ProductDetailsDTO implements Serializable {

    private Long id;

    private String details;

    private String featurs;

    @NotNull
    private String activationDate;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    private ProductTypeDTO productType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getFeaturs() {
        return featurs;
    }

    public void setFeaturs(String featurs) {
        this.featurs = featurs;
    }

    public String getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
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

    public ProductTypeDTO getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeDTO productType) {
        this.productType = productType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDetailsDTO)) {
            return false;
        }

        ProductDetailsDTO productDetailsDTO = (ProductDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDetailsDTO{" +
            "id=" + getId() +
            ", details='" + getDetails() + "'" +
            ", featurs='" + getFeaturs() + "'" +
            ", activationDate='" + getActivationDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", productType=" + getProductType() +
            "}";
    }
}
