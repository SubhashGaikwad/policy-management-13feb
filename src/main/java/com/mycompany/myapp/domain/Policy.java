package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.PolicyStatus;
import com.mycompany.myapp.domain.enumeration.PolicyType;
import com.mycompany.myapp.domain.enumeration.PremiumMode;
import com.mycompany.myapp.domain.enumeration.Zone;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Policy.
 */
@Entity
@Table(name = "policy")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Policy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "policy_amount")
    private Long policyAmount;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "term")
    private Long term;

    @Column(name = "ppt")
    private Long ppt;

    @NotNull
    @Column(name = "comm_date", nullable = false)
    private String commDate;

    @Column(name = "proposer_name")
    private String proposerName;

    @Column(name = "sum_assured_amount")
    private Long sumAssuredAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "premium_mode")
    private PremiumMode premiumMode;

    @Column(name = "basic_premium")
    private Long basicPremium;

    @Column(name = "extra_premium")
    private Long extraPremium;

    @Column(name = "gst")
    private String gst;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PolicyStatus status;

    @Column(name = "total_premiun")
    private String totalPremiun;

    @Column(name = "gst_first_year")
    private String gstFirstYear;

    @Column(name = "net_premium")
    private String netPremium;

    @Column(name = "tax_beneficiary")
    private String taxBeneficiary;

    @Column(name = "policy_received")
    private Boolean policyReceived;

    @Column(name = "previous_policy")
    private Long previousPolicy;

    @Column(name = "policy_start_date")
    private String policyStartDate;

    @Column(name = "policy_end_date")
    private String policyEndDate;

    @Column(name = "period")
    private String period;

    @Column(name = "claim_done")
    private Boolean claimDone;

    @Column(name = "free_heath_checkup")
    private Boolean freeHeathCheckup;

    @Enumerated(EnumType.STRING)
    @Column(name = "zone")
    private Zone zone;

    @Column(name = "no_of_year")
    private Long noOfYear;

    @Column(name = "floater_sum")
    private String floaterSum;

    @Column(name = "tpa")
    private String tpa;

    @Column(name = "payment_date")
    private String paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type")
    private PolicyType policyType;

    @Column(name = "pa_to_owner")
    private String paToOwner;

    @Column(name = "pa_to_other")
    private String paToOther;

    @Column(name = "loading")
    private Long loading;

    @Column(name = "risk_covered_from")
    private String riskCoveredFrom;

    @Column(name = "risk_covered_to")
    private String riskCoveredTo;

    @Column(name = "notes")
    private String notes;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @NotNull
    @Column(name = "maturity_date", nullable = false)
    private String maturityDate;

    @Column(name = "uin_no")
    private String uinNo;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private String lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Agency agency;

    @JsonIgnoreProperties(value = { "companyType", "products", "addresses", "policy" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Company company;

    @JsonIgnoreProperties(value = { "productDetails", "policy", "company" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Product product;

    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private PremiunDetails premiunDetails;

    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private VehicleClass vehicleClass;

    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BankDetails bankDetails;

    @OneToMany(mappedBy = "policy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    private Set<Nominee> nominees = new HashSet<>();

    @OneToMany(mappedBy = "policy")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "policy" }, allowSetters = true)
    private Set<Member> members = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "usersType", "policies", "addresses" }, allowSetters = true)
    private PolicyUsers policyUsers;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Policy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyAmount() {
        return this.policyAmount;
    }

    public Policy policyAmount(Long policyAmount) {
        this.setPolicyAmount(policyAmount);
        return this;
    }

    public void setPolicyAmount(Long policyAmount) {
        this.policyAmount = policyAmount;
    }

    public String getPolicyNumber() {
        return this.policyNumber;
    }

    public Policy policyNumber(String policyNumber) {
        this.setPolicyNumber(policyNumber);
        return this;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Long getTerm() {
        return this.term;
    }

    public Policy term(Long term) {
        this.setTerm(term);
        return this;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Long getPpt() {
        return this.ppt;
    }

    public Policy ppt(Long ppt) {
        this.setPpt(ppt);
        return this;
    }

    public void setPpt(Long ppt) {
        this.ppt = ppt;
    }

    public String getCommDate() {
        return this.commDate;
    }

    public Policy commDate(String commDate) {
        this.setCommDate(commDate);
        return this;
    }

    public void setCommDate(String commDate) {
        this.commDate = commDate;
    }

    public String getProposerName() {
        return this.proposerName;
    }

    public Policy proposerName(String proposerName) {
        this.setProposerName(proposerName);
        return this;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public Long getSumAssuredAmount() {
        return this.sumAssuredAmount;
    }

    public Policy sumAssuredAmount(Long sumAssuredAmount) {
        this.setSumAssuredAmount(sumAssuredAmount);
        return this;
    }

    public void setSumAssuredAmount(Long sumAssuredAmount) {
        this.sumAssuredAmount = sumAssuredAmount;
    }

    public PremiumMode getPremiumMode() {
        return this.premiumMode;
    }

    public Policy premiumMode(PremiumMode premiumMode) {
        this.setPremiumMode(premiumMode);
        return this;
    }

    public void setPremiumMode(PremiumMode premiumMode) {
        this.premiumMode = premiumMode;
    }

    public Long getBasicPremium() {
        return this.basicPremium;
    }

    public Policy basicPremium(Long basicPremium) {
        this.setBasicPremium(basicPremium);
        return this;
    }

    public void setBasicPremium(Long basicPremium) {
        this.basicPremium = basicPremium;
    }

    public Long getExtraPremium() {
        return this.extraPremium;
    }

    public Policy extraPremium(Long extraPremium) {
        this.setExtraPremium(extraPremium);
        return this;
    }

    public void setExtraPremium(Long extraPremium) {
        this.extraPremium = extraPremium;
    }

    public String getGst() {
        return this.gst;
    }

    public Policy gst(String gst) {
        this.setGst(gst);
        return this;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public PolicyStatus getStatus() {
        return this.status;
    }

    public Policy status(PolicyStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PolicyStatus status) {
        this.status = status;
    }

    public String getTotalPremiun() {
        return this.totalPremiun;
    }

    public Policy totalPremiun(String totalPremiun) {
        this.setTotalPremiun(totalPremiun);
        return this;
    }

    public void setTotalPremiun(String totalPremiun) {
        this.totalPremiun = totalPremiun;
    }

    public String getGstFirstYear() {
        return this.gstFirstYear;
    }

    public Policy gstFirstYear(String gstFirstYear) {
        this.setGstFirstYear(gstFirstYear);
        return this;
    }

    public void setGstFirstYear(String gstFirstYear) {
        this.gstFirstYear = gstFirstYear;
    }

    public String getNetPremium() {
        return this.netPremium;
    }

    public Policy netPremium(String netPremium) {
        this.setNetPremium(netPremium);
        return this;
    }

    public void setNetPremium(String netPremium) {
        this.netPremium = netPremium;
    }

    public String getTaxBeneficiary() {
        return this.taxBeneficiary;
    }

    public Policy taxBeneficiary(String taxBeneficiary) {
        this.setTaxBeneficiary(taxBeneficiary);
        return this;
    }

    public void setTaxBeneficiary(String taxBeneficiary) {
        this.taxBeneficiary = taxBeneficiary;
    }

    public Boolean getPolicyReceived() {
        return this.policyReceived;
    }

    public Policy policyReceived(Boolean policyReceived) {
        this.setPolicyReceived(policyReceived);
        return this;
    }

    public void setPolicyReceived(Boolean policyReceived) {
        this.policyReceived = policyReceived;
    }

    public Long getPreviousPolicy() {
        return this.previousPolicy;
    }

    public Policy previousPolicy(Long previousPolicy) {
        this.setPreviousPolicy(previousPolicy);
        return this;
    }

    public void setPreviousPolicy(Long previousPolicy) {
        this.previousPolicy = previousPolicy;
    }

    public String getPolicyStartDate() {
        return this.policyStartDate;
    }

    public Policy policyStartDate(String policyStartDate) {
        this.setPolicyStartDate(policyStartDate);
        return this;
    }

    public void setPolicyStartDate(String policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getPolicyEndDate() {
        return this.policyEndDate;
    }

    public Policy policyEndDate(String policyEndDate) {
        this.setPolicyEndDate(policyEndDate);
        return this;
    }

    public void setPolicyEndDate(String policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public String getPeriod() {
        return this.period;
    }

    public Policy period(String period) {
        this.setPeriod(period);
        return this;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Boolean getClaimDone() {
        return this.claimDone;
    }

    public Policy claimDone(Boolean claimDone) {
        this.setClaimDone(claimDone);
        return this;
    }

    public void setClaimDone(Boolean claimDone) {
        this.claimDone = claimDone;
    }

    public Boolean getFreeHeathCheckup() {
        return this.freeHeathCheckup;
    }

    public Policy freeHeathCheckup(Boolean freeHeathCheckup) {
        this.setFreeHeathCheckup(freeHeathCheckup);
        return this;
    }

    public void setFreeHeathCheckup(Boolean freeHeathCheckup) {
        this.freeHeathCheckup = freeHeathCheckup;
    }

    public Zone getZone() {
        return this.zone;
    }

    public Policy zone(Zone zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Long getNoOfYear() {
        return this.noOfYear;
    }

    public Policy noOfYear(Long noOfYear) {
        this.setNoOfYear(noOfYear);
        return this;
    }

    public void setNoOfYear(Long noOfYear) {
        this.noOfYear = noOfYear;
    }

    public String getFloaterSum() {
        return this.floaterSum;
    }

    public Policy floaterSum(String floaterSum) {
        this.setFloaterSum(floaterSum);
        return this;
    }

    public void setFloaterSum(String floaterSum) {
        this.floaterSum = floaterSum;
    }

    public String getTpa() {
        return this.tpa;
    }

    public Policy tpa(String tpa) {
        this.setTpa(tpa);
        return this;
    }

    public void setTpa(String tpa) {
        this.tpa = tpa;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public Policy paymentDate(String paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PolicyType getPolicyType() {
        return this.policyType;
    }

    public Policy policyType(PolicyType policyType) {
        this.setPolicyType(policyType);
        return this;
    }

    public void setPolicyType(PolicyType policyType) {
        this.policyType = policyType;
    }

    public String getPaToOwner() {
        return this.paToOwner;
    }

    public Policy paToOwner(String paToOwner) {
        this.setPaToOwner(paToOwner);
        return this;
    }

    public void setPaToOwner(String paToOwner) {
        this.paToOwner = paToOwner;
    }

    public String getPaToOther() {
        return this.paToOther;
    }

    public Policy paToOther(String paToOther) {
        this.setPaToOther(paToOther);
        return this;
    }

    public void setPaToOther(String paToOther) {
        this.paToOther = paToOther;
    }

    public Long getLoading() {
        return this.loading;
    }

    public Policy loading(Long loading) {
        this.setLoading(loading);
        return this;
    }

    public void setLoading(Long loading) {
        this.loading = loading;
    }

    public String getRiskCoveredFrom() {
        return this.riskCoveredFrom;
    }

    public Policy riskCoveredFrom(String riskCoveredFrom) {
        this.setRiskCoveredFrom(riskCoveredFrom);
        return this;
    }

    public void setRiskCoveredFrom(String riskCoveredFrom) {
        this.riskCoveredFrom = riskCoveredFrom;
    }

    public String getRiskCoveredTo() {
        return this.riskCoveredTo;
    }

    public Policy riskCoveredTo(String riskCoveredTo) {
        this.setRiskCoveredTo(riskCoveredTo);
        return this;
    }

    public void setRiskCoveredTo(String riskCoveredTo) {
        this.riskCoveredTo = riskCoveredTo;
    }

    public String getNotes() {
        return this.notes;
    }

    public Policy notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Policy freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Policy freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Policy freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Policy freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public Policy freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getMaturityDate() {
        return this.maturityDate;
    }

    public Policy maturityDate(String maturityDate) {
        this.setMaturityDate(maturityDate);
        return this;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getUinNo() {
        return this.uinNo;
    }

    public Policy uinNo(String uinNo) {
        this.setUinNo(uinNo);
        return this;
    }

    public void setUinNo(String uinNo) {
        this.uinNo = uinNo;
    }

    public String getLastModified() {
        return this.lastModified;
    }

    public Policy lastModified(String lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Policy lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Agency getAgency() {
        return this.agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public Policy agency(Agency agency) {
        this.setAgency(agency);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Policy company(Company company) {
        this.setCompany(company);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Policy product(Product product) {
        this.setProduct(product);
        return this;
    }

    public PremiunDetails getPremiunDetails() {
        return this.premiunDetails;
    }

    public void setPremiunDetails(PremiunDetails premiunDetails) {
        this.premiunDetails = premiunDetails;
    }

    public Policy premiunDetails(PremiunDetails premiunDetails) {
        this.setPremiunDetails(premiunDetails);
        return this;
    }

    public VehicleClass getVehicleClass() {
        return this.vehicleClass;
    }

    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }

    public Policy vehicleClass(VehicleClass vehicleClass) {
        this.setVehicleClass(vehicleClass);
        return this;
    }

    public BankDetails getBankDetails() {
        return this.bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public Policy bankDetails(BankDetails bankDetails) {
        this.setBankDetails(bankDetails);
        return this;
    }

    public Set<Nominee> getNominees() {
        return this.nominees;
    }

    public void setNominees(Set<Nominee> nominees) {
        if (this.nominees != null) {
            this.nominees.forEach(i -> i.setPolicy(null));
        }
        if (nominees != null) {
            nominees.forEach(i -> i.setPolicy(this));
        }
        this.nominees = nominees;
    }

    public Policy nominees(Set<Nominee> nominees) {
        this.setNominees(nominees);
        return this;
    }

    public Policy addNominee(Nominee nominee) {
        this.nominees.add(nominee);
        nominee.setPolicy(this);
        return this;
    }

    public Policy removeNominee(Nominee nominee) {
        this.nominees.remove(nominee);
        nominee.setPolicy(null);
        return this;
    }

    public Set<Member> getMembers() {
        return this.members;
    }

    public void setMembers(Set<Member> members) {
        if (this.members != null) {
            this.members.forEach(i -> i.setPolicy(null));
        }
        if (members != null) {
            members.forEach(i -> i.setPolicy(this));
        }
        this.members = members;
    }

    public Policy members(Set<Member> members) {
        this.setMembers(members);
        return this;
    }

    public Policy addMember(Member member) {
        this.members.add(member);
        member.setPolicy(this);
        return this;
    }

    public Policy removeMember(Member member) {
        this.members.remove(member);
        member.setPolicy(null);
        return this;
    }

    public PolicyUsers getPolicyUsers() {
        return this.policyUsers;
    }

    public void setPolicyUsers(PolicyUsers policyUsers) {
        this.policyUsers = policyUsers;
    }

    public Policy policyUsers(PolicyUsers policyUsers) {
        this.setPolicyUsers(policyUsers);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Policy)) {
            return false;
        }
        return id != null && id.equals(((Policy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Policy{" +
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
            "}";
    }
}
