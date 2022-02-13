package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Nominee;
import com.mycompany.myapp.repository.NomineeRepository;
import com.mycompany.myapp.service.criteria.NomineeCriteria;
import com.mycompany.myapp.service.dto.NomineeDTO;
import com.mycompany.myapp.service.mapper.NomineeMapper;
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
 * Service for executing complex queries for {@link Nominee} entities in the database.
 * The main input is a {@link NomineeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NomineeDTO} or a {@link Page} of {@link NomineeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NomineeQueryService extends QueryService<Nominee> {

    private final Logger log = LoggerFactory.getLogger(NomineeQueryService.class);

    private final NomineeRepository nomineeRepository;

    private final NomineeMapper nomineeMapper;

    public NomineeQueryService(NomineeRepository nomineeRepository, NomineeMapper nomineeMapper) {
        this.nomineeRepository = nomineeRepository;
        this.nomineeMapper = nomineeMapper;
    }

    /**
     * Return a {@link List} of {@link NomineeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NomineeDTO> findByCriteria(NomineeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeMapper.toDto(nomineeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NomineeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NomineeDTO> findByCriteria(NomineeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeRepository.findAll(specification, page).map(nomineeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NomineeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nominee> specification = createSpecification(criteria);
        return nomineeRepository.count(specification);
    }

    /**
     * Function to convert {@link NomineeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nominee> createSpecification(NomineeCriteria criteria) {
        Specification<Nominee> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nominee_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getName(), Nominee_.name));
            }
            if (criteria.getRelation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelation(), Nominee_.relation));
            }
            if (criteria.getNomineePercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNomineePercentage(), Nominee_.nomineePercentage));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactNo(), Nominee_.contactNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Nominee_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Nominee_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(Nominee_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
