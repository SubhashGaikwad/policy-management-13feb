package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.UsersType;
import com.mycompany.myapp.repository.UsersTypeRepository;
import com.mycompany.myapp.service.criteria.UsersTypeCriteria;
import com.mycompany.myapp.service.dto.UsersTypeDTO;
import com.mycompany.myapp.service.mapper.UsersTypeMapper;
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
 * Service for executing complex queries for {@link UsersType} entities in the database.
 * The main input is a {@link UsersTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsersTypeDTO} or a {@link Page} of {@link UsersTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsersTypeQueryService extends QueryService<UsersType> {

    private final Logger log = LoggerFactory.getLogger(UsersTypeQueryService.class);

    private final UsersTypeRepository usersTypeRepository;

    private final UsersTypeMapper usersTypeMapper;

    public UsersTypeQueryService(UsersTypeRepository usersTypeRepository, UsersTypeMapper usersTypeMapper) {
        this.usersTypeRepository = usersTypeRepository;
        this.usersTypeMapper = usersTypeMapper;
    }

    /**
     * Return a {@link List} of {@link UsersTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsersTypeDTO> findByCriteria(UsersTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UsersType> specification = createSpecification(criteria);
        return usersTypeMapper.toDto(usersTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsersTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsersTypeDTO> findByCriteria(UsersTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsersType> specification = createSpecification(criteria);
        return usersTypeRepository.findAll(specification, page).map(usersTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsersTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsersType> specification = createSpecification(criteria);
        return usersTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link UsersTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsersType> createSpecification(UsersTypeCriteria criteria) {
        Specification<UsersType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsersType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), UsersType_.name));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), UsersType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), UsersType_.lastModifiedBy));
            }
            if (criteria.getPolicyUsersId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPolicyUsersId(),
                            root -> root.join(UsersType_.policyUsers, JoinType.LEFT).get(PolicyUsers_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
