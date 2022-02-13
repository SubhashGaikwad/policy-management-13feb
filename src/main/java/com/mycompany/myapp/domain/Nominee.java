package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nominee.
 */
@Entity
@Table(name = "nominee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nominee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private Long name;

    @Column(name = "relation")
    private String relation;

    @Column(name = "nominee_percentage")
    private Long nomineePercentage;

    @Column(name = "contact_no")
    private Long contactNo;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "agency", "company", "product", "premiunDetails", "vehicleClass", "bankDetails", "nominees", "members", "policyUsers" },
        allowSetters = true
    )
    private Policy policy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nominee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return this.name;
    }

    public Nominee name(Long name) {
        this.setName(name);
        return this;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getRelation() {
        return this.relation;
    }

    public Nominee relation(String relation) {
        this.setRelation(relation);
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Long getNomineePercentage() {
        return this.nomineePercentage;
    }

    public Nominee nomineePercentage(Long nomineePercentage) {
        this.setNomineePercentage(nomineePercentage);
        return this;
    }

    public void setNomineePercentage(Long nomineePercentage) {
        this.nomineePercentage = nomineePercentage;
    }

    public Long getContactNo() {
        return this.contactNo;
    }

    public Nominee contactNo(Long contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Nominee lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Nominee lastModifiedBy(String lastModifiedBy) {
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
        this.policy = policy;
    }

    public Nominee policy(Policy policy) {
        this.setPolicy(policy);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nominee)) {
            return false;
        }
        return id != null && id.equals(((Nominee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nominee{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", relation='" + getRelation() + "'" +
            ", nomineePercentage=" + getNomineePercentage() +
            ", contactNo=" + getContactNo() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
