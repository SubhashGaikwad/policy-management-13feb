package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.Zone;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.VehicleDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.VehicleDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Zone
     */
    public static class ZoneFilter extends Filter<Zone> {

        public ZoneFilter() {}

        public ZoneFilter(ZoneFilter filter) {
            super(filter);
        }

        @Override
        public ZoneFilter copy() {
            return new ZoneFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter name;

    private StringFilter invoiceValue;

    private StringFilter idv;

    private StringFilter enginNumber;

    private StringFilter chassisNumber;

    private StringFilter registrationNumber;

    private LongFilter seatingCapacity;

    private ZoneFilter zone;

    private StringFilter yearOfManufacturing;

    private StringFilter registrationDate;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter parameterLookupId;

    private Boolean distinct;

    public VehicleDetailsCriteria() {}

    public VehicleDetailsCriteria(VehicleDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.invoiceValue = other.invoiceValue == null ? null : other.invoiceValue.copy();
        this.idv = other.idv == null ? null : other.idv.copy();
        this.enginNumber = other.enginNumber == null ? null : other.enginNumber.copy();
        this.chassisNumber = other.chassisNumber == null ? null : other.chassisNumber.copy();
        this.registrationNumber = other.registrationNumber == null ? null : other.registrationNumber.copy();
        this.seatingCapacity = other.seatingCapacity == null ? null : other.seatingCapacity.copy();
        this.zone = other.zone == null ? null : other.zone.copy();
        this.yearOfManufacturing = other.yearOfManufacturing == null ? null : other.yearOfManufacturing.copy();
        this.registrationDate = other.registrationDate == null ? null : other.registrationDate.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.parameterLookupId = other.parameterLookupId == null ? null : other.parameterLookupId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VehicleDetailsCriteria copy() {
        return new VehicleDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getName() {
        return name;
    }

    public LongFilter name() {
        if (name == null) {
            name = new LongFilter();
        }
        return name;
    }

    public void setName(LongFilter name) {
        this.name = name;
    }

    public StringFilter getInvoiceValue() {
        return invoiceValue;
    }

    public StringFilter invoiceValue() {
        if (invoiceValue == null) {
            invoiceValue = new StringFilter();
        }
        return invoiceValue;
    }

    public void setInvoiceValue(StringFilter invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public StringFilter getIdv() {
        return idv;
    }

    public StringFilter idv() {
        if (idv == null) {
            idv = new StringFilter();
        }
        return idv;
    }

    public void setIdv(StringFilter idv) {
        this.idv = idv;
    }

    public StringFilter getEnginNumber() {
        return enginNumber;
    }

    public StringFilter enginNumber() {
        if (enginNumber == null) {
            enginNumber = new StringFilter();
        }
        return enginNumber;
    }

    public void setEnginNumber(StringFilter enginNumber) {
        this.enginNumber = enginNumber;
    }

    public StringFilter getChassisNumber() {
        return chassisNumber;
    }

    public StringFilter chassisNumber() {
        if (chassisNumber == null) {
            chassisNumber = new StringFilter();
        }
        return chassisNumber;
    }

    public void setChassisNumber(StringFilter chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public StringFilter getRegistrationNumber() {
        return registrationNumber;
    }

    public StringFilter registrationNumber() {
        if (registrationNumber == null) {
            registrationNumber = new StringFilter();
        }
        return registrationNumber;
    }

    public void setRegistrationNumber(StringFilter registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public LongFilter getSeatingCapacity() {
        return seatingCapacity;
    }

    public LongFilter seatingCapacity() {
        if (seatingCapacity == null) {
            seatingCapacity = new LongFilter();
        }
        return seatingCapacity;
    }

    public void setSeatingCapacity(LongFilter seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public ZoneFilter getZone() {
        return zone;
    }

    public ZoneFilter zone() {
        if (zone == null) {
            zone = new ZoneFilter();
        }
        return zone;
    }

    public void setZone(ZoneFilter zone) {
        this.zone = zone;
    }

    public StringFilter getYearOfManufacturing() {
        return yearOfManufacturing;
    }

    public StringFilter yearOfManufacturing() {
        if (yearOfManufacturing == null) {
            yearOfManufacturing = new StringFilter();
        }
        return yearOfManufacturing;
    }

    public void setYearOfManufacturing(StringFilter yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }

    public StringFilter getRegistrationDate() {
        return registrationDate;
    }

    public StringFilter registrationDate() {
        if (registrationDate == null) {
            registrationDate = new StringFilter();
        }
        return registrationDate;
    }

    public void setRegistrationDate(StringFilter registrationDate) {
        this.registrationDate = registrationDate;
    }

    public StringFilter getLastModified() {
        return lastModified;
    }

    public StringFilter lastModified() {
        if (lastModified == null) {
            lastModified = new StringFilter();
        }
        return lastModified;
    }

    public void setLastModified(StringFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getParameterLookupId() {
        return parameterLookupId;
    }

    public LongFilter parameterLookupId() {
        if (parameterLookupId == null) {
            parameterLookupId = new LongFilter();
        }
        return parameterLookupId;
    }

    public void setParameterLookupId(LongFilter parameterLookupId) {
        this.parameterLookupId = parameterLookupId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VehicleDetailsCriteria that = (VehicleDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(invoiceValue, that.invoiceValue) &&
            Objects.equals(idv, that.idv) &&
            Objects.equals(enginNumber, that.enginNumber) &&
            Objects.equals(chassisNumber, that.chassisNumber) &&
            Objects.equals(registrationNumber, that.registrationNumber) &&
            Objects.equals(seatingCapacity, that.seatingCapacity) &&
            Objects.equals(zone, that.zone) &&
            Objects.equals(yearOfManufacturing, that.yearOfManufacturing) &&
            Objects.equals(registrationDate, that.registrationDate) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(parameterLookupId, that.parameterLookupId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            invoiceValue,
            idv,
            enginNumber,
            chassisNumber,
            registrationNumber,
            seatingCapacity,
            zone,
            yearOfManufacturing,
            registrationDate,
            lastModified,
            lastModifiedBy,
            parameterLookupId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (invoiceValue != null ? "invoiceValue=" + invoiceValue + ", " : "") +
            (idv != null ? "idv=" + idv + ", " : "") +
            (enginNumber != null ? "enginNumber=" + enginNumber + ", " : "") +
            (chassisNumber != null ? "chassisNumber=" + chassisNumber + ", " : "") +
            (registrationNumber != null ? "registrationNumber=" + registrationNumber + ", " : "") +
            (seatingCapacity != null ? "seatingCapacity=" + seatingCapacity + ", " : "") +
            (zone != null ? "zone=" + zone + ", " : "") +
            (yearOfManufacturing != null ? "yearOfManufacturing=" + yearOfManufacturing + ", " : "") +
            (registrationDate != null ? "registrationDate=" + registrationDate + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (parameterLookupId != null ? "parameterLookupId=" + parameterLookupId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
