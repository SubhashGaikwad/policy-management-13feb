package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.StatusInd;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.PolicyUsers} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PolicyUsersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /policy-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PolicyUsersCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatusInd
     */
    public static class StatusIndFilter extends Filter<StatusInd> {

        public StatusIndFilter() {}

        public StatusIndFilter(StatusIndFilter filter) {
            super(filter);
        }

        @Override
        public StatusIndFilter copy() {
            return new StatusIndFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter groupCode;

    private StringFilter groupHeadName;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter birthDate;

    private StringFilter marriageDate;

    private LongFilter userTypeId;

    private StringFilter username;

    private StringFilter password;

    private StringFilter email;

    private StringFilter imageUrl;

    private StatusIndFilter status;

    private BooleanFilter activated;

    private StringFilter licenceExpiryDate;

    private StringFilter mobileNo;

    private StringFilter aadharCardNuber;

    private StringFilter pancardNumber;

    private StringFilter oneTimePassword;

    private StringFilter otpExpiryTime;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter usersTypeId;

    private LongFilter policyId;

    private LongFilter addressId;

    private Boolean distinct;

    public PolicyUsersCriteria() {}

    public PolicyUsersCriteria(PolicyUsersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.groupCode = other.groupCode == null ? null : other.groupCode.copy();
        this.groupHeadName = other.groupHeadName == null ? null : other.groupHeadName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.marriageDate = other.marriageDate == null ? null : other.marriageDate.copy();
        this.userTypeId = other.userTypeId == null ? null : other.userTypeId.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.licenceExpiryDate = other.licenceExpiryDate == null ? null : other.licenceExpiryDate.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.aadharCardNuber = other.aadharCardNuber == null ? null : other.aadharCardNuber.copy();
        this.pancardNumber = other.pancardNumber == null ? null : other.pancardNumber.copy();
        this.oneTimePassword = other.oneTimePassword == null ? null : other.oneTimePassword.copy();
        this.otpExpiryTime = other.otpExpiryTime == null ? null : other.otpExpiryTime.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.usersTypeId = other.usersTypeId == null ? null : other.usersTypeId.copy();
        this.policyId = other.policyId == null ? null : other.policyId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PolicyUsersCriteria copy() {
        return new PolicyUsersCriteria(this);
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

    public StringFilter getGroupCode() {
        return groupCode;
    }

    public StringFilter groupCode() {
        if (groupCode == null) {
            groupCode = new StringFilter();
        }
        return groupCode;
    }

    public void setGroupCode(StringFilter groupCode) {
        this.groupCode = groupCode;
    }

    public StringFilter getGroupHeadName() {
        return groupHeadName;
    }

    public StringFilter groupHeadName() {
        if (groupHeadName == null) {
            groupHeadName = new StringFilter();
        }
        return groupHeadName;
    }

    public void setGroupHeadName(StringFilter groupHeadName) {
        this.groupHeadName = groupHeadName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getBirthDate() {
        return birthDate;
    }

    public StringFilter birthDate() {
        if (birthDate == null) {
            birthDate = new StringFilter();
        }
        return birthDate;
    }

    public void setBirthDate(StringFilter birthDate) {
        this.birthDate = birthDate;
    }

    public StringFilter getMarriageDate() {
        return marriageDate;
    }

    public StringFilter marriageDate() {
        if (marriageDate == null) {
            marriageDate = new StringFilter();
        }
        return marriageDate;
    }

    public void setMarriageDate(StringFilter marriageDate) {
        this.marriageDate = marriageDate;
    }

    public LongFilter getUserTypeId() {
        return userTypeId;
    }

    public LongFilter userTypeId() {
        if (userTypeId == null) {
            userTypeId = new LongFilter();
        }
        return userTypeId;
    }

    public void setUserTypeId(LongFilter userTypeId) {
        this.userTypeId = userTypeId;
    }

    public StringFilter getUsername() {
        return username;
    }

    public StringFilter username() {
        if (username == null) {
            username = new StringFilter();
        }
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StatusIndFilter getStatus() {
        return status;
    }

    public StatusIndFilter status() {
        if (status == null) {
            status = new StatusIndFilter();
        }
        return status;
    }

    public void setStatus(StatusIndFilter status) {
        this.status = status;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public StringFilter licenceExpiryDate() {
        if (licenceExpiryDate == null) {
            licenceExpiryDate = new StringFilter();
        }
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(StringFilter licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getAadharCardNuber() {
        return aadharCardNuber;
    }

    public StringFilter aadharCardNuber() {
        if (aadharCardNuber == null) {
            aadharCardNuber = new StringFilter();
        }
        return aadharCardNuber;
    }

    public void setAadharCardNuber(StringFilter aadharCardNuber) {
        this.aadharCardNuber = aadharCardNuber;
    }

    public StringFilter getPancardNumber() {
        return pancardNumber;
    }

    public StringFilter pancardNumber() {
        if (pancardNumber == null) {
            pancardNumber = new StringFilter();
        }
        return pancardNumber;
    }

    public void setPancardNumber(StringFilter pancardNumber) {
        this.pancardNumber = pancardNumber;
    }

    public StringFilter getOneTimePassword() {
        return oneTimePassword;
    }

    public StringFilter oneTimePassword() {
        if (oneTimePassword == null) {
            oneTimePassword = new StringFilter();
        }
        return oneTimePassword;
    }

    public void setOneTimePassword(StringFilter oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public StringFilter getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public StringFilter otpExpiryTime() {
        if (otpExpiryTime == null) {
            otpExpiryTime = new StringFilter();
        }
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(StringFilter otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
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

    public LongFilter getUsersTypeId() {
        return usersTypeId;
    }

    public LongFilter usersTypeId() {
        if (usersTypeId == null) {
            usersTypeId = new LongFilter();
        }
        return usersTypeId;
    }

    public void setUsersTypeId(LongFilter usersTypeId) {
        this.usersTypeId = usersTypeId;
    }

    public LongFilter getPolicyId() {
        return policyId;
    }

    public LongFilter policyId() {
        if (policyId == null) {
            policyId = new LongFilter();
        }
        return policyId;
    }

    public void setPolicyId(LongFilter policyId) {
        this.policyId = policyId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final PolicyUsersCriteria that = (PolicyUsersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(groupCode, that.groupCode) &&
            Objects.equals(groupHeadName, that.groupHeadName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(marriageDate, that.marriageDate) &&
            Objects.equals(userTypeId, that.userTypeId) &&
            Objects.equals(username, that.username) &&
            Objects.equals(password, that.password) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(status, that.status) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(licenceExpiryDate, that.licenceExpiryDate) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(aadharCardNuber, that.aadharCardNuber) &&
            Objects.equals(pancardNumber, that.pancardNumber) &&
            Objects.equals(oneTimePassword, that.oneTimePassword) &&
            Objects.equals(otpExpiryTime, that.otpExpiryTime) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(usersTypeId, that.usersTypeId) &&
            Objects.equals(policyId, that.policyId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            groupCode,
            groupHeadName,
            firstName,
            lastName,
            birthDate,
            marriageDate,
            userTypeId,
            username,
            password,
            email,
            imageUrl,
            status,
            activated,
            licenceExpiryDate,
            mobileNo,
            aadharCardNuber,
            pancardNumber,
            oneTimePassword,
            otpExpiryTime,
            lastModified,
            lastModifiedBy,
            usersTypeId,
            policyId,
            addressId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyUsersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (groupCode != null ? "groupCode=" + groupCode + ", " : "") +
            (groupHeadName != null ? "groupHeadName=" + groupHeadName + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (marriageDate != null ? "marriageDate=" + marriageDate + ", " : "") +
            (userTypeId != null ? "userTypeId=" + userTypeId + ", " : "") +
            (username != null ? "username=" + username + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (licenceExpiryDate != null ? "licenceExpiryDate=" + licenceExpiryDate + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (aadharCardNuber != null ? "aadharCardNuber=" + aadharCardNuber + ", " : "") +
            (pancardNumber != null ? "pancardNumber=" + pancardNumber + ", " : "") +
            (oneTimePassword != null ? "oneTimePassword=" + oneTimePassword + ", " : "") +
            (otpExpiryTime != null ? "otpExpiryTime=" + otpExpiryTime + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (usersTypeId != null ? "usersTypeId=" + usersTypeId + ", " : "") +
            (policyId != null ? "policyId=" + policyId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
