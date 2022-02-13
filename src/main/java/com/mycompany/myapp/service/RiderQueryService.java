package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Rider;
import com.mycompany.myapp.repository.RiderRepository;
import com.mycompany.myapp.service.criteria.RiderCriteria;
import com.mycompany.myapp.service.dto.RiderDTO;
import com.mycompany.myapp.service.mapper.RiderMapper;
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
 * Service for executing complex queries for {@link Rider} entities in the database.
 * The main input is a {@link RiderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RiderDTO} or a {@link Page} of {@link RiderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RiderQueryService extends QueryService<Rider> {

    private final Logger log = LoggerFactory.getLogger(RiderQueryService.class);

    private final RiderRepository riderRepository;

    private final RiderMapper riderMapper;

    public RiderQueryService(RiderRepository riderRepository, RiderMapper riderMapper) {
        this.riderRepository = riderRepository;
        this.riderMapper = riderMapper;
    }

    /**
     * Return a {@link List} of {@link RiderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RiderDTO> findByCriteria(RiderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rider> specification = createSpecification(criteria);
        return riderMapper.toDto(riderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RiderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RiderDTO> findByCriteria(RiderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rider> specification = createSpecification(criteria);
        return riderRepository.findAll(specification, page).map(riderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RiderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rider> specification = createSpecification(criteria);
        return riderRepository.count(specification);
    }

    /**
     * Function to convert {@link RiderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rider> createSpecification(RiderCriteria criteria) {
        Specification<Rider> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rider_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Rider_.name));
            }
            if (criteria.getCommDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommDate(), Rider_.commDate));
            }
            if (criteria.getSum() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSum(), Rider_.sum));
            }
            if (criteria.getTerm() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerm(), Rider_.term));
            }
            if (criteria.getPpt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPpt(), Rider_.ppt));
            }
            if (criteria.getPremium() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPremium(), Rider_.premium));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Rider_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Rider_.lastModifiedBy));
            }
        }
        return specification;
    }
}
