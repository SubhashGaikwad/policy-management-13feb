package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.BankDetails;
import com.mycompany.myapp.repository.BankDetailsRepository;
import com.mycompany.myapp.service.criteria.BankDetailsCriteria;
import com.mycompany.myapp.service.dto.BankDetailsDTO;
import com.mycompany.myapp.service.mapper.BankDetailsMapper;
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
 * Service for executing complex queries for {@link BankDetails} entities in the database.
 * The main input is a {@link BankDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankDetailsDTO} or a {@link Page} of {@link BankDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankDetailsQueryService extends QueryService<BankDetails> {

    private final Logger log = LoggerFactory.getLogger(BankDetailsQueryService.class);

    private final BankDetailsRepository bankDetailsRepository;

    private final BankDetailsMapper bankDetailsMapper;

    public BankDetailsQueryService(BankDetailsRepository bankDetailsRepository, BankDetailsMapper bankDetailsMapper) {
        this.bankDetailsRepository = bankDetailsRepository;
        this.bankDetailsMapper = bankDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link BankDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankDetailsDTO> findByCriteria(BankDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BankDetails> specification = createSpecification(criteria);
        return bankDetailsMapper.toDto(bankDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDetailsDTO> findByCriteria(BankDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BankDetails> specification = createSpecification(criteria);
        return bankDetailsRepository.findAll(specification, page).map(bankDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BankDetails> specification = createSpecification(criteria);
        return bankDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link BankDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BankDetails> createSpecification(BankDetailsCriteria criteria) {
        Specification<BankDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BankDetails_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BankDetails_.name));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), BankDetails_.branch));
            }
            if (criteria.getBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchCode(), BankDetails_.branchCode));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCity(), BankDetails_.city));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactNo(), BankDetails_.contactNo));
            }
            if (criteria.getIfcCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfcCode(), BankDetails_.ifcCode));
            }
            if (criteria.getAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccount(), BankDetails_.account));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), BankDetails_.accountType));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), BankDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BankDetails_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(BankDetails_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
