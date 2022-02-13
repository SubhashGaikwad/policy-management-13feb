package com.mycompany.myapp.service.criteria;

import com.mycompany.myapp.domain.enumeration.PolicyStatus;
import com.mycompany.myapp.domain.enumeration.PolicyType;
import com.mycompany.myapp.domain.enumeration.PremiumMode;
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
 * Criteria class for the {@link com.mycompany.myapp.domain.Policy} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.PolicyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /policies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PolicyCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PremiumMode
     */
    public static class PremiumModeFilter extends Filter<PremiumMode> {

        public PremiumModeFilter() {}

        public PremiumModeFilter(PremiumModeFilter filter) {
            super(filter);
        }

        @Override
        public PremiumModeFilter copy() {
            return new PremiumModeFilter(this);
        }
    }

    /**
     * Class for filtering PolicyStatus
     */
    public static class PolicyStatusFilter extends Filter<PolicyStatus> {

        public PolicyStatusFilter() {}

        public PolicyStatusFilter(PolicyStatusFilter filter) {
            super(filter);
        }

        @Override
        public PolicyStatusFilter copy() {
            return new PolicyStatusFilter(this);
        }
    }

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

    /**
     * Class for filtering PolicyType
     */
    public static class PolicyTypeFilter extends Filter<PolicyType> {

        public PolicyTypeFilter() {}

        public PolicyTypeFilter(PolicyTypeFilter filter) {
            super(filter);
        }

        @Override
        public PolicyTypeFilter copy() {
            return new PolicyTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter policyAmount;

    private StringFilter policyNumber;

    private LongFilter term;

    private LongFilter ppt;

    private StringFilter commDate;

    private StringFilter proposerName;

    private LongFilter sumAssuredAmount;

    private PremiumModeFilter premiumMode;

    private LongFilter basicPremium;

    private LongFilter extraPremium;

    private StringFilter gst;

    private PolicyStatusFilter status;

    private StringFilter totalPremiun;

    private StringFilter gstFirstYear;

    private StringFilter netPremium;

    private StringFilter taxBeneficiary;

    private BooleanFilter policyReceived;

    private LongFilter previousPolicy;

    private StringFilter policyStartDate;

    private StringFilter policyEndDate;

    private StringFilter period;

    private BooleanFilter claimDone;

    private BooleanFilter freeHeathCheckup;

    private ZoneFilter zone;

    private LongFilter noOfYear;

    private StringFilter floaterSum;

    private StringFilter tpa;

    private StringFilter paymentDate;

    private PolicyTypeFilter policyType;

    private StringFilter paToOwner;

    private StringFilter paToOther;

    private LongFilter loading;

    private StringFilter riskCoveredFrom;

    private StringFilter riskCoveredTo;

    private StringFilter notes;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter maturityDate;

    private StringFilter uinNo;

    private StringFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter agencyId;

    private LongFilter companyId;

    private LongFilter productId;

    private LongFilter premiunDetailsId;

    private LongFilter vehicleClassId;

    private LongFilter bankDetailsId;

    private LongFilter nomineeId;

    private LongFilter memberId;

    private LongFilter policyUsersId;

    private Boolean distinct;

    public PolicyCriteria() {}

    public PolicyCriteria(PolicyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.policyAmount = other.policyAmount == null ? null : other.policyAmount.copy();
        this.policyNumber = other.policyNumber == null ? null : other.policyNumber.copy();
        this.term = other.term == null ? null : other.term.copy();
        this.ppt = other.ppt == null ? null : other.ppt.copy();
        this.commDate = other.commDate == null ? null : other.commDate.copy();
        this.proposerName = other.proposerName == null ? null : other.proposerName.copy();
        this.sumAssuredAmount = other.sumAssuredAmount == null ? null : other.sumAssuredAmount.copy();
        this.premiumMode = other.premiumMode == null ? null : other.premiumMode.copy();
        this.basicPremium = other.basicPremium == null ? null : other.basicPremium.copy();
        this.extraPremium = other.extraPremium == null ? null : other.extraPremium.copy();
        this.gst = other.gst == null ? null : other.gst.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.totalPremiun = other.totalPremiun == null ? null : other.totalPremiun.copy();
        this.gstFirstYear = other.gstFirstYear == null ? null : other.gstFirstYear.copy();
        this.netPremium = other.netPremium == null ? null : other.netPremium.copy();
        this.taxBeneficiary = other.taxBeneficiary == null ? null : other.taxBeneficiary.copy();
        this.policyReceived = other.policyReceived == null ? null : other.policyReceived.copy();
        this.previousPolicy = other.previousPolicy == null ? null : other.previousPolicy.copy();
        this.policyStartDate = other.policyStartDate == null ? null : other.policyStartDate.copy();
        this.policyEndDate = other.policyEndDate == null ? null : other.policyEndDate.copy();
        this.period = other.period == null ? null : other.period.copy();
        this.claimDone = other.claimDone == null ? null : other.claimDone.copy();
        this.freeHeathCheckup = other.freeHeathCheckup == null ? null : other.freeHeathCheckup.copy();
        this.zone = other.zone == null ? null : other.zone.copy();
        this.noOfYear = other.noOfYear == null ? null : other.noOfYear.copy();
        this.floaterSum = other.floaterSum == null ? null : other.floaterSum.copy();
        this.tpa = other.tpa == null ? null : other.tpa.copy();
        this.paymentDate = other.paymentDate == null ? null : other.paymentDate.copy();
        this.policyType = other.policyType == null ? null : other.policyType.copy();
        this.paToOwner = other.paToOwner == null ? null : other.paToOwner.copy();
        this.paToOther = other.paToOther == null ? null : other.paToOther.copy();
        this.loading = other.loading == null ? null : other.loading.copy();
        this.riskCoveredFrom = other.riskCoveredFrom == null ? null : other.riskCoveredFrom.copy();
        this.riskCoveredTo = other.riskCoveredTo == null ? null : other.riskCoveredTo.copy();
        this.notes = other.notes == null ? null : other.notes.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.maturityDate = other.maturityDate == null ? null : other.maturityDate.copy();
        this.uinNo = other.uinNo == null ? null : other.uinNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.agencyId = other.agencyId == null ? null : other.agencyId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.premiunDetailsId = other.premiunDetailsId == null ? null : other.premiunDetailsId.copy();
        this.vehicleClassId = other.vehicleClassId == null ? null : other.vehicleClassId.copy();
        this.bankDetailsId = other.bankDetailsId == null ? null : other.bankDetailsId.copy();
        this.nomineeId = other.nomineeId == null ? null : other.nomineeId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.policyUsersId = other.policyUsersId == null ? null : other.policyUsersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PolicyCriteria copy() {
        return new PolicyCriteria(this);
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

    public LongFilter getPolicyAmount() {
        return policyAmount;
    }

    public LongFilter policyAmount() {
        if (policyAmount == null) {
            policyAmount = new LongFilter();
        }
        return policyAmount;
    }

    public void setPolicyAmount(LongFilter policyAmount) {
        this.policyAmount = policyAmount;
    }

    public StringFilter getPolicyNumber() {
        return policyNumber;
    }

    public StringFilter policyNumber() {
        if (policyNumber == null) {
            policyNumber = new StringFilter();
        }
        return policyNumber;
    }

    public void setPolicyNumber(StringFilter policyNumber) {
        this.policyNumber = policyNumber;
    }

    public LongFilter getTerm() {
        return term;
    }

    public LongFilter term() {
        if (term == null) {
            term = new LongFilter();
        }
        return term;
    }

    public void setTerm(LongFilter term) {
        this.term = term;
    }

    public LongFilter getPpt() {
        return ppt;
    }

    public LongFilter ppt() {
        if (ppt == null) {
            ppt = new LongFilter();
        }
        return ppt;
    }

    public void setPpt(LongFilter ppt) {
        this.ppt = ppt;
    }

    public StringFilter getCommDate() {
        return commDate;
    }

    public StringFilter commDate() {
        if (commDate == null) {
            commDate = new StringFilter();
        }
        return commDate;
    }

    public void setCommDate(StringFilter commDate) {
        this.commDate = commDate;
    }

    public StringFilter getProposerName() {
        return proposerName;
    }

    public StringFilter proposerName() {
        if (proposerName == null) {
            proposerName = new StringFilter();
        }
        return proposerName;
    }

    public void setProposerName(StringFilter proposerName) {
        this.proposerName = proposerName;
    }

    public LongFilter getSumAssuredAmount() {
        return sumAssuredAmount;
    }

    public LongFilter sumAssuredAmount() {
        if (sumAssuredAmount == null) {
            sumAssuredAmount = new LongFilter();
        }
        return sumAssuredAmount;
    }

    public void setSumAssuredAmount(LongFilter sumAssuredAmount) {
        this.sumAssuredAmount = sumAssuredAmount;
    }

    public PremiumModeFilter getPremiumMode() {
        return premiumMode;
    }

    public PremiumModeFilter premiumMode() {
        if (premiumMode == null) {
            premiumMode = new PremiumModeFilter();
        }
        return premiumMode;
    }

    public void setPremiumMode(PremiumModeFilter premiumMode) {
        this.premiumMode = premiumMode;
    }

    public LongFilter getBasicPremium() {
        return basicPremium;
    }

    public LongFilter basicPremium() {
        if (basicPremium == null) {
            basicPremium = new LongFilter();
        }
        return basicPremium;
    }

    public void setBasicPremium(LongFilter basicPremium) {
        this.basicPremium = basicPremium;
    }

    public LongFilter getExtraPremium() {
        return extraPremium;
    }

    public LongFilter extraPremium() {
        if (extraPremium == null) {
            extraPremium = new LongFilter();
        }
        return extraPremium;
    }

    public void setExtraPremium(LongFilter extraPremium) {
        this.extraPremium = extraPremium;
    }

    public StringFilter getGst() {
        return gst;
    }

    public StringFilter gst() {
        if (gst == null) {
            gst = new StringFilter();
        }
        return gst;
    }

    public void setGst(StringFilter gst) {
        this.gst = gst;
    }

    public PolicyStatusFilter getStatus() {
        return status;
    }

    public PolicyStatusFilter status() {
        if (status == null) {
            status = new PolicyStatusFilter();
        }
        return status;
    }

    public void setStatus(PolicyStatusFilter status) {
        this.status = status;
    }

    public StringFilter getTotalPremiun() {
        return totalPremiun;
    }

    public StringFilter totalPremiun() {
        if (totalPremiun == null) {
            totalPremiun = new StringFilter();
        }
        return totalPremiun;
    }

    public void setTotalPremiun(StringFilter totalPremiun) {
        this.totalPremiun = totalPremiun;
    }

    public StringFilter getGstFirstYear() {
        return gstFirstYear;
    }

    public StringFilter gstFirstYear() {
        if (gstFirstYear == null) {
            gstFirstYear = new StringFilter();
        }
        return gstFirstYear;
    }

    public void setGstFirstYear(StringFilter gstFirstYear) {
        this.gstFirstYear = gstFirstYear;
    }

    public StringFilter getNetPremium() {
        return netPremium;
    }

    public StringFilter netPremium() {
        if (netPremium == null) {
            netPremium = new StringFilter();
        }
        return netPremium;
    }

    public void setNetPremium(StringFilter netPremium) {
        this.netPremium = netPremium;
    }

    public StringFilter getTaxBeneficiary() {
        return taxBeneficiary;
    }

    public StringFilter taxBeneficiary() {
        if (taxBeneficiary == null) {
            taxBeneficiary = new StringFilter();
        }
        return taxBeneficiary;
    }

    public void setTaxBeneficiary(StringFilter taxBeneficiary) {
        this.taxBeneficiary = taxBeneficiary;
    }

    public BooleanFilter getPolicyReceived() {
        return policyReceived;
    }

    public BooleanFilter policyReceived() {
        if (policyReceived == null) {
            policyReceived = new BooleanFilter();
        }
        return policyReceived;
    }

    public void setPolicyReceived(BooleanFilter policyReceived) {
        this.policyReceived = policyReceived;
    }

    public LongFilter getPreviousPolicy() {
        return previousPolicy;
    }

    public LongFilter previousPolicy() {
        if (previousPolicy == null) {
            previousPolicy = new LongFilter();
        }
        return previousPolicy;
    }

    public void setPreviousPolicy(LongFilter previousPolicy) {
        this.previousPolicy = previousPolicy;
    }

    public StringFilter getPolicyStartDate() {
        return policyStartDate;
    }

    public StringFilter policyStartDate() {
        if (policyStartDate == null) {
            policyStartDate = new StringFilter();
        }
        return policyStartDate;
    }

    public void setPolicyStartDate(StringFilter policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public StringFilter getPolicyEndDate() {
        return policyEndDate;
    }

    public StringFilter policyEndDate() {
        if (policyEndDate == null) {
            policyEndDate = new StringFilter();
        }
        return policyEndDate;
    }

    public void setPolicyEndDate(StringFilter policyEndDate) {
        this.policyEndDate = policyEndDate;
    }

    public StringFilter getPeriod() {
        return period;
    }

    public StringFilter period() {
        if (period == null) {
            period = new StringFilter();
        }
        return period;
    }

    public void setPeriod(StringFilter period) {
        this.period = period;
    }

    public BooleanFilter getClaimDone() {
        return claimDone;
    }

    public BooleanFilter claimDone() {
        if (claimDone == null) {
            claimDone = new BooleanFilter();
        }
        return claimDone;
    }

    public void setClaimDone(BooleanFilter claimDone) {
        this.claimDone = claimDone;
    }

    public BooleanFilter getFreeHeathCheckup() {
        return freeHeathCheckup;
    }

    public BooleanFilter freeHeathCheckup() {
        if (freeHeathCheckup == null) {
            freeHeathCheckup = new BooleanFilter();
        }
        return freeHeathCheckup;
    }

    public void setFreeHeathCheckup(BooleanFilter freeHeathCheckup) {
        this.freeHeathCheckup = freeHeathCheckup;
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

    public LongFilter getNoOfYear() {
        return noOfYear;
    }

    public LongFilter noOfYear() {
        if (noOfYear == null) {
            noOfYear = new LongFilter();
        }
        return noOfYear;
    }

    public void setNoOfYear(LongFilter noOfYear) {
        this.noOfYear = noOfYear;
    }

    public StringFilter getFloaterSum() {
        return floaterSum;
    }

    public StringFilter floaterSum() {
        if (floaterSum == null) {
            floaterSum = new StringFilter();
        }
        return floaterSum;
    }

    public void setFloaterSum(StringFilter floaterSum) {
        this.floaterSum = floaterSum;
    }

    public StringFilter getTpa() {
        return tpa;
    }

    public StringFilter tpa() {
        if (tpa == null) {
            tpa = new StringFilter();
        }
        return tpa;
    }

    public void setTpa(StringFilter tpa) {
        this.tpa = tpa;
    }

    public StringFilter getPaymentDate() {
        return paymentDate;
    }

    public StringFilter paymentDate() {
        if (paymentDate == null) {
            paymentDate = new StringFilter();
        }
        return paymentDate;
    }

    public void setPaymentDate(StringFilter paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PolicyTypeFilter getPolicyType() {
        return policyType;
    }

    public PolicyTypeFilter policyType() {
        if (policyType == null) {
            policyType = new PolicyTypeFilter();
        }
        return policyType;
    }

    public void setPolicyType(PolicyTypeFilter policyType) {
        this.policyType = policyType;
    }

    public StringFilter getPaToOwner() {
        return paToOwner;
    }

    public StringFilter paToOwner() {
        if (paToOwner == null) {
            paToOwner = new StringFilter();
        }
        return paToOwner;
    }

    public void setPaToOwner(StringFilter paToOwner) {
        this.paToOwner = paToOwner;
    }

    public StringFilter getPaToOther() {
        return paToOther;
    }

    public StringFilter paToOther() {
        if (paToOther == null) {
            paToOther = new StringFilter();
        }
        return paToOther;
    }

    public void setPaToOther(StringFilter paToOther) {
        this.paToOther = paToOther;
    }

    public LongFilter getLoading() {
        return loading;
    }

    public LongFilter loading() {
        if (loading == null) {
            loading = new LongFilter();
        }
        return loading;
    }

    public void setLoading(LongFilter loading) {
        this.loading = loading;
    }

    public StringFilter getRiskCoveredFrom() {
        return riskCoveredFrom;
    }

    public StringFilter riskCoveredFrom() {
        if (riskCoveredFrom == null) {
            riskCoveredFrom = new StringFilter();
        }
        return riskCoveredFrom;
    }

    public void setRiskCoveredFrom(StringFilter riskCoveredFrom) {
        this.riskCoveredFrom = riskCoveredFrom;
    }

    public StringFilter getRiskCoveredTo() {
        return riskCoveredTo;
    }

    public StringFilter riskCoveredTo() {
        if (riskCoveredTo == null) {
            riskCoveredTo = new StringFilter();
        }
        return riskCoveredTo;
    }

    public void setRiskCoveredTo(StringFilter riskCoveredTo) {
        this.riskCoveredTo = riskCoveredTo;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public StringFilter notes() {
        if (notes == null) {
            notes = new StringFilter();
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public StringFilter getFreeField5() {
        return freeField5;
    }

    public StringFilter freeField5() {
        if (freeField5 == null) {
            freeField5 = new StringFilter();
        }
        return freeField5;
    }

    public void setFreeField5(StringFilter freeField5) {
        this.freeField5 = freeField5;
    }

    public StringFilter getMaturityDate() {
        return maturityDate;
    }

    public StringFilter maturityDate() {
        if (maturityDate == null) {
            maturityDate = new StringFilter();
        }
        return maturityDate;
    }

    public void setMaturityDate(StringFilter maturityDate) {
        this.maturityDate = maturityDate;
    }

    public StringFilter getUinNo() {
        return uinNo;
    }

    public StringFilter uinNo() {
        if (uinNo == null) {
            uinNo = new StringFilter();
        }
        return uinNo;
    }

    public void setUinNo(StringFilter uinNo) {
        this.uinNo = uinNo;
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

    public LongFilter getAgencyId() {
        return agencyId;
    }

    public LongFilter agencyId() {
        if (agencyId == null) {
            agencyId = new LongFilter();
        }
        return agencyId;
    }

    public void setAgencyId(LongFilter agencyId) {
        this.agencyId = agencyId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getPremiunDetailsId() {
        return premiunDetailsId;
    }

    public LongFilter premiunDetailsId() {
        if (premiunDetailsId == null) {
            premiunDetailsId = new LongFilter();
        }
        return premiunDetailsId;
    }

    public void setPremiunDetailsId(LongFilter premiunDetailsId) {
        this.premiunDetailsId = premiunDetailsId;
    }

    public LongFilter getVehicleClassId() {
        return vehicleClassId;
    }

    public LongFilter vehicleClassId() {
        if (vehicleClassId == null) {
            vehicleClassId = new LongFilter();
        }
        return vehicleClassId;
    }

    public void setVehicleClassId(LongFilter vehicleClassId) {
        this.vehicleClassId = vehicleClassId;
    }

    public LongFilter getBankDetailsId() {
        return bankDetailsId;
    }

    public LongFilter bankDetailsId() {
        if (bankDetailsId == null) {
            bankDetailsId = new LongFilter();
        }
        return bankDetailsId;
    }

    public void setBankDetailsId(LongFilter bankDetailsId) {
        this.bankDetailsId = bankDetailsId;
    }

    public LongFilter getNomineeId() {
        return nomineeId;
    }

    public LongFilter nomineeId() {
        if (nomineeId == null) {
            nomineeId = new LongFilter();
        }
        return nomineeId;
    }

    public void setNomineeId(LongFilter nomineeId) {
        this.nomineeId = nomineeId;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    public LongFilter getPolicyUsersId() {
        return policyUsersId;
    }

    public LongFilter policyUsersId() {
        if (policyUsersId == null) {
            policyUsersId = new LongFilter();
        }
        return policyUsersId;
    }

    public void setPolicyUsersId(LongFilter policyUsersId) {
        this.policyUsersId = policyUsersId;
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
        final PolicyCriteria that = (PolicyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(policyAmount, that.policyAmount) &&
            Objects.equals(policyNumber, that.policyNumber) &&
            Objects.equals(term, that.term) &&
            Objects.equals(ppt, that.ppt) &&
            Objects.equals(commDate, that.commDate) &&
            Objects.equals(proposerName, that.proposerName) &&
            Objects.equals(sumAssuredAmount, that.sumAssuredAmount) &&
            Objects.equals(premiumMode, that.premiumMode) &&
            Objects.equals(basicPremium, that.basicPremium) &&
            Objects.equals(extraPremium, that.extraPremium) &&
            Objects.equals(gst, that.gst) &&
            Objects.equals(status, that.status) &&
            Objects.equals(totalPremiun, that.totalPremiun) &&
            Objects.equals(gstFirstYear, that.gstFirstYear) &&
            Objects.equals(netPremium, that.netPremium) &&
            Objects.equals(taxBeneficiary, that.taxBeneficiary) &&
            Objects.equals(policyReceived, that.policyReceived) &&
            Objects.equals(previousPolicy, that.previousPolicy) &&
            Objects.equals(policyStartDate, that.policyStartDate) &&
            Objects.equals(policyEndDate, that.policyEndDate) &&
            Objects.equals(period, that.period) &&
            Objects.equals(claimDone, that.claimDone) &&
            Objects.equals(freeHeathCheckup, that.freeHeathCheckup) &&
            Objects.equals(zone, that.zone) &&
            Objects.equals(noOfYear, that.noOfYear) &&
            Objects.equals(floaterSum, that.floaterSum) &&
            Objects.equals(tpa, that.tpa) &&
            Objects.equals(paymentDate, that.paymentDate) &&
            Objects.equals(policyType, that.policyType) &&
            Objects.equals(paToOwner, that.paToOwner) &&
            Objects.equals(paToOther, that.paToOther) &&
            Objects.equals(loading, that.loading) &&
            Objects.equals(riskCoveredFrom, that.riskCoveredFrom) &&
            Objects.equals(riskCoveredTo, that.riskCoveredTo) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(maturityDate, that.maturityDate) &&
            Objects.equals(uinNo, that.uinNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(agencyId, that.agencyId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(premiunDetailsId, that.premiunDetailsId) &&
            Objects.equals(vehicleClassId, that.vehicleClassId) &&
            Objects.equals(bankDetailsId, that.bankDetailsId) &&
            Objects.equals(nomineeId, that.nomineeId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(policyUsersId, that.policyUsersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            policyAmount,
            policyNumber,
            term,
            ppt,
            commDate,
            proposerName,
            sumAssuredAmount,
            premiumMode,
            basicPremium,
            extraPremium,
            gst,
            status,
            totalPremiun,
            gstFirstYear,
            netPremium,
            taxBeneficiary,
            policyReceived,
            previousPolicy,
            policyStartDate,
            policyEndDate,
            period,
            claimDone,
            freeHeathCheckup,
            zone,
            noOfYear,
            floaterSum,
            tpa,
            paymentDate,
            policyType,
            paToOwner,
            paToOther,
            loading,
            riskCoveredFrom,
            riskCoveredTo,
            notes,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            maturityDate,
            uinNo,
            lastModified,
            lastModifiedBy,
            agencyId,
            companyId,
            productId,
            premiunDetailsId,
            vehicleClassId,
            bankDetailsId,
            nomineeId,
            memberId,
            policyUsersId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PolicyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (policyAmount != null ? "policyAmount=" + policyAmount + ", " : "") +
            (policyNumber != null ? "policyNumber=" + policyNumber + ", " : "") +
            (term != null ? "term=" + term + ", " : "") +
            (ppt != null ? "ppt=" + ppt + ", " : "") +
            (commDate != null ? "commDate=" + commDate + ", " : "") +
            (proposerName != null ? "proposerName=" + proposerName + ", " : "") +
            (sumAssuredAmount != null ? "sumAssuredAmount=" + sumAssuredAmount + ", " : "") +
            (premiumMode != null ? "premiumMode=" + premiumMode + ", " : "") +
            (basicPremium != null ? "basicPremium=" + basicPremium + ", " : "") +
            (extraPremium != null ? "extraPremium=" + extraPremium + ", " : "") +
            (gst != null ? "gst=" + gst + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (totalPremiun != null ? "totalPremiun=" + totalPremiun + ", " : "") +
            (gstFirstYear != null ? "gstFirstYear=" + gstFirstYear + ", " : "") +
            (netPremium != null ? "netPremium=" + netPremium + ", " : "") +
            (taxBeneficiary != null ? "taxBeneficiary=" + taxBeneficiary + ", " : "") +
            (policyReceived != null ? "policyReceived=" + policyReceived + ", " : "") +
            (previousPolicy != null ? "previousPolicy=" + previousPolicy + ", " : "") +
            (policyStartDate != null ? "policyStartDate=" + policyStartDate + ", " : "") +
            (policyEndDate != null ? "policyEndDate=" + policyEndDate + ", " : "") +
            (period != null ? "period=" + period + ", " : "") +
            (claimDone != null ? "claimDone=" + claimDone + ", " : "") +
            (freeHeathCheckup != null ? "freeHeathCheckup=" + freeHeathCheckup + ", " : "") +
            (zone != null ? "zone=" + zone + ", " : "") +
            (noOfYear != null ? "noOfYear=" + noOfYear + ", " : "") +
            (floaterSum != null ? "floaterSum=" + floaterSum + ", " : "") +
            (tpa != null ? "tpa=" + tpa + ", " : "") +
            (paymentDate != null ? "paymentDate=" + paymentDate + ", " : "") +
            (policyType != null ? "policyType=" + policyType + ", " : "") +
            (paToOwner != null ? "paToOwner=" + paToOwner + ", " : "") +
            (paToOther != null ? "paToOther=" + paToOther + ", " : "") +
            (loading != null ? "loading=" + loading + ", " : "") +
            (riskCoveredFrom != null ? "riskCoveredFrom=" + riskCoveredFrom + ", " : "") +
            (riskCoveredTo != null ? "riskCoveredTo=" + riskCoveredTo + ", " : "") +
            (notes != null ? "notes=" + notes + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (maturityDate != null ? "maturityDate=" + maturityDate + ", " : "") +
            (uinNo != null ? "uinNo=" + uinNo + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (agencyId != null ? "agencyId=" + agencyId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (premiunDetailsId != null ? "premiunDetailsId=" + premiunDetailsId + ", " : "") +
            (vehicleClassId != null ? "vehicleClassId=" + vehicleClassId + ", " : "") +
            (bankDetailsId != null ? "bankDetailsId=" + bankDetailsId + ", " : "") +
            (nomineeId != null ? "nomineeId=" + nomineeId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (policyUsersId != null ? "policyUsersId=" + policyUsersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
