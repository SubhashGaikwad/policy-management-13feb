package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.VehicleDetails;
import com.mycompany.myapp.repository.VehicleDetailsRepository;
import com.mycompany.myapp.service.criteria.VehicleDetailsCriteria;
import com.mycompany.myapp.service.dto.VehicleDetailsDTO;
import com.mycompany.myapp.service.mapper.VehicleDetailsMapper;
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
 * Service for executing complex queries for {@link VehicleDetails} entities in the database.
 * The main input is a {@link VehicleDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleDetailsDTO} or a {@link Page} of {@link VehicleDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleDetailsQueryService extends QueryService<VehicleDetails> {

    private final Logger log = LoggerFactory.getLogger(VehicleDetailsQueryService.class);

    private final VehicleDetailsRepository vehicleDetailsRepository;

    private final VehicleDetailsMapper vehicleDetailsMapper;

    public VehicleDetailsQueryService(VehicleDetailsRepository vehicleDetailsRepository, VehicleDetailsMapper vehicleDetailsMapper) {
        this.vehicleDetailsRepository = vehicleDetailsRepository;
        this.vehicleDetailsMapper = vehicleDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleDetailsDTO> findByCriteria(VehicleDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VehicleDetails> specification = createSpecification(criteria);
        return vehicleDetailsMapper.toDto(vehicleDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDetailsDTO> findByCriteria(VehicleDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VehicleDetails> specification = createSpecification(criteria);
        return vehicleDetailsRepository.findAll(specification, page).map(vehicleDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VehicleDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VehicleDetails> specification = createSpecification(criteria);
        return vehicleDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link VehicleDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VehicleDetails> createSpecification(VehicleDetailsCriteria criteria) {
        Specification<VehicleDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VehicleDetails_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getName(), VehicleDetails_.name));
            }
            if (criteria.getInvoiceValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceValue(), VehicleDetails_.invoiceValue));
            }
            if (criteria.getIdv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdv(), VehicleDetails_.idv));
            }
            if (criteria.getEnginNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnginNumber(), VehicleDetails_.enginNumber));
            }
            if (criteria.getChassisNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChassisNumber(), VehicleDetails_.chassisNumber));
            }
            if (criteria.getRegistrationNumber() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRegistrationNumber(), VehicleDetails_.registrationNumber));
            }
            if (criteria.getSeatingCapacity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSeatingCapacity(), VehicleDetails_.seatingCapacity));
            }
            if (criteria.getZone() != null) {
                specification = specification.and(buildSpecification(criteria.getZone(), VehicleDetails_.zone));
            }
            if (criteria.getYearOfManufacturing() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getYearOfManufacturing(), VehicleDetails_.yearOfManufacturing));
            }
            if (criteria.getRegistrationDate() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRegistrationDate(), VehicleDetails_.registrationDate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), VehicleDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), VehicleDetails_.lastModifiedBy));
            }
            if (criteria.getParameterLookupId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getParameterLookupId(),
                            root -> root.join(VehicleDetails_.parameterLookups, JoinType.LEFT).get(ParameterLookup_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
