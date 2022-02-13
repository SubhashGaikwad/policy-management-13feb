package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.PolicyRepository;
import com.mycompany.myapp.service.criteria.PolicyCriteria;
import com.mycompany.myapp.service.dto.PolicyDTO;
import com.mycompany.myapp.service.mapper.PolicyMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Policy} entities in the database.
 * The main input is a {@link PolicyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PolicyDTO} or a {@link Page} of {@link PolicyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PolicyQueryService extends QueryService<Policy> {

    private final Logger log = LoggerFactory.getLogger(PolicyQueryService.class);

    private final PolicyRepository policyRepository;

    private final PolicyMapper policyMapper;

    public PolicyQueryService(PolicyRepository policyRepository, PolicyMapper policyMapper) {
        this.policyRepository = policyRepository;
        this.policyMapper = policyMapper;
    }

    /**
     * Return a {@link List} of {@link PolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PolicyDTO> findByCriteria(PolicyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Policy> specification = createSpecification(criteria);
        return policyMapper.toDto(policyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PolicyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PolicyDTO> findByCriteria(PolicyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Policy> specification = createSpecification(criteria);
        return policyRepository.findAll(specification, page).map(policyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PolicyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Policy> specification = createSpecification(criteria);
        return policyRepository.count(specification);
    }

    /**
     * Function to convert {@link PolicyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Policy> createSpecification(PolicyCriteria criteria) {
        Specification<Policy> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Policy_.id));
            }
            if (criteria.getPolicyAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPolicyAmount(), Policy_.policyAmount));
            }
            if (criteria.getPolicyNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPolicyNumber(), Policy_.policyNumber));
            }
            if (criteria.getTerm() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTerm(), Policy_.term));
            }
            if (criteria.getPpt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPpt(), Policy_.ppt));
            }
            if (criteria.getCommDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommDate(), Policy_.commDate));
            }
            if (criteria.getProposerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProposerName(), Policy_.proposerName));
            }
            if (criteria.getSumAssuredAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSumAssuredAmount(), Policy_.sumAssuredAmount));
            }
            if (criteria.getPremiumMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPremiumMode(), Policy_.premiumMode));
            }
            if (criteria.getBasicPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBasicPremium(), Policy_.basicPremium));
            }
            if (criteria.getExtraPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExtraPremium(), Policy_.extraPremium));
            }
            if (criteria.getGst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGst(), Policy_.gst));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Policy_.status));
            }
            if (criteria.getTotalPremiun() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalPremiun(), Policy_.totalPremiun));
            }
            if (criteria.getGstFirstYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstFirstYear(), Policy_.gstFirstYear));
            }
            if (criteria.getNetPremium() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNetPremium(), Policy_.netPremium));
            }
            if (criteria.getTaxBeneficiary() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxBeneficiary(), Policy_.taxBeneficiary));
            }
            if (criteria.getPolicyReceived() != null) {
                specification = specification.and(buildSpecification(criteria.getPolicyReceived(), Policy_.policyReceived));
            }
            if (criteria.getPreviousPolicy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreviousPolicy(), Policy_.previousPolicy));
            }
            if (criteria.getPolicyStartDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPolicyStartDate(), Policy_.policyStartDate));
            }
            if (criteria.getPolicyEndDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPolicyEndDate(), Policy_.policyEndDate));
            }
            if (criteria.getPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeriod(), Policy_.period));
            }
            if (criteria.getClaimDone() != null) {
                specification = specification.and(buildSpecification(criteria.getClaimDone(), Policy_.claimDone));
            }
            if (criteria.getFreeHeathCheckup() != null) {
                specification = specification.and(buildSpecification(criteria.getFreeHeathCheckup(), Policy_.freeHeathCheckup));
            }
            if (criteria.getZone() != null) {
                specification = specification.and(buildSpecification(criteria.getZone(), Policy_.zone));
            }
            if (criteria.getNoOfYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfYear(), Policy_.noOfYear));
            }
            if (criteria.getFloaterSum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFloaterSum(), Policy_.floaterSum));
            }
            if (criteria.getTpa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTpa(), Policy_.tpa));
            }
            if (criteria.getPaymentDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentDate(), Policy_.paymentDate));
            }
            if (criteria.getPolicyType() != null) {
                specification = specification.and(buildSpecification(criteria.getPolicyType(), Policy_.policyType));
            }
            if (criteria.getPaToOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaToOwner(), Policy_.paToOwner));
            }
            if (criteria.getPaToOther() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaToOther(), Policy_.paToOther));
            }
            if (criteria.getLoading() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoading(), Policy_.loading));
            }
            if (criteria.getRiskCoveredFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRiskCoveredFrom(), Policy_.riskCoveredFrom));
            }
            if (criteria.getRiskCoveredTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRiskCoveredTo(), Policy_.riskCoveredTo));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), Policy_.notes));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Policy_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Policy_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Policy_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Policy_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), Policy_.freeField5));
            }
            if (criteria.getMaturityDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaturityDate(), Policy_.maturityDate));
            }
            if (criteria.getUinNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUinNo(), Policy_.uinNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Policy_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Policy_.lastModifiedBy));
            }
            if (criteria.getAgencyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAgencyId(), root -> root.join(Policy_.agency, JoinType.LEFT).get(Agency_.id))
                    );
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(Policy_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProductId(), root -> root.join(Policy_.product, JoinType.LEFT).get(Product_.id))
                    );
            }
            if (criteria.getPremiunDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPremiunDetailsId(),
                            root -> root.join(Policy_.premiunDetails, JoinType.LEFT).get(PremiunDetails_.id)
                        )
                    );
            }
            if (criteria.getVehicleClassId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getVehicleClassId(),
                            root -> root.join(Policy_.vehicleClass, JoinType.LEFT).get(VehicleClass_.id)
                        )
                    );
            }
            if (criteria.getBankDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBankDetailsId(),
                            root -> root.join(Policy_.bankDetails, JoinType.LEFT).get(BankDetails_.id)
                        )
                    );
            }
            if (criteria.getNomineeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getNomineeId(), root -> root.join(Policy_.nominees, JoinType.LEFT).get(Nominee_.id))
                    );
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(Policy_.members, JoinType.LEFT).get(Member_.id))
                    );
            }
            if (criteria.getPolicyUsersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPolicyUsersId(),
                            root -> root.join(Policy_.policyUsers, JoinType.LEFT).get(PolicyUsers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
