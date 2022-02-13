package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CompanyType;
import com.mycompany.myapp.repository.CompanyTypeRepository;
import com.mycompany.myapp.service.criteria.CompanyTypeCriteria;
import com.mycompany.myapp.service.dto.CompanyTypeDTO;
import com.mycompany.myapp.service.mapper.CompanyTypeMapper;
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
 * Service for executing complex queries for {@link CompanyType} entities in the database.
 * The main input is a {@link CompanyTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyTypeDTO} or a {@link Page} of {@link CompanyTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyTypeQueryService extends QueryService<CompanyType> {

    private final Logger log = LoggerFactory.getLogger(CompanyTypeQueryService.class);

    private final CompanyTypeRepository companyTypeRepository;

    private final CompanyTypeMapper companyTypeMapper;

    public CompanyTypeQueryService(CompanyTypeRepository companyTypeRepository, CompanyTypeMapper companyTypeMapper) {
        this.companyTypeRepository = companyTypeRepository;
        this.companyTypeMapper = companyTypeMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyTypeDTO> findByCriteria(CompanyTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CompanyType> specification = createSpecification(criteria);
        return companyTypeMapper.toDto(companyTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyTypeDTO> findByCriteria(CompanyTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CompanyType> specification = createSpecification(criteria);
        return companyTypeRepository.findAll(specification, page).map(companyTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CompanyType> specification = createSpecification(criteria);
        return companyTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CompanyType> createSpecification(CompanyTypeCriteria criteria) {
        Specification<CompanyType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CompanyType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CompanyType_.name));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), CompanyType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), CompanyType_.lastModifiedBy));
            }
            if (criteria.getCompanyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCompanyId(), root -> root.join(CompanyType_.company, JoinType.LEFT).get(Company_.id))
                    );
            }
        }
        return specification;
    }
}
