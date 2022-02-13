package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Zone;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.VehicleDetails} entity.
 */
public class VehicleDetailsDTO implements Serializable {

    private Long id;

    private Long name;

    private String invoiceValue;

    private String idv;

    private String enginNumber;

    private String chassisNumber;

    private String registrationNumber;

    private Long seatingCapacity;

    private Zone zone;

    private String yearOfManufacturing;

    private String registrationDate;

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

    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public String getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(String invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getIdv() {
        return idv;
    }

    public void setIdv(String idv) {
        this.idv = idv;
    }

    public String getEnginNumber() {
        return enginNumber;
    }

    public void setEnginNumber(String enginNumber) {
        this.enginNumber = enginNumber;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Long seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getYearOfManufacturing() {
        return yearOfManufacturing;
    }

    public void setYearOfManufacturing(String yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
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
        if (!(o instanceof VehicleDetailsDTO)) {
            return false;
        }

        VehicleDetailsDTO vehicleDetailsDTO = (VehicleDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDetailsDTO{" +
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
