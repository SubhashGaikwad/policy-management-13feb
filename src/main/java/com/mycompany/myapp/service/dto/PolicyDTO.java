package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.PolicyStatus;
import com.mycompany.myapp.domain.enumeration.PolicyType;
import com.mycompany.myapp.domain.enumeration.PremiumMode;
import com.mycompany.myapp.domain.enumeration.Zone;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Policy} entity.
 */
public class PolicyDTO implements Serializable {

    private Long id;

    private Long policyAmount;

    private String policyNumber;

    private Long term;

    private Long ppt;

    @NotNull
    private String commDate;

    private String proposerName;

    private Long sumAssuredAmount;

    private PremiumMode premiumMode;

    private Long basicPremium;

    private Long extraPremium;

    private String gst;

    private PolicyStatus status;

    private String totalPremiun;

    private String gstFirstYear;

    private String netPremium;

    private String taxBeneficiary;

    private Boolean policyReceived;

    private Long previousPolicy;

    private String policyStartDate;

    private String policyEndDate;

    private String period;

    private Boolean claimDone;

    private Boolean freeHeathCheckup;

    private Zone zone;

    private Long noOfYear;

    private String floaterSum;

    private String tpa;

    private String paymentDate;

    private PolicyType policyType;

    private String paToOwner;

    private String paToOther;

    private Long loading;

    private String riskCoveredFrom;

    private String riskCoveredTo;

    private String notes;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    @NotNull
    private String maturityDate;

    private String uinNo;

    @NotNull
    private String lastModified;

    @NotNull
    private String lastModifiedBy;

    private AgencyDTO agency;

    private CompanyDTO company;

    private ProductDTO product;

    private PremiunDetailsDTO premiunDetails;

    private VehicleClassDTO vehicleClass;

    private BankDetailsDTO bankDetails;

    private PolicyUsersDTO policyUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyAmount() {
        return policyAmount;
    }

