package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.repository.MemberRepository;
import com.mycompany.myapp.service.criteria.MemberCriteria;
import com.mycompany.myapp.service.dto.MemberDTO;
import com.mycompany.myapp.service.mapper.MemberMapper;
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
 * Service for executing complex queries for {@link Member} entities in the database.
 * The main input is a {@link MemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberDTO} or a {@link Page} of {@link MemberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberQueryService extends QueryService<Member> {

    private final Logger log = LoggerFactory.getLogger(MemberQueryService.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberQueryService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Return a {@link List} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberDTO> findByCriteria(MemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberMapper.toDto(memberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findByCriteria(MemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.findAll(specification, page).map(memberMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Member> createSpecification(MemberCriteria criteria) {
        Specification<Member> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Member_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getName(), Member_.name));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Member_.age));
            }
            if (criteria.getRelation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRelation(), Member_.relation));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getContactNo(), Member_.contactNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModified(), Member_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Member_.lastModifiedBy));
            }
            if (criteria.getPolicyId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPolicyId(), root -> root.join(Member_.policy, JoinType.LEFT).get(Policy_.id))
                    );
            }
        }
        return specification;
    }
}
