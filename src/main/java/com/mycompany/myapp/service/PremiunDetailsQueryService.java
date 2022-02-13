package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PremiunDetails;
import com.mycompany.myapp.repository.PremiunDetailsRepository;
import com.mycompany.myapp.service.criteria.PremiunDetailsCriteria;
import com.mycompany.myapp.service.dto.PremiunDetailsDTO;
import com.mycompany.myapp.service.mapper.PremiunDetailsMapper;
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
 * Service for executing complex queries for {@link PremiunDetails} entities in the database.
 * The main input is a {@link PremiunDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PremiunDetailsDTO} or a {@link Page} of {@link PremiunDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PremiunDetailsQueryService extends QueryService<PremiunDetails> {

    private final Logger log = LoggerFactory.getLogger(PremiunDetailsQueryService.class);

    private final PremiunDetailsRepository premiunDetailsRepository;

    private final PremiunDetailsMapper premiunDetailsMapper;

    public PremiunDetailsQueryService(PremiunDetailsRepository premiunDetailsRepository, PremiunDetailsMapper premiunDetailsMapper) {
        this.premiunDetailsRepository = premiunDetailsRepository;
        this.premiunDetailsMapper = premiunDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PremiunDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PremiunDetailsDTO> findByCriteria(PremiunDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PremiunDetails> specification = createSpecification(criteria);
        return premiunDetailsMapper.toDto(premiunDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PremiunDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PremiunDetailsDTO> findByCriteria(PremiunDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PremiunDetails> specification = createSpecification(criteria);
        return premiunDetailsRepository.findAll(specification, page).map(premiunDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PremiunDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PremiunDetails> specification = createSpecification(criteria);
        return premiunDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PremiunDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PremiunDetails> createSpecification(PremiunDetailsCriteria criteria) {
        Specification<PremiunDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PremiunDetails_.id));
            }
            if (criteria.getPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPremium(), PremiunDetails_.premium));
            }
            if (criteria.getOtherLoading() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherLoading(), PremiunDetails_.otherLoading));
            }
            if (criteria.getOtherDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherDiscount(), PremiunDetails_.otherDiscount));
            }
            if (criteria.getAddOnPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAddOnPremium(), PremiunDetails_.addOnPremium));
            }
            if (criteria.getLiabilityPremium() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLiabilityPremium(), PremiunDetails_.liabilityPremium));
            }
            if (criteria.getOdPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOdPremium(), PremiunDetails_.odPremium));
            }
            if (criteria.getPersonalAccidentDiscount() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getPersonalAccidentDiscount(), PremiunDetails_.personalAccidentDiscount));
            }
            if (criteria.getPersonalAccident() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPersonalAccident(), PremiunDetails_.personalAccident));
            }
            if (criteria.getGrossPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrossPremium(), PremiunDetails_.grossPremium));
            }
            if (criteria.getGst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGst(), PremiunDetails_.gst));
            }
            if (criteria.getNetPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetPremium(), PremiunDetails_.netPremium));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), PremiunDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PremiunDetails_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(PremiunDetails_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