    public void setPolicyAmount(Long policyAmount) {
        this.policyAmount = policyAmount;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Long getPpt() {
        return ppt;
    }

    public void setPpt(Long ppt) {
        this.ppt = ppt;
    }

    public String getCommDate() {
        return commDate;
    }

    public void setCommDate(String commDate) {
        this.commDate = commDate;
    }

    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public Long getSumAssuredAmount() {
        return sumAssuredAmount;
    }

    public void setSumAssuredAmount(Long sumAssuredAmount) {
        this.sumAssuredAmount = sumAssuredAmount;
    }

    public PremiumMode getPremiumMode() {
        return premiumMode;
    }

    public void setPremiumMode(PremiumMode premiumMode) {
        this.premiumMode = premiumMode;
    }

    public Long getBasicPremium() {
        return basicPremium;
    }

    public void setBasicPremium(Long basicPremium) {
        this.basicPremium = basicPremium;
    }

    public Long getExtraPremium() {
        return extraPremium;
    }

    public void setExtraPremium(Long extraPremium) {
        this.extraPremium = extraPremium;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public PolicyStatus getStatus() {
        return status;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public String getTotalPremiun() {
        return totalPremiun;
    }

    public void setTotalPremiun(String totalPremiun) {
        this.totalPremiun = totalPremiun;
    }

    public String getGstFirstYear() {
        return gstFirstYear;
    }

    public void setGstFirstYear(String gstFirstYear) {
        this.gstFirstYear = gstFirstYear;
    }

    public String getNetPremium() {
        return netPremium;
    }

    public void setNetPremium(String netPremium) {
        this.netPremium = netPremium;
    }

    public String getTaxBeneficiary() {
        return taxBeneficiary;
    }

    public void setTaxBeneficiary(String taxBeneficiary) {
        this.taxBeneficiary = taxBeneficiary;
    }

    public Boolean getPolicyReceived() {
        return policyReceived;
    }

    public void setPolicyReceived(Boolean policyReceived) {
        this.policyReceived = policyReceived;
    }

    public Long getPreviousPolicy() {
        return previousPolicy;
    }

    public void setPreviousPolicy(Long previousPolicy) {
        this.previousPolicy = previousPolicy;
    }

    public String getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(String policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getPolicyEndDate() {
        return policyEndDate;
    }

    public void setPolicyEndDate(String policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Boolean getClaimDone() {
        return claimDone;
    }

    public void setClaimDone(Boolean claimDone) {
        this.claimDone = claimDone;
    }

    public Boolean getFreeHeathCheckup() {
        return freeHeathCheckup;
    }

    public void setFreeHeathCheckup(Boolean freeHeathCheckup) {
        this.freeHeathCheckup = freeHeathCheckup;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Long getNoOfYear() {
        return noOfYear;
    }

    public void setNoOfYear(Long noOfYear) {
        this.noOfYear = noOfYear;
    }

    public String getFloaterSum() {
        return floaterSum;
    }

    public void setFloaterSum(String floaterSum) {
        this.floaterSum = floaterSum;
    }

    public String getTpa() {
        return tpa;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PolicyType getPolicyType() {
        return policyType;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public String getPaToOwner() {
        return paToOwner;
    }

    public void setPaToOwner(String paToOwner) {
        this.paToOwner = paToOwner;
    }

    public String getPaToOther() {
        return paToOther;
    }

    public void setPaToOther(String paToOther) {
        this.paToOther = paToOther;
    }

    public Long getLoading() {
        return loading;
    }

    public void setLoading(Long loading) {
        this.loading = loading;
    }

    public String getRiskCoveredFrom() {
        return riskCoveredFrom;
    }

    public void setRiskCoveredFrom(String riskCoveredFrom) {
        this.riskCoveredFrom = riskCoveredFrom;
    }

    public String getRiskCoveredTo() {
        return riskCoveredTo;
    }

    public void setRiskCoveredTo(String riskCoveredTo) {
        this.riskCoveredTo = riskCoveredTo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getUinNo() {
        return uinNo;
    }

    public void setUinNo(String uinNo) {
        this.uinNo = uinNo;
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

    public AgencyDTO getAgency() {
        return agency;
    }

    public void setAgency(AgencyDTO agency) {
        this.agency = agency;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public PremiunDetailsDTO getPremiunDetails() {
        return premiunDetails;
    }

    public void setPremiunDetails(PremiunDetailsDTO premiunDetails) {
        this.premiunDetails = premiunDetails;
    }

    public VehicleClassDTO getVehicleClass() {
        return vehicleClass;
    }

    public void setVehicleClass(VehicleClassDTO vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public BankDetailsDTO getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetailsDTO bankDetails) {
        this.bankDetails = bankDetails;
    }

    public PolicyUsersDTO getPolicyUsers() {
        return policyUsers;
    }

    public void setPolicyUsers(PolicyUsersDTO policyUsers) {
        this.policyUsers = policyUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PolicyDTO)) {
            return false;
        }

        PolicyDTO policyDTO = (PolicyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, policyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyDTO{" +
            "id=" + getId() +
            ", policyAmount=" + getPolicyAmount() +
            ", policyNumber='" + getPolicyNumber() + "'" +
            ", term=" + getTerm() +
            ", ppt=" + getPpt() +
            ", commDate='" + getCommDate() + "'" +
            ", proposerName='" + getProposerName() + "'" +
            ", sumAssuredAmount=" + getSumAssuredAmount() +
            ", premiumMode='" + getPremiumMode() + "'" +
            ", basicPremium=" + getBasicPremium() +
            ", extraPremium=" + getExtraPremium() +
            ", gst='" + getGst() + "'" +
            ", status='" + getStatus() + "'" +
            ", totalPremiun='" + getTotalPremiun() + "'" +
            ", gstFirstYear='" + getGstFirstYear() + "'" +
            ", netPremium='" + getNetPremium() + "'" +
            ", taxBeneficiary='" + getTaxBeneficiary() + "'" +
            ", policyReceived='" + getPolicyReceived() + "'" +
            ", previousPolicy=" + getPreviousPolicy() +
            ", policyStartDate='" + getPolicyStartDate() + "'" +
            ", policyEndDate='" + getPolicyEndDate() + "'" +
            ", period='" + getPeriod() + "'" +
            ", claimDone='" + getClaimDone() + "'" +
            ", freeHeathCheckup='" + getFreeHeathCheckup() + "'" +
            ", zone='" + getZone() + "'" +
            ", noOfYear=" + getNoOfYear() +
            ", floaterSum='" + getFloaterSum() + "'" +
            ", tpa='" + getTpa() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", policyType='" + getPolicyType() + "'" +
            ", paToOwner='" + getPaToOwner() + "'" +
            ", paToOther='" + getPaToOther() + "'" +
            ", loading=" + getLoading() +
            ", riskCoveredFrom='" + getRiskCoveredFrom() + "'" +
            ", riskCoveredTo='" + getRiskCoveredTo() + "'" +
            ", notes='" + getNotes() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", maturityDate='" + getMaturityDate() + "'" +
            ", uinNo='" + getUinNo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", agency=" + getAgency() +
            ", company=" + getCompany() +
            ", product=" + getProduct() +
            ", premiunDetails=" + getPremiunDetails() +
            ", vehicleClass=" + getVehicleClass() +
            ", bankDetails=" + getBankDetails() +
            ", policyUsers=" + getPolicyUsers() +
            "}";
    }
}
