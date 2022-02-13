package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Nominee} entity.
 */
public class NomineeDTO implements Serializable {

    private Long id;

    private Long name;

    private String relation;

    private Long nomineePercentage;

    private Long contactNo;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    private PolicyDTO policy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Long getNomineePercentage() {
        return nomineePercentage;
    }

    public void setNomineePercentage(Long nomineePercentage) {
        this.nomineePercentage = nomineePercentage;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
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

    public PolicyDTO getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyDTO policy) {
        this.policy = policy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NomineeDTO)) {
            return false;
        }

        NomineeDTO nomineeDTO = (NomineeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nomineeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NomineeDTO{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", relation='" + getRelation() + "'" +
            ", nomineePercentage=" + getNomineePercentage() +
            ", contactNo=" + getContactNo() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", policy=" + getPolicy() +
            "}";
    }
}
