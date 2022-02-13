package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.ParameterType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ParameterLookup} entity.
 */
public class ParameterLookupDTO implements Serializable {

    private Long id;

    private Long name;

    private ParameterType type;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    private VehicleDetailsDTO vehicleDetails;

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

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
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

    public VehicleDetailsDTO getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(VehicleDetailsDTO vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParameterLookupDTO)) {
            return false;
        }

        ParameterLookupDTO parameterLookupDTO = (ParameterLookupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parameterLookupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParameterLookupDTO{" +
            "id=" + getId() +
            ", name=" + getName() +
            ", type='" + getType() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", vehicleDetails=" + getVehicleDetails() +
            "}";
    }
}
