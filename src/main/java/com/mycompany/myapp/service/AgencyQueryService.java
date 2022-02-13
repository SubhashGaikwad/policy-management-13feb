package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Agency;
import com.mycompany.myapp.repository.AgencyRepository;
import com.mycompany.myapp.service.criteria.AgencyCriteria;
import com.mycompany.myapp.service.dto.AgencyDTO;
import com.mycompany.myapp.service.mapper.AgencyMapper;
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
 * Service for executing complex queries for {@link Agency} entities in the database.
 * The main input is a {@link AgencyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgencyDTO} or a {@link Page} of {@link AgencyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgencyQueryService extends QueryService<Agency> {

    private final Logger log = LoggerFactory.getLogger(AgencyQueryService.class);

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    public AgencyQueryService(AgencyRepository agencyRepository, AgencyMapper agencyMapper) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
    }

    /**
     * Return a {@link List} of {@link AgencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgencyDTO> findByCriteria(AgencyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyMapper.toDto(agencyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgencyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgencyDTO> findByCriteria(AgencyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyRepository.findAll(specification, page).map(agencyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgencyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Agency> specification = createSpecification(criteria);
        return agencyRepository.count(specification);
    }

    /**
     * Function to convert {@link AgencyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Agency> createSpecification(AgencyCriteria criteria) {
        Specification<Agency> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Agency_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Agency_.name));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Agency_.address));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), Agency_.branch));
            }
            if (criteria.getBrnachCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrnachCode(), Agency_.brnachCode));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Agency_.email));
            }
            if (criteria.getCompanyTypeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompanyTypeId(), Agency_.companyTypeId));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Agency_.imageUrl));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNo(), Agency_.contactNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Agency_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Agency_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(Agency_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
