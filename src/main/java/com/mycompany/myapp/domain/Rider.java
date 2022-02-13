package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rider.
 */
@Entity
@Table(name = "rider")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "comm_date")
    private String commDate;

    @Column(name = "sum")
    private String sum;

    @Column(name = "term")
    private String term;

    @Column(name = "ppt", unique = true)
    private String ppt;

    @Column(name = "premium")
    private Long premium;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Rider name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommDate() {
        return this.commDate;
    }

    public Rider commDate(String commDate) {
        this.setCommDate(commDate);
        return this;
    }

    public void setCommDate(String commDate) {
        this.commDate = commDate;
    }

    public String getSum() {
        return this.sum;
    }

    public Rider sum(String sum) {
        this.setSum(sum);
        return this;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getTerm() {
        return this.term;
    }

    public Rider term(String term) {
        this.setTerm(term);
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getPpt() {
        return this.ppt;
    }

    public Rider ppt(String ppt) {
        this.setPpt(ppt);
        return this;
    }

    public void setPpt(String ppt) {
        this.ppt = ppt;
    }

    public Long getPremium() {
        return this.premium;
    }

    public Rider premium(Long premium) {
        this.setPremium(premium);
        return this;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Rider lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Rider lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rider)) {
            return false;
        }
        return id != null && id.equals(((Rider) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rider{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", commDate='" + getCommDate() + "'" +
            ", sum='" + getSum() + "'" +
            ", term='" + getTerm() + "'" +
            ", ppt='" + getPpt() + "'" +
            ", premium=" + getPremium() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
