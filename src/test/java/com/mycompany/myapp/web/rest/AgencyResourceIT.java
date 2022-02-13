package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Agency;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.AgencyRepository;
import com.mycompany.myapp.service.criteria.AgencyCriteria;
import com.mycompany.myapp.service.dto.AgencyDTO;
import com.mycompany.myapp.service.mapper.AgencyMapper;
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
 * Integration tests for the {@link AgencyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgencyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BRNACH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRNACH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_TYPE_ID = 1L;
    private static final Long UPDATED_COMPANY_TYPE_ID = 2L;
    private static final Long SMALLER_COMPANY_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/agencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private AgencyMapper agencyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgencyMockMvc;

    private Agency agency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agency createEntity(EntityManager em) {
        Agency agency = new Agency()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .branch(DEFAULT_BRANCH)
            .brnachCode(DEFAULT_BRNACH_CODE)
            .email(DEFAULT_EMAIL)
            .companyTypeId(DEFAULT_COMPANY_TYPE_ID)
            .imageUrl(DEFAULT_IMAGE_URL)
            .contactNo(DEFAULT_CONTACT_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return agency;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agency createUpdatedEntity(EntityManager em) {
        Agency agency = new Agency()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .companyTypeId(UPDATED_COMPANY_TYPE_ID)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return agency;
    }

    @BeforeEach
    public void initTest() {
        agency = createEntity(em);
    }

    @Test
    @Transactional
    void createAgency() throws Exception {
        int databaseSizeBeforeCreate = agencyRepository.findAll().size();
        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);
        restAgencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate + 1);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgency.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAgency.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testAgency.getBrnachCode()).isEqualTo(DEFAULT_BRNACH_CODE);
        assertThat(testAgency.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAgency.getCompanyTypeId()).isEqualTo(DEFAULT_COMPANY_TYPE_ID);
        assertThat(testAgency.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testAgency.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testAgency.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAgency.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAgencyWithExistingId() throws Exception {
        // Create the Agency with an existing ID
        agency.setId(1L);
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        int databaseSizeBeforeCreate = agencyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencyRepository.findAll().size();
        // set the field null
        agency.setLastModified(null);

        // Create the Agency, which fails.
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        restAgencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = agencyRepository.findAll().size();
        // set the field null
        agency.setLastModifiedBy(null);

        // Create the Agency, which fails.
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        restAgencyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isBadRequest());

        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAgencies() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].brnachCode").value(hasItem(DEFAULT_BRNACH_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].companyTypeId").value(hasItem(DEFAULT_COMPANY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get the agency
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL_ID, agency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agency.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.brnachCode").value(DEFAULT_BRNACH_CODE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.companyTypeId").value(DEFAULT_COMPANY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAgenciesByIdFiltering() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        Long id = agency.getId();

        defaultAgencyShouldBeFound("id.equals=" + id);
        defaultAgencyShouldNotBeFound("id.notEquals=" + id);

        defaultAgencyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAgencyShouldNotBeFound("id.greaterThan=" + id);

        defaultAgencyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAgencyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name equals to DEFAULT_NAME
        defaultAgencyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the agencyList where name equals to UPDATED_NAME
        defaultAgencyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name not equals to DEFAULT_NAME
        defaultAgencyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the agencyList where name not equals to UPDATED_NAME
        defaultAgencyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAgencyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the agencyList where name equals to UPDATED_NAME
        defaultAgencyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name is not null
        defaultAgencyShouldBeFound("name.specified=true");

        // Get all the agencyList where name is null
        defaultAgencyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name contains DEFAULT_NAME
        defaultAgencyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the agencyList where name contains UPDATED_NAME
        defaultAgencyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where name does not contain DEFAULT_NAME
        defaultAgencyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the agencyList where name does not contain UPDATED_NAME
        defaultAgencyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address equals to DEFAULT_ADDRESS
        defaultAgencyShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the agencyList where address equals to UPDATED_ADDRESS
        defaultAgencyShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address not equals to DEFAULT_ADDRESS
        defaultAgencyShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the agencyList where address not equals to UPDATED_ADDRESS
        defaultAgencyShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultAgencyShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the agencyList where address equals to UPDATED_ADDRESS
        defaultAgencyShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address is not null
        defaultAgencyShouldBeFound("address.specified=true");

        // Get all the agencyList where address is null
        defaultAgencyShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address contains DEFAULT_ADDRESS
        defaultAgencyShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the agencyList where address contains UPDATED_ADDRESS
        defaultAgencyShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where address does not contain DEFAULT_ADDRESS
        defaultAgencyShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the agencyList where address does not contain UPDATED_ADDRESS
        defaultAgencyShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch equals to DEFAULT_BRANCH
        defaultAgencyShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the agencyList where branch equals to UPDATED_BRANCH
        defaultAgencyShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch not equals to DEFAULT_BRANCH
        defaultAgencyShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the agencyList where branch not equals to UPDATED_BRANCH
        defaultAgencyShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultAgencyShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the agencyList where branch equals to UPDATED_BRANCH
        defaultAgencyShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch is not null
        defaultAgencyShouldBeFound("branch.specified=true");

        // Get all the agencyList where branch is null
        defaultAgencyShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch contains DEFAULT_BRANCH
        defaultAgencyShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the agencyList where branch contains UPDATED_BRANCH
        defaultAgencyShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllAgenciesByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where branch does not contain DEFAULT_BRANCH
        defaultAgencyShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the agencyList where branch does not contain UPDATED_BRANCH
        defaultAgencyShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode equals to DEFAULT_BRNACH_CODE
        defaultAgencyShouldBeFound("brnachCode.equals=" + DEFAULT_BRNACH_CODE);

        // Get all the agencyList where brnachCode equals to UPDATED_BRNACH_CODE
        defaultAgencyShouldNotBeFound("brnachCode.equals=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode not equals to DEFAULT_BRNACH_CODE
        defaultAgencyShouldNotBeFound("brnachCode.notEquals=" + DEFAULT_BRNACH_CODE);

        // Get all the agencyList where brnachCode not equals to UPDATED_BRNACH_CODE
        defaultAgencyShouldBeFound("brnachCode.notEquals=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode in DEFAULT_BRNACH_CODE or UPDATED_BRNACH_CODE
        defaultAgencyShouldBeFound("brnachCode.in=" + DEFAULT_BRNACH_CODE + "," + UPDATED_BRNACH_CODE);

        // Get all the agencyList where brnachCode equals to UPDATED_BRNACH_CODE
        defaultAgencyShouldNotBeFound("brnachCode.in=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode is not null
        defaultAgencyShouldBeFound("brnachCode.specified=true");

        // Get all the agencyList where brnachCode is null
        defaultAgencyShouldNotBeFound("brnachCode.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode contains DEFAULT_BRNACH_CODE
        defaultAgencyShouldBeFound("brnachCode.contains=" + DEFAULT_BRNACH_CODE);

        // Get all the agencyList where brnachCode contains UPDATED_BRNACH_CODE
        defaultAgencyShouldNotBeFound("brnachCode.contains=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllAgenciesByBrnachCodeNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where brnachCode does not contain DEFAULT_BRNACH_CODE
        defaultAgencyShouldNotBeFound("brnachCode.doesNotContain=" + DEFAULT_BRNACH_CODE);

        // Get all the agencyList where brnachCode does not contain UPDATED_BRNACH_CODE
        defaultAgencyShouldBeFound("brnachCode.doesNotContain=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email equals to DEFAULT_EMAIL
        defaultAgencyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the agencyList where email equals to UPDATED_EMAIL
        defaultAgencyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email not equals to DEFAULT_EMAIL
        defaultAgencyShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the agencyList where email not equals to UPDATED_EMAIL
        defaultAgencyShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultAgencyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the agencyList where email equals to UPDATED_EMAIL
        defaultAgencyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email is not null
        defaultAgencyShouldBeFound("email.specified=true");

        // Get all the agencyList where email is null
        defaultAgencyShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email contains DEFAULT_EMAIL
        defaultAgencyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the agencyList where email contains UPDATED_EMAIL
        defaultAgencyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllAgenciesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where email does not contain DEFAULT_EMAIL
        defaultAgencyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the agencyList where email does not contain UPDATED_EMAIL
        defaultAgencyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId equals to DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.equals=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId equals to UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.equals=" + UPDATED_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId not equals to DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.notEquals=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId not equals to UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.notEquals=" + UPDATED_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId in DEFAULT_COMPANY_TYPE_ID or UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.in=" + DEFAULT_COMPANY_TYPE_ID + "," + UPDATED_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId equals to UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.in=" + UPDATED_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId is not null
        defaultAgencyShouldBeFound("companyTypeId.specified=true");

        // Get all the agencyList where companyTypeId is null
        defaultAgencyShouldNotBeFound("companyTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId is greater than or equal to DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.greaterThanOrEqual=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId is greater than or equal to UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.greaterThanOrEqual=" + UPDATED_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId is less than or equal to DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.lessThanOrEqual=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId is less than or equal to SMALLER_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.lessThanOrEqual=" + SMALLER_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId is less than DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.lessThan=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId is less than UPDATED_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.lessThan=" + UPDATED_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByCompanyTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where companyTypeId is greater than DEFAULT_COMPANY_TYPE_ID
        defaultAgencyShouldNotBeFound("companyTypeId.greaterThan=" + DEFAULT_COMPANY_TYPE_ID);

        // Get all the agencyList where companyTypeId is greater than SMALLER_COMPANY_TYPE_ID
        defaultAgencyShouldBeFound("companyTypeId.greaterThan=" + SMALLER_COMPANY_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultAgencyShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the agencyList where imageUrl equals to UPDATED_IMAGE_URL
        defaultAgencyShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultAgencyShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the agencyList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultAgencyShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultAgencyShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the agencyList where imageUrl equals to UPDATED_IMAGE_URL
        defaultAgencyShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl is not null
        defaultAgencyShouldBeFound("imageUrl.specified=true");

        // Get all the agencyList where imageUrl is null
        defaultAgencyShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl contains DEFAULT_IMAGE_URL
        defaultAgencyShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the agencyList where imageUrl contains UPDATED_IMAGE_URL
        defaultAgencyShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllAgenciesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultAgencyShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the agencyList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultAgencyShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo equals to DEFAULT_CONTACT_NO
        defaultAgencyShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the agencyList where contactNo equals to UPDATED_CONTACT_NO
        defaultAgencyShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultAgencyShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the agencyList where contactNo not equals to UPDATED_CONTACT_NO
        defaultAgencyShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultAgencyShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the agencyList where contactNo equals to UPDATED_CONTACT_NO
        defaultAgencyShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo is not null
        defaultAgencyShouldBeFound("contactNo.specified=true");

        // Get all the agencyList where contactNo is null
        defaultAgencyShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo contains DEFAULT_CONTACT_NO
        defaultAgencyShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the agencyList where contactNo contains UPDATED_CONTACT_NO
        defaultAgencyShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllAgenciesByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultAgencyShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the agencyList where contactNo does not contain UPDATED_CONTACT_NO
        defaultAgencyShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAgencyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the agencyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAgencyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultAgencyShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the agencyList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultAgencyShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAgencyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the agencyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAgencyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified is not null
        defaultAgencyShouldBeFound("lastModified.specified=true");

        // Get all the agencyList where lastModified is null
        defaultAgencyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultAgencyShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the agencyList where lastModified contains UPDATED_LAST_MODIFIED
        defaultAgencyShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultAgencyShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the agencyList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultAgencyShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAgencyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the agencyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultAgencyShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the agencyList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the agencyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy is not null
        defaultAgencyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the agencyList where lastModifiedBy is null
        defaultAgencyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAgencyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the agencyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAgenciesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        // Get all the agencyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAgencyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the agencyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAgencyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAgenciesByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);
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
        agency.setPolicy(policy);
        policy.setAgency(agency);
        agencyRepository.saveAndFlush(agency);
        Long policyId = policy.getId();

        // Get all the agencyList where policy equals to policyId
        defaultAgencyShouldBeFound("policyId.equals=" + policyId);

        // Get all the agencyList where policy equals to (policyId + 1)
        defaultAgencyShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgencyShouldBeFound(String filter) throws Exception {
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agency.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].brnachCode").value(hasItem(DEFAULT_BRNACH_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].companyTypeId").value(hasItem(DEFAULT_COMPANY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgencyShouldNotBeFound(String filter) throws Exception {
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgencyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAgency() throws Exception {
        // Get the agency
        restAgencyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency
        Agency updatedAgency = agencyRepository.findById(agency.getId()).get();
        // Disconnect from session so that the updates on updatedAgency are not directly saved in db
        em.detach(updatedAgency);
        updatedAgency
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .companyTypeId(UPDATED_COMPANY_TYPE_ID)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AgencyDTO agencyDTO = agencyMapper.toDto(updatedAgency);

        restAgencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgency.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAgency.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testAgency.getBrnachCode()).isEqualTo(UPDATED_BRNACH_CODE);
        assertThat(testAgency.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgency.getCompanyTypeId()).isEqualTo(UPDATED_COMPANY_TYPE_ID);
        assertThat(testAgency.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testAgency.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testAgency.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAgency.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agencyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(agencyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgencyWithPatch() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency using partial update
        Agency partialUpdatedAgency = new Agency();
        partialUpdatedAgency.setId(agency.getId());

        partialUpdatedAgency
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED);

        restAgencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgency))
            )
            .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgency.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAgency.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testAgency.getBrnachCode()).isEqualTo(DEFAULT_BRNACH_CODE);
        assertThat(testAgency.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgency.getCompanyTypeId()).isEqualTo(DEFAULT_COMPANY_TYPE_ID);
        assertThat(testAgency.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testAgency.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testAgency.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAgency.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAgencyWithPatch() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();

        // Update the agency using partial update
        Agency partialUpdatedAgency = new Agency();
        partialUpdatedAgency.setId(agency.getId());

        partialUpdatedAgency
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .companyTypeId(UPDATED_COMPANY_TYPE_ID)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAgencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgency.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgency))
            )
            .andExpect(status().isOk());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
        Agency testAgency = agencyList.get(agencyList.size() - 1);
        assertThat(testAgency.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgency.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAgency.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testAgency.getBrnachCode()).isEqualTo(UPDATED_BRNACH_CODE);
        assertThat(testAgency.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAgency.getCompanyTypeId()).isEqualTo(UPDATED_COMPANY_TYPE_ID);
        assertThat(testAgency.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testAgency.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testAgency.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAgency.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agencyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgency() throws Exception {
        int databaseSizeBeforeUpdate = agencyRepository.findAll().size();
        agency.setId(count.incrementAndGet());

        // Create the Agency
        AgencyDTO agencyDTO = agencyMapper.toDto(agency);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgencyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(agencyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agency in the database
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgency() throws Exception {
        // Initialize the database
        agencyRepository.saveAndFlush(agency);

        int databaseSizeBeforeDelete = agencyRepository.findAll().size();

        // Delete the agency
        restAgencyMockMvc
            .perform(delete(ENTITY_API_URL_ID, agency.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agency> agencyList = agencyRepository.findAll();
        assertThat(agencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
