package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.repository.PolicyUsersRepository;
import com.mycompany.myapp.service.criteria.PolicyUsersCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersMapper;
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
 * Service for executing complex queries for {@link PolicyUsers} entities in the database.
 * The main input is a {@link PolicyUsersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PolicyUsersDTO} or a {@link Page} of {@link PolicyUsersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PolicyUsersQueryService extends QueryService<PolicyUsers> {

    private final Logger log = LoggerFactory.getLogger(PolicyUsersQueryService.class);

    private final PolicyUsersRepository policyUsersRepository;

    private final PolicyUsersMapper policyUsersMapper;

    public PolicyUsersQueryService(PolicyUsersRepository policyUsersRepository, PolicyUsersMapper policyUsersMapper) {
        this.policyUsersRepository = policyUsersRepository;
        this.policyUsersMapper = policyUsersMapper;
    }

    /**
     * Return a {@link List} of {@link PolicyUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PolicyUsersDTO> findByCriteria(PolicyUsersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PolicyUsers> specification = createSpecification(criteria);
        return policyUsersMapper.toDto(policyUsersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PolicyUsersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PolicyUsersDTO> findByCriteria(PolicyUsersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PolicyUsers> specification = createSpecification(criteria);
        return policyUsersRepository.findAll(specification, page).map(policyUsersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PolicyUsersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PolicyUsers> specification = createSpecification(criteria);
        return policyUsersRepository.count(specification);
    }

    /**
     * Function to convert {@link PolicyUsersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PolicyUsers> createSpecification(PolicyUsersCriteria criteria) {
        Specification<PolicyUsers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PolicyUsers_.id));
            }
            if (criteria.getGroupCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupCode(), PolicyUsers_.groupCode));
            }
            if (criteria.getGroupHeadName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGroupHeadName(), PolicyUsers_.groupHeadName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), PolicyUsers_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), PolicyUsers_.lastName));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthDate(), PolicyUsers_.birthDate));
            }
            if (criteria.getMarriageDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarriageDate(), PolicyUsers_.marriageDate));
            }
            if (criteria.getUserTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserTypeId(), PolicyUsers_.userTypeId));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), PolicyUsers_.username));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), PolicyUsers_.password));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), PolicyUsers_.email));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), PolicyUsers_.imageUrl));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), PolicyUsers_.status));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), PolicyUsers_.activated));
            }
            if (criteria.getLicenceExpiryDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLicenceExpiryDate(), PolicyUsers_.licenceExpiryDate));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), PolicyUsers_.mobileNo));
            }
            if (criteria.getAadharCardNuber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharCardNuber(), PolicyUsers_.aadharCardNuber));
            }
            if (criteria.getPancardNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPancardNumber(), PolicyUsers_.pancardNumber));
            }
            if (criteria.getOneTimePassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOneTimePassword(), PolicyUsers_.oneTimePassword));
            }
            if (criteria.getOtpExpiryTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtpExpiryTime(), PolicyUsers_.otpExpiryTime));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), PolicyUsers_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PolicyUsers_.lastModifiedBy));
            }
            if (criteria.getUsersTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsersTypeId(),
                            root -> root.join(PolicyUsers_.usersType, JoinType.LEFT).get(UsersType_.id)
                        )
                    );
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(PolicyUsers_.policies, JoinType.LEFT).get(Policy_.id))
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(PolicyUsers_.addresses, JoinType.LEFT).get(Address_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
