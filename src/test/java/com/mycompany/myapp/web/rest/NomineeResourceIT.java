package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Nominee;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.NomineeRepository;
import com.mycompany.myapp.service.criteria.NomineeCriteria;
import com.mycompany.myapp.service.dto.NomineeDTO;
import com.mycompany.myapp.service.mapper.NomineeMapper;
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
 * Integration tests for the {@link NomineeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NomineeResourceIT {

    private static final Long DEFAULT_NAME = 1L;
    private static final Long UPDATED_NAME = 2L;
    private static final Long SMALLER_NAME = 1L - 1L;

    private static final String DEFAULT_RELATION = "AAAAAAAAAA";
    private static final String UPDATED_RELATION = "BBBBBBBBBB";

    private static final Long DEFAULT_NOMINEE_PERCENTAGE = 1L;
    private static final Long UPDATED_NOMINEE_PERCENTAGE = 2L;
    private static final Long SMALLER_NOMINEE_PERCENTAGE = 1L - 1L;

    private static final Long DEFAULT_CONTACT_NO = 1L;
    private static final Long UPDATED_CONTACT_NO = 2L;
    private static final Long SMALLER_CONTACT_NO = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nominees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NomineeRepository nomineeRepository;

    @Autowired
    private NomineeMapper nomineeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNomineeMockMvc;

    private Nominee nominee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nominee createEntity(EntityManager em) {
        Nominee nominee = new Nominee()
            .name(DEFAULT_NAME)
            .relation(DEFAULT_RELATION)
            .nomineePercentage(DEFAULT_NOMINEE_PERCENTAGE)
            .contactNo(DEFAULT_CONTACT_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return nominee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nominee createUpdatedEntity(EntityManager em) {
        Nominee nominee = new Nominee()
            .name(UPDATED_NAME)
            .relation(UPDATED_RELATION)
            .nomineePercentage(UPDATED_NOMINEE_PERCENTAGE)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return nominee;
    }

    @BeforeEach
    public void initTest() {
        nominee = createEntity(em);
    }

    @Test
    @Transactional
    void createNominee() throws Exception {
        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();
        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);
        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isCreated());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate + 1);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNominee.getRelation()).isEqualTo(DEFAULT_RELATION);
        assertThat(testNominee.getNomineePercentage()).isEqualTo(DEFAULT_NOMINEE_PERCENTAGE);
        assertThat(testNominee.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testNominee.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createNomineeWithExistingId() throws Exception {
        // Create the Nominee with an existing ID
        nominee.setId(1L);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        int databaseSizeBeforeCreate = nomineeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setLastModified(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = nomineeRepository.findAll().size();
        // set the field null
        nominee.setLastModifiedBy(null);

        // Create the Nominee, which fails.
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        restNomineeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isBadRequest());

        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllNominees() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].nomineePercentage").value(hasItem(DEFAULT_NOMINEE_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get the nominee
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL_ID, nominee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nominee.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.intValue()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION))
            .andExpect(jsonPath("$.nomineePercentage").value(DEFAULT_NOMINEE_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNomineesByIdFiltering() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        Long id = nominee.getId();

        defaultNomineeShouldBeFound("id.equals=" + id);
        defaultNomineeShouldNotBeFound("id.notEquals=" + id);

        defaultNomineeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNomineeShouldNotBeFound("id.greaterThan=" + id);

        defaultNomineeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNomineeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name equals to DEFAULT_NAME
        defaultNomineeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the nomineeList where name equals to UPDATED_NAME
        defaultNomineeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name not equals to DEFAULT_NAME
        defaultNomineeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the nomineeList where name not equals to UPDATED_NAME
        defaultNomineeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultNomineeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the nomineeList where name equals to UPDATED_NAME
        defaultNomineeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name is not null
        defaultNomineeShouldBeFound("name.specified=true");

        // Get all the nomineeList where name is null
        defaultNomineeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name is greater than or equal to DEFAULT_NAME
        defaultNomineeShouldBeFound("name.greaterThanOrEqual=" + DEFAULT_NAME);

        // Get all the nomineeList where name is greater than or equal to UPDATED_NAME
        defaultNomineeShouldNotBeFound("name.greaterThanOrEqual=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name is less than or equal to DEFAULT_NAME
        defaultNomineeShouldBeFound("name.lessThanOrEqual=" + DEFAULT_NAME);

        // Get all the nomineeList where name is less than or equal to SMALLER_NAME
        defaultNomineeShouldNotBeFound("name.lessThanOrEqual=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsLessThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name is less than DEFAULT_NAME
        defaultNomineeShouldNotBeFound("name.lessThan=" + DEFAULT_NAME);

        // Get all the nomineeList where name is less than UPDATED_NAME
        defaultNomineeShouldBeFound("name.lessThan=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByNameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where name is greater than DEFAULT_NAME
        defaultNomineeShouldNotBeFound("name.greaterThan=" + DEFAULT_NAME);

        // Get all the nomineeList where name is greater than SMALLER_NAME
        defaultNomineeShouldBeFound("name.greaterThan=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation equals to DEFAULT_RELATION
        defaultNomineeShouldBeFound("relation.equals=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation equals to UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.equals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation not equals to DEFAULT_RELATION
        defaultNomineeShouldNotBeFound("relation.notEquals=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation not equals to UPDATED_RELATION
        defaultNomineeShouldBeFound("relation.notEquals=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation in DEFAULT_RELATION or UPDATED_RELATION
        defaultNomineeShouldBeFound("relation.in=" + DEFAULT_RELATION + "," + UPDATED_RELATION);

        // Get all the nomineeList where relation equals to UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.in=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation is not null
        defaultNomineeShouldBeFound("relation.specified=true");

        // Get all the nomineeList where relation is null
        defaultNomineeShouldNotBeFound("relation.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByRelationContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation contains DEFAULT_RELATION
        defaultNomineeShouldBeFound("relation.contains=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation contains UPDATED_RELATION
        defaultNomineeShouldNotBeFound("relation.contains=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByRelationNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where relation does not contain DEFAULT_RELATION
        defaultNomineeShouldNotBeFound("relation.doesNotContain=" + DEFAULT_RELATION);

        // Get all the nomineeList where relation does not contain UPDATED_RELATION
        defaultNomineeShouldBeFound("relation.doesNotContain=" + UPDATED_RELATION);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage equals to DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.equals=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage equals to UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.equals=" + UPDATED_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage not equals to DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.notEquals=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage not equals to UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.notEquals=" + UPDATED_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage in DEFAULT_NOMINEE_PERCENTAGE or UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.in=" + DEFAULT_NOMINEE_PERCENTAGE + "," + UPDATED_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage equals to UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.in=" + UPDATED_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage is not null
        defaultNomineeShouldBeFound("nomineePercentage.specified=true");

        // Get all the nomineeList where nomineePercentage is null
        defaultNomineeShouldNotBeFound("nomineePercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage is greater than or equal to DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.greaterThanOrEqual=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage is greater than or equal to UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.greaterThanOrEqual=" + UPDATED_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage is less than or equal to DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.lessThanOrEqual=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage is less than or equal to SMALLER_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.lessThanOrEqual=" + SMALLER_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage is less than DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.lessThan=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage is less than UPDATED_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.lessThan=" + UPDATED_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByNomineePercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where nomineePercentage is greater than DEFAULT_NOMINEE_PERCENTAGE
        defaultNomineeShouldNotBeFound("nomineePercentage.greaterThan=" + DEFAULT_NOMINEE_PERCENTAGE);

        // Get all the nomineeList where nomineePercentage is greater than SMALLER_NOMINEE_PERCENTAGE
        defaultNomineeShouldBeFound("nomineePercentage.greaterThan=" + SMALLER_NOMINEE_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo equals to DEFAULT_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo equals to UPDATED_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo not equals to UPDATED_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the nomineeList where contactNo equals to UPDATED_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo is not null
        defaultNomineeShouldBeFound("contactNo.specified=true");

        // Get all the nomineeList where contactNo is null
        defaultNomineeShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo is greater than or equal to DEFAULT_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.greaterThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo is greater than or equal to UPDATED_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.greaterThanOrEqual=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo is less than or equal to DEFAULT_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.lessThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo is less than or equal to SMALLER_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.lessThanOrEqual=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsLessThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo is less than DEFAULT_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.lessThan=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo is less than UPDATED_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.lessThan=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByContactNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where contactNo is greater than DEFAULT_CONTACT_NO
        defaultNomineeShouldNotBeFound("contactNo.greaterThan=" + DEFAULT_CONTACT_NO);

        // Get all the nomineeList where contactNo is greater than SMALLER_CONTACT_NO
        defaultNomineeShouldBeFound("contactNo.greaterThan=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the nomineeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the nomineeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the nomineeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified is not null
        defaultNomineeShouldBeFound("lastModified.specified=true");

        // Get all the nomineeList where lastModified is null
        defaultNomineeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the nomineeList where lastModified contains UPDATED_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultNomineeShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the nomineeList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultNomineeShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy is not null
        defaultNomineeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the nomineeList where lastModifiedBy is null
        defaultNomineeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        // Get all the nomineeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultNomineeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the nomineeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultNomineeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNomineesByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);
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
        nominee.setPolicy(policy);
        nomineeRepository.saveAndFlush(nominee);
        Long policyId = policy.getId();

        // Get all the nomineeList where policy equals to policyId
        defaultNomineeShouldBeFound("policyId.equals=" + policyId);

        // Get all the nomineeList where policy equals to (policyId + 1)
        defaultNomineeShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNomineeShouldBeFound(String filter) throws Exception {
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nominee.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION)))
            .andExpect(jsonPath("$.[*].nomineePercentage").value(hasItem(DEFAULT_NOMINEE_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNomineeShouldNotBeFound(String filter) throws Exception {
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNomineeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNominee() throws Exception {
        // Get the nominee
        restNomineeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee
        Nominee updatedNominee = nomineeRepository.findById(nominee.getId()).get();
        // Disconnect from session so that the updates on updatedNominee are not directly saved in db
        em.detach(updatedNominee);
        updatedNominee
            .name(UPDATED_NAME)
            .relation(UPDATED_RELATION)
            .nomineePercentage(UPDATED_NOMINEE_PERCENTAGE)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        NomineeDTO nomineeDTO = nomineeMapper.toDto(updatedNominee);

        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getNomineePercentage()).isEqualTo(UPDATED_NOMINEE_PERCENTAGE);
        assertThat(testNominee.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testNominee.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nomineeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNomineeWithPatch() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee using partial update
        Nominee partialUpdatedNominee = new Nominee();
        partialUpdatedNominee.setId(nominee.getId());

        partialUpdatedNominee.name(UPDATED_NAME).relation(UPDATED_RELATION).contactNo(UPDATED_CONTACT_NO);

        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNominee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNominee))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getNomineePercentage()).isEqualTo(DEFAULT_NOMINEE_PERCENTAGE);
        assertThat(testNominee.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testNominee.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateNomineeWithPatch() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();

        // Update the nominee using partial update
        Nominee partialUpdatedNominee = new Nominee();
        partialUpdatedNominee.setId(nominee.getId());

        partialUpdatedNominee
            .name(UPDATED_NAME)
            .relation(UPDATED_RELATION)
            .nomineePercentage(UPDATED_NOMINEE_PERCENTAGE)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNominee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNominee))
            )
            .andExpect(status().isOk());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
        Nominee testNominee = nomineeList.get(nomineeList.size() - 1);
        assertThat(testNominee.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNominee.getRelation()).isEqualTo(UPDATED_RELATION);
        assertThat(testNominee.getNomineePercentage()).isEqualTo(UPDATED_NOMINEE_PERCENTAGE);
        assertThat(testNominee.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testNominee.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNominee.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nomineeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNominee() throws Exception {
        int databaseSizeBeforeUpdate = nomineeRepository.findAll().size();
        nominee.setId(count.incrementAndGet());

        // Create the Nominee
        NomineeDTO nomineeDTO = nomineeMapper.toDto(nominee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNomineeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nomineeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nominee in the database
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNominee() throws Exception {
        // Initialize the database
        nomineeRepository.saveAndFlush(nominee);

        int databaseSizeBeforeDelete = nomineeRepository.findAll().size();

        // Delete the nominee
        restNomineeMockMvc
            .perform(delete(ENTITY_API_URL_ID, nominee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nominee> nomineeList = nomineeRepository.findAll();
        assertThat(nomineeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
