package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Zone;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VehicleDetails.
 */
@Entity
@Table(name = "vehicle_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VehicleDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private Long name;

    @Column(name = "invoice_value")
    private String invoiceValue;

    @Column(name = "idv")
    private String idv;

    @Column(name = "engin_number")
    private String enginNumber;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "seating_capacity")
    private Long seatingCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone")
    private Zone zone;

    @Column(name = "year_of_manufacturing")
    private String yearOfManufacturing;

    @Column(name = "registration_date")
    private String registrationDate;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @OneToMany(mappedBy = "vehicleDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "vehicleDetails" }, allowSetters = true)
    private Set<ParameterLookup> parameterLookups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VehicleDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getName() {
        return this.name;
    }

    public VehicleDetails name(Long name) {
        this.setName(name);
        return this;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getInvoiceValue() {
        return this.invoiceValue;
    }

    public VehicleDetails invoiceValue(String invoiceValue) {
        this.setInvoiceValue(invoiceValue);
        return this;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getIdv() {
        return this.idv;
    }

    public VehicleDetails idv(String idv) {
        this.setIdv(idv);
        return this;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public String getEnginNumber() {
        return this.enginNumber;
    }

    public VehicleDetails enginNumber(String enginNumber) {
        this.setEnginNumber(enginNumber);
        return this;
    }

    public void setEnginNumber(String enginNumber) {
        this.enginNumber = enginNumber;
    }

    public String getChassisNumber() {
        return this.chassisNumber;
    }

    public VehicleDetails chassisNumber(String chassisNumber) {
        this.setChassisNumber(chassisNumber);
        return this;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public VehicleDetails registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public VehicleDetails seatingCapacity(Long seatingCapacity) {
        this.setSeatingCapacity(seatingCapacity);
        return this;
    }

    public void setSeatingCapacity(Long seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Zone getZone() {
        return this.zone;
    }

    public VehicleDetails zone(Zone zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getYearOfManufacturing() {
        return this.yearOfManufacturing;
    }

    public VehicleDetails yearOfManufacturing(String yearOfManufacturing) {
        this.setYearOfManufacturing(yearOfManufacturing);
        return this;
    }

    public void setYearOfManufacturing(String yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }

    public String getRegistrationDate() {
        return this.registrationDate;
    }

    public VehicleDetails registrationDate(String registrationDate) {
        this.setRegistrationDate(registrationDate);
        return this;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public VehicleDetails lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public VehicleDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<ParameterLookup> getParameterLookups() {
        return this.parameterLookups;
    }

    public void setParameterLookups(Set<ParameterLookup> parameterLookups) {
        if (this.parameterLookups != null) {
            this.parameterLookups.forEach(i -> i.setVehicleDetails(null));
        }
        if (parameterLookups != null) {
            parameterLookups.forEach(i -> i.setVehicleDetails(this));
        }
        this.parameterLookups = parameterLookups;
    }

    public VehicleDetails parameterLookups(Set<ParameterLookup> parameterLookups) {
        this.setParameterLookups(parameterLookups);
        return this;
    }

    public VehicleDetails addParameterLookup(ParameterLookup parameterLookup) {
        this.parameterLookups.add(parameterLookup);
        parameterLookup.setVehicleDetails(this);
        return this;
    }

    public VehicleDetails removeParameterLookup(ParameterLookup parameterLookup) {
        this.parameterLookups.remove(parameterLookup);
        parameterLookup.setVehicleDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleDetails)) {
            return false;
        }
        return id != null && id.equals(((VehicleDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDetails{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", invoiceValue='" + getInvoiceValue() + "'" +
            ", idv='" + getIdv() + "'" +
            ", enginNumber='" + getEnginNumber() + "'" +
            ", chassisNumber='" + getChassisNumber() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", seatingCapacity=" + getSeatingCapacity() +
            ", zone='" + getZone() + "'" +
            ", yearOfManufacturing='" + getYearOfManufacturing() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
