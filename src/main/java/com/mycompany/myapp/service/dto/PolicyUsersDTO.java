package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.StatusInd;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.PolicyUsers} entity.
 */
public class PolicyUsersDTO implements Serializable {

    private Long id;

    private String groupCode;

    private String groupHeadName;

    private String firstName;

    private String lastName;

    @NotNull
    private String birthDate;

    @NotNull
    private String marriageDate;

    private Long userTypeId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String email;

    private String imageUrl;

    private StatusInd status;

    @NotNull
    private Boolean activated;

    private String licenceExpiryDate;

    private String mobileNo;

    private String aadharCardNuber;

    private String pancardNumber;

    private String oneTimePassword;

    private String otpExpiryTime;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    private UsersTypeDTO usersType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupHeadName() {
        return groupHeadName;
    }

    public void setGroupHeadName(String groupHeadName) {
        this.groupHeadName = groupHeadName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StatusInd getStatus() {
        return status;
    }

    public void setStatus(StatusInd status) {
        this.status = status;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLicenceExpiryDate() {
        return licenceExpiryDate;
    }

    public void setLicenceExpiryDate(String licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAadharCardNuber() {
        return aadharCardNuber;
    }

    public void setAadharCardNuber(String aadharCardNuber) {
        this.aadharCardNuber = aadharCardNuber;
    }

    public String getPancardNumber() {
        return pancardNumber;
    }

    public void setPancardNumber(String pancardNumber) {
        this.pancardNumber = pancardNumber;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(String otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
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

    public UsersTypeDTO getUsersType() {
        return usersType;
    }

    public void setUsersType(UsersTypeDTO usersType) {
        this.usersType = usersType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyUsersDTO)) {
            return false;
        }

        PolicyUsersDTO policyUsersDTO = (PolicyUsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, policyUsersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyUsersDTO{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", groupHeadName='" + getGroupHeadName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", marriageDate='" + getMarriageDate() + "'" +
            ", userTypeId=" + getUserTypeId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", activated='" + getActivated() + "'" +
            ", licenceExpiryDate='" + getLicenceExpiryDate() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", aadharCardNuber='" + getAadharCardNuber() + "'" +
            ", pancardNumber='" + getPancardNumber() + "'" +
            ", oneTimePassword='" + getOneTimePassword() + "'" +
            ", otpExpiryTime='" + getOtpExpiryTime() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", usersType=" + getUsersType() +
            "}";
    }
}
