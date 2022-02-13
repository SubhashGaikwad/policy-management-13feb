package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.VehicleClass;
import com.mycompany.myapp.repository.VehicleClassRepository;
import com.mycompany.myapp.service.criteria.VehicleClassCriteria;
import com.mycompany.myapp.service.dto.VehicleClassDTO;
import com.mycompany.myapp.service.mapper.VehicleClassMapper;
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
 * Service for executing complex queries for {@link VehicleClass} entities in the database.
 * The main input is a {@link VehicleClassCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleClassDTO} or a {@link Page} of {@link VehicleClassDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleClassQueryService extends QueryService<VehicleClass> {

    private final Logger log = LoggerFactory.getLogger(VehicleClassQueryService.class);

    private final VehicleClassRepository vehicleClassRepository;

    private final VehicleClassMapper vehicleClassMapper;

    public VehicleClassQueryService(VehicleClassRepository vehicleClassRepository, VehicleClassMapper vehicleClassMapper) {
        this.vehicleClassRepository = vehicleClassRepository;
        this.vehicleClassMapper = vehicleClassMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleClassDTO> findByCriteria(VehicleClassCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleClass> specification = createSpecification(criteria);
        return vehicleClassMapper.toDto(vehicleClassRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleClassDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleClassDTO> findByCriteria(VehicleClassCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleClass> specification = createSpecification(criteria);
        return vehicleClassRepository.findAll(specification, page).map(vehicleClassMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleClassCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleClass> specification = createSpecification(criteria);
        return vehicleClassRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleClassCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleClass> createSpecification(VehicleClassCriteria criteria) {
        Specification<VehicleClass> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleClass_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getName(), VehicleClass_.name));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), VehicleClass_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), VehicleClass_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(VehicleClass_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
