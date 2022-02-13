package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.MemberRepository;
import com.mycompany.myapp.service.criteria.MemberCriteria;
import com.mycompany.myapp.service.dto.MemberDTO;
import com.mycompany.myapp.service.mapper.MemberMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberResourceIT {

    private static final Long DEFAULT_NAME = 1L;
    private static final Long UPDATED_NAME = 2L;
    private static final Long SMALLER_NAME = 1L - 1L;

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;
    private static final Long SMALLER_AGE = 1L - 1L;

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTACT_NO = 1L;
    private static final Long UPDATED_CONTACT_NO = 2L;
    private static final Long SMALLER_CONTACT_NO = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .relation(DEFAULT_RELATION)
            .contactNo(DEFAULT_CONTACT_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return member;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .relation(UPDATED_RELATION)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMember.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMember.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testMember.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testMember.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createMemberWithExistingId() throws Exception {
        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setLastModified(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setLastModifiedBy(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getMembersByIdFiltering() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        Long id = member.getId();

        defaultMemberShouldBeFound("id.equals=" + id);
        defaultMemberShouldNotBeFound("id.notEquals=" + id);

        defaultMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name equals to DEFAULT_NAME
        defaultMemberShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the memberList where name equals to UPDATED_NAME
        defaultMemberShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name not equals to DEFAULT_NAME
        defaultMemberShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the memberList where name not equals to UPDATED_NAME
        defaultMemberShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMemberShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the memberList where name equals to UPDATED_NAME
        defaultMemberShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name is not null
        defaultMemberShouldBeFound("name.specified=true");

        // Get all the memberList where name is null
        defaultMemberShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name is greater than or equal to DEFAULT_NAME
        defaultMemberShouldBeFound("name.greaterThanOrEqual=" + DEFAULT_NAME);

        // Get all the memberList where name is greater than or equal to UPDATED_NAME
        defaultMemberShouldNotBeFound("name.greaterThanOrEqual=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name is less than or equal to DEFAULT_NAME
        defaultMemberShouldBeFound("name.lessThanOrEqual=" + DEFAULT_NAME);

        // Get all the memberList where name is less than or equal to SMALLER_NAME
        defaultMemberShouldNotBeFound("name.lessThanOrEqual=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name is less than DEFAULT_NAME
        defaultMemberShouldNotBeFound("name.lessThan=" + DEFAULT_NAME);

        // Get all the memberList where name is less than UPDATED_NAME
        defaultMemberShouldBeFound("name.lessThan=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByNameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where name is greater than DEFAULT_NAME
        defaultMemberShouldNotBeFound("name.greaterThan=" + DEFAULT_NAME);

        // Get all the memberList where name is greater than SMALLER_NAME
        defaultMemberShouldBeFound("name.greaterThan=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age equals to DEFAULT_AGE
        defaultMemberShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the memberList where age equals to UPDATED_AGE
        defaultMemberShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age not equals to DEFAULT_AGE
        defaultMemberShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the memberList where age not equals to UPDATED_AGE
        defaultMemberShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age in DEFAULT_AGE or UPDATED_AGE
        defaultMemberShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the memberList where age equals to UPDATED_AGE
        defaultMemberShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age is not null
        defaultMemberShouldBeFound("age.specified=true");

        // Get all the memberList where age is null
        defaultMemberShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age is greater than or equal to DEFAULT_AGE
        defaultMemberShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the memberList where age is greater than or equal to UPDATED_AGE
        defaultMemberShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age is less than or equal to DEFAULT_AGE
        defaultMemberShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the memberList where age is less than or equal to SMALLER_AGE
        defaultMemberShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age is less than DEFAULT_AGE
        defaultMemberShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the memberList where age is less than UPDATED_AGE
        defaultMemberShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where age is greater than DEFAULT_AGE
        defaultMemberShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the memberList where age is greater than SMALLER_AGE
        defaultMemberShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllMembersByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation equals to DEFAULT_RELATION
        defaultMemberShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the memberList where relation equals to UPDATED_RELATION
        defaultMemberShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllMembersByRelationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation not equals to DEFAULT_RELATION
        defaultMemberShouldNotBeFound("relation.notEquals=" + DEFAULT_RELATION);

        // Get all the memberList where relation not equals to UPDATED_RELATION
        defaultMemberShouldBeFound("relation.notEquals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllMembersByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultMemberShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the memberList where relation equals to UPDATED_RELATION
        defaultMemberShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllMembersByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation is not null
        defaultMemberShouldBeFound("relation.specified=true");

        // Get all the memberList where relation is null
        defaultMemberShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByRelationContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation contains DEFAULT_RELATION
        defaultMemberShouldBeFound("relation.contains=" + DEFAULT_RELATION);

        // Get all the memberList where relation contains UPDATED_RELATION
        defaultMemberShouldNotBeFound("relation.contains=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllMembersByRelationNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where relation does not contain DEFAULT_RELATION
        defaultMemberShouldNotBeFound("relation.doesNotContain=" + DEFAULT_RELATION);

        // Get all the memberList where relation does not contain UPDATED_RELATION
        defaultMemberShouldBeFound("relation.doesNotContain=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo equals to DEFAULT_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo equals to UPDATED_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo not equals to UPDATED_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the memberList where contactNo equals to UPDATED_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo is not null
        defaultMemberShouldBeFound("contactNo.specified=true");

        // Get all the memberList where contactNo is null
        defaultMemberShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo is greater than or equal to DEFAULT_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.greaterThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo is greater than or equal to UPDATED_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.greaterThanOrEqual=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo is less than or equal to DEFAULT_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.lessThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo is less than or equal to SMALLER_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.lessThanOrEqual=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo is less than DEFAULT_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.lessThan=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo is less than UPDATED_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.lessThan=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByContactNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactNo is greater than DEFAULT_CONTACT_NO
        defaultMemberShouldNotBeFound("contactNo.greaterThan=" + DEFAULT_CONTACT_NO);

        // Get all the memberList where contactNo is greater than SMALLER_CONTACT_NO
        defaultMemberShouldBeFound("contactNo.greaterThan=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified is not null
        defaultMemberShouldBeFound("lastModified.specified=true");

        // Get all the memberList where lastModified is null
        defaultMemberShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified contains UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy is not null
        defaultMemberShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberList where lastModifiedBy is null
        defaultMemberShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        Policy policy;
        if (TestUtil.findAll(em, Policy.class).isEmpty()) {
            policy = PolicyResourceIT.createEntity(em);
            em.persist(policy);
            em.flush();
        } else {
            policy = TestUtil.findAll(em, Policy.class).get(0);
        }
        em.persist(policy);
        em.flush();
        member.setPolicy(policy);
        memberRepository.saveAndFlush(member);
        Long policyId = policy.getId();

        // Get all the memberList where policy equals to policyId
        defaultMemberShouldBeFound("policyId.equals=" + policyId);

        // Get all the memberList where policy equals to (policyId + 1)
        defaultMemberShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberShouldBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberShouldNotBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .relation(UPDATED_RELATION)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMember.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testMember.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember.name(UPDATED_NAME).relation(UPDATED_RELATION);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMember.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testMember.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testMember.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .relation(UPDATED_RELATION)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMember.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMember.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testMember.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, member.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
