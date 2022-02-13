package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.StatusInd;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PolicyUsers.
 */
@Entity
@Table(name = "policy_users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PolicyUsers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "group_head_name")
    private String groupHeadName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @NotNull
    @Column(name = "marriage_date", nullable = false)
    private String marriageDate;

    @Column(name = "user_type_id")
    private Long userTypeId;

    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusInd status;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Column(name = "licence_expiry_date")
    private String licenceExpiryDate;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "aadhar_card_nuber")
    private String aadharCardNuber;

    @Column(name = "pancard_number")
    private String pancardNumber;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_expiry_time")
    private String otpExpiryTime;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "policyUsers" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private UsersType usersType;

    @OneToMany(mappedBy = "policyUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "agency", "company", "product", "premiunDetails", "vehicleClass", "bankDetails", "nominees", "members", "policyUsers" },
        allowSetters = true
    )
    private Set<Policy> policies = new HashSet<>();

    @OneToMany(mappedBy = "policyUsers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "policyUsers", "company" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PolicyUsers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public PolicyUsers groupCode(String groupCode) {
        this.setGroupCode(groupCode);
        return this;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupHeadName() {
        return this.groupHeadName;
    }

    public PolicyUsers groupHeadName(String groupHeadName) {
        this.setGroupHeadName(groupHeadName);
        return this;
    }

    public void setGroupHeadName(String groupHeadName) {
        this.groupHeadName = groupHeadName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public PolicyUsers firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public PolicyUsers lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public PolicyUsers birthDate(String birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMarriageDate() {
        return this.marriageDate;
    }

    public PolicyUsers marriageDate(String marriageDate) {
        this.setMarriageDate(marriageDate);
        return this;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public Long getUserTypeId() {
        return this.userTypeId;
    }

    public PolicyUsers userTypeId(Long userTypeId) {
        this.setUserTypeId(userTypeId);
        return this;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUsername() {
        return this.username;
    }

    public PolicyUsers username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public PolicyUsers password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public PolicyUsers email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public PolicyUsers imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StatusInd getStatus() {
        return this.status;
    }

    public PolicyUsers status(StatusInd status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusInd status) {
        this.status = status;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public PolicyUsers activated(Boolean activated) {
        this.setActivated(activated);
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLicenceExpiryDate() {
        return this.licenceExpiryDate;
    }

    public PolicyUsers licenceExpiryDate(String licenceExpiryDate) {
        this.setLicenceExpiryDate(licenceExpiryDate);
        return this;
    }

    public void setLicenceExpiryDate(String licenceExpiryDate) {
        this.licenceExpiryDate = licenceExpiryDate;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public PolicyUsers mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAadharCardNuber() {
        return this.aadharCardNuber;
    }

    public PolicyUsers aadharCardNuber(String aadharCardNuber) {
        this.setAadharCardNuber(aadharCardNuber);
        return this;
    }

    public void setAadharCardNuber(String aadharCardNuber) {
        this.aadharCardNuber = aadharCardNuber;
    }

    public String getPancardNumber() {
        return this.pancardNumber;
    }

    public PolicyUsers pancardNumber(String pancardNumber) {
        this.setPancardNumber(pancardNumber);
        return this;
    }

    public void setPancardNumber(String pancardNumber) {
        this.pancardNumber = pancardNumber;
    }

    public String getOneTimePassword() {
        return this.oneTimePassword;
    }

    public PolicyUsers oneTimePassword(String oneTimePassword) {
        this.setOneTimePassword(oneTimePassword);
        return this;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public String getOtpExpiryTime() {
        return this.otpExpiryTime;
    }

    public PolicyUsers otpExpiryTime(String otpExpiryTime) {
        this.setOtpExpiryTime(otpExpiryTime);
        return this;
    }

    public void setOtpExpiryTime(String otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public PolicyUsers lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PolicyUsers lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public UsersType getUsersType() {
        return this.usersType;
    }

    public void setUsersType(UsersType usersType) {
        this.usersType = usersType;
    }

    public PolicyUsers usersType(UsersType usersType) {
        this.setUsersType(usersType);
        return this;
    }

    public Set<Policy> getPolicies() {
        return this.policies;
    }

    public void setPolicies(Set<Policy> policies) {
        if (this.policies != null) {
            this.policies.forEach(i -> i.setPolicyUsers(null));
        }
        if (policies != null) {
            policies.forEach(i -> i.setPolicyUsers(this));
        }
        this.policies = policies;
    }

    public PolicyUsers policies(Set<Policy> policies) {
        this.setPolicies(policies);
        return this;
    }

    public PolicyUsers addPolicy(Policy policy) {
        this.policies.add(policy);
        policy.setPolicyUsers(this);
        return this;
    }

    public PolicyUsers removePolicy(Policy policy) {
        this.policies.remove(policy);
        policy.setPolicyUsers(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setPolicyUsers(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setPolicyUsers(this));
        }
        this.addresses = addresses;
    }

    public PolicyUsers addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public PolicyUsers addAddress(Address address) {
        this.addresses.add(address);
        address.setPolicyUsers(this);
        return this;
    }

    public PolicyUsers removeAddress(Address address) {
        this.addresses.remove(address);
        address.setPolicyUsers(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyUsers)) {
            return false;
        }
        return id != null && id.equals(((PolicyUsers) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyUsers{" +
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
            "}";
    }
}
