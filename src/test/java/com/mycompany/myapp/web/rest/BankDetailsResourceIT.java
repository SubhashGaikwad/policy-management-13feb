package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BankDetails;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.repository.BankDetailsRepository;
import com.mycompany.myapp.service.criteria.BankDetailsCriteria;
import com.mycompany.myapp.service.dto.BankDetailsDTO;
import com.mycompany.myapp.service.mapper.BankDetailsMapper;
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
 * Integration tests for the {@link BankDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_CITY = 1L;
    private static final Long UPDATED_CITY = 2L;
    private static final Long SMALLER_CITY = 1L - 1L;

    private static final Long DEFAULT_CONTACT_NO = 1L;
    private static final Long UPDATED_CONTACT_NO = 2L;
    private static final Long SMALLER_CONTACT_NO = 1L - 1L;

    private static final String DEFAULT_IFC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bank-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    private BankDetailsMapper bankDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankDetailsMockMvc;

    private BankDetails bankDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetails createEntity(EntityManager em) {
        BankDetails bankDetails = new BankDetails()
            .name(DEFAULT_NAME)
            .branch(DEFAULT_BRANCH)
            .branchCode(DEFAULT_BRANCH_CODE)
            .city(DEFAULT_CITY)
            .contactNo(DEFAULT_CONTACT_NO)
            .ifcCode(DEFAULT_IFC_CODE)
            .account(DEFAULT_ACCOUNT)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return bankDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetails createUpdatedEntity(EntityManager em) {
        BankDetails bankDetails = new BankDetails()
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .branchCode(UPDATED_BRANCH_CODE)
            .city(UPDATED_CITY)
            .contactNo(UPDATED_CONTACT_NO)
            .ifcCode(UPDATED_IFC_CODE)
            .account(UPDATED_ACCOUNT)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return bankDetails;
    }

    @BeforeEach
    public void initTest() {
        bankDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBankDetails() throws Exception {
        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();
        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);
        restBankDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankDetails.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBankDetails.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testBankDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBankDetails.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testBankDetails.getIfcCode()).isEqualTo(DEFAULT_IFC_CODE);
        assertThat(testBankDetails.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testBankDetails.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBankDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createBankDetailsWithExistingId() throws Exception {
        // Create the BankDetails with an existing ID
        bankDetails.setId(1L);
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        int databaseSizeBeforeCreate = bankDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankDetailsRepository.findAll().size();
        // set the field null
        bankDetails.setLastModified(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankDetailsRepository.findAll().size();
        // set the field null
        bankDetails.setLastModifiedBy(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.intValue())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].ifcCode").value(hasItem(DEFAULT_IFC_CODE)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get the bankDetails
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, bankDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.intValue()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.intValue()))
            .andExpect(jsonPath("$.ifcCode").value(DEFAULT_IFC_CODE))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getBankDetailsByIdFiltering() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        Long id = bankDetails.getId();

        defaultBankDetailsShouldBeFound("id.equals=" + id);
        defaultBankDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultBankDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultBankDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name equals to DEFAULT_NAME
        defaultBankDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bankDetailsList where name equals to UPDATED_NAME
        defaultBankDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name not equals to DEFAULT_NAME
        defaultBankDetailsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the bankDetailsList where name not equals to UPDATED_NAME
        defaultBankDetailsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBankDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bankDetailsList where name equals to UPDATED_NAME
        defaultBankDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name is not null
        defaultBankDetailsShouldBeFound("name.specified=true");

        // Get all the bankDetailsList where name is null
        defaultBankDetailsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name contains DEFAULT_NAME
        defaultBankDetailsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bankDetailsList where name contains UPDATED_NAME
        defaultBankDetailsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where name does not contain DEFAULT_NAME
        defaultBankDetailsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bankDetailsList where name does not contain UPDATED_NAME
        defaultBankDetailsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch equals to DEFAULT_BRANCH
        defaultBankDetailsShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the bankDetailsList where branch equals to UPDATED_BRANCH
        defaultBankDetailsShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch not equals to DEFAULT_BRANCH
        defaultBankDetailsShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the bankDetailsList where branch not equals to UPDATED_BRANCH
        defaultBankDetailsShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultBankDetailsShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the bankDetailsList where branch equals to UPDATED_BRANCH
        defaultBankDetailsShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch is not null
        defaultBankDetailsShouldBeFound("branch.specified=true");

        // Get all the bankDetailsList where branch is null
        defaultBankDetailsShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch contains DEFAULT_BRANCH
        defaultBankDetailsShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the bankDetailsList where branch contains UPDATED_BRANCH
        defaultBankDetailsShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branch does not contain DEFAULT_BRANCH
        defaultBankDetailsShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the bankDetailsList where branch does not contain UPDATED_BRANCH
        defaultBankDetailsShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode equals to DEFAULT_BRANCH_CODE
        defaultBankDetailsShouldBeFound("branchCode.equals=" + DEFAULT_BRANCH_CODE);

        // Get all the bankDetailsList where branchCode equals to UPDATED_BRANCH_CODE
        defaultBankDetailsShouldNotBeFound("branchCode.equals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode not equals to DEFAULT_BRANCH_CODE
        defaultBankDetailsShouldNotBeFound("branchCode.notEquals=" + DEFAULT_BRANCH_CODE);

        // Get all the bankDetailsList where branchCode not equals to UPDATED_BRANCH_CODE
        defaultBankDetailsShouldBeFound("branchCode.notEquals=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode in DEFAULT_BRANCH_CODE or UPDATED_BRANCH_CODE
        defaultBankDetailsShouldBeFound("branchCode.in=" + DEFAULT_BRANCH_CODE + "," + UPDATED_BRANCH_CODE);

        // Get all the bankDetailsList where branchCode equals to UPDATED_BRANCH_CODE
        defaultBankDetailsShouldNotBeFound("branchCode.in=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode is not null
        defaultBankDetailsShouldBeFound("branchCode.specified=true");

        // Get all the bankDetailsList where branchCode is null
        defaultBankDetailsShouldNotBeFound("branchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode contains DEFAULT_BRANCH_CODE
        defaultBankDetailsShouldBeFound("branchCode.contains=" + DEFAULT_BRANCH_CODE);

        // Get all the bankDetailsList where branchCode contains UPDATED_BRANCH_CODE
        defaultBankDetailsShouldNotBeFound("branchCode.contains=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where branchCode does not contain DEFAULT_BRANCH_CODE
        defaultBankDetailsShouldNotBeFound("branchCode.doesNotContain=" + DEFAULT_BRANCH_CODE);

        // Get all the bankDetailsList where branchCode does not contain UPDATED_BRANCH_CODE
        defaultBankDetailsShouldBeFound("branchCode.doesNotContain=" + UPDATED_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city equals to DEFAULT_CITY
        defaultBankDetailsShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city equals to UPDATED_CITY
        defaultBankDetailsShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city not equals to DEFAULT_CITY
        defaultBankDetailsShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city not equals to UPDATED_CITY
        defaultBankDetailsShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city in DEFAULT_CITY or UPDATED_CITY
        defaultBankDetailsShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the bankDetailsList where city equals to UPDATED_CITY
        defaultBankDetailsShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city is not null
        defaultBankDetailsShouldBeFound("city.specified=true");

        // Get all the bankDetailsList where city is null
        defaultBankDetailsShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city is greater than or equal to DEFAULT_CITY
        defaultBankDetailsShouldBeFound("city.greaterThanOrEqual=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city is greater than or equal to UPDATED_CITY
        defaultBankDetailsShouldNotBeFound("city.greaterThanOrEqual=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city is less than or equal to DEFAULT_CITY
        defaultBankDetailsShouldBeFound("city.lessThanOrEqual=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city is less than or equal to SMALLER_CITY
        defaultBankDetailsShouldNotBeFound("city.lessThanOrEqual=" + SMALLER_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsLessThanSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city is less than DEFAULT_CITY
        defaultBankDetailsShouldNotBeFound("city.lessThan=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city is less than UPDATED_CITY
        defaultBankDetailsShouldBeFound("city.lessThan=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByCityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where city is greater than DEFAULT_CITY
        defaultBankDetailsShouldNotBeFound("city.greaterThan=" + DEFAULT_CITY);

        // Get all the bankDetailsList where city is greater than SMALLER_CITY
        defaultBankDetailsShouldBeFound("city.greaterThan=" + SMALLER_CITY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo equals to DEFAULT_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo equals to UPDATED_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo not equals to UPDATED_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the bankDetailsList where contactNo equals to UPDATED_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo is not null
        defaultBankDetailsShouldBeFound("contactNo.specified=true");

        // Get all the bankDetailsList where contactNo is null
        defaultBankDetailsShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo is greater than or equal to DEFAULT_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.greaterThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo is greater than or equal to UPDATED_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.greaterThanOrEqual=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo is less than or equal to DEFAULT_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.lessThanOrEqual=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo is less than or equal to SMALLER_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.lessThanOrEqual=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsLessThanSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo is less than DEFAULT_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.lessThan=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo is less than UPDATED_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.lessThan=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByContactNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where contactNo is greater than DEFAULT_CONTACT_NO
        defaultBankDetailsShouldNotBeFound("contactNo.greaterThan=" + DEFAULT_CONTACT_NO);

        // Get all the bankDetailsList where contactNo is greater than SMALLER_CONTACT_NO
        defaultBankDetailsShouldBeFound("contactNo.greaterThan=" + SMALLER_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode equals to DEFAULT_IFC_CODE
        defaultBankDetailsShouldBeFound("ifcCode.equals=" + DEFAULT_IFC_CODE);

        // Get all the bankDetailsList where ifcCode equals to UPDATED_IFC_CODE
        defaultBankDetailsShouldNotBeFound("ifcCode.equals=" + UPDATED_IFC_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode not equals to DEFAULT_IFC_CODE
        defaultBankDetailsShouldNotBeFound("ifcCode.notEquals=" + DEFAULT_IFC_CODE);

        // Get all the bankDetailsList where ifcCode not equals to UPDATED_IFC_CODE
        defaultBankDetailsShouldBeFound("ifcCode.notEquals=" + UPDATED_IFC_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode in DEFAULT_IFC_CODE or UPDATED_IFC_CODE
        defaultBankDetailsShouldBeFound("ifcCode.in=" + DEFAULT_IFC_CODE + "," + UPDATED_IFC_CODE);

        // Get all the bankDetailsList where ifcCode equals to UPDATED_IFC_CODE
        defaultBankDetailsShouldNotBeFound("ifcCode.in=" + UPDATED_IFC_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode is not null
        defaultBankDetailsShouldBeFound("ifcCode.specified=true");

        // Get all the bankDetailsList where ifcCode is null
        defaultBankDetailsShouldNotBeFound("ifcCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode contains DEFAULT_IFC_CODE
        defaultBankDetailsShouldBeFound("ifcCode.contains=" + DEFAULT_IFC_CODE);

        // Get all the bankDetailsList where ifcCode contains UPDATED_IFC_CODE
        defaultBankDetailsShouldNotBeFound("ifcCode.contains=" + UPDATED_IFC_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByIfcCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where ifcCode does not contain DEFAULT_IFC_CODE
        defaultBankDetailsShouldNotBeFound("ifcCode.doesNotContain=" + DEFAULT_IFC_CODE);

        // Get all the bankDetailsList where ifcCode does not contain UPDATED_IFC_CODE
        defaultBankDetailsShouldBeFound("ifcCode.doesNotContain=" + UPDATED_IFC_CODE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account equals to DEFAULT_ACCOUNT
        defaultBankDetailsShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the bankDetailsList where account equals to UPDATED_ACCOUNT
        defaultBankDetailsShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account not equals to DEFAULT_ACCOUNT
        defaultBankDetailsShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the bankDetailsList where account not equals to UPDATED_ACCOUNT
        defaultBankDetailsShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultBankDetailsShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the bankDetailsList where account equals to UPDATED_ACCOUNT
        defaultBankDetailsShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account is not null
        defaultBankDetailsShouldBeFound("account.specified=true");

        // Get all the bankDetailsList where account is null
        defaultBankDetailsShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account contains DEFAULT_ACCOUNT
        defaultBankDetailsShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the bankDetailsList where account contains UPDATED_ACCOUNT
        defaultBankDetailsShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where account does not contain DEFAULT_ACCOUNT
        defaultBankDetailsShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the bankDetailsList where account does not contain UPDATED_ACCOUNT
        defaultBankDetailsShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultBankDetailsShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the bankDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType not equals to DEFAULT_ACCOUNT_TYPE
        defaultBankDetailsShouldNotBeFound("accountType.notEquals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the bankDetailsList where accountType not equals to UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldBeFound("accountType.notEquals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the bankDetailsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType is not null
        defaultBankDetailsShouldBeFound("accountType.specified=true");

        // Get all the bankDetailsList where accountType is null
        defaultBankDetailsShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultBankDetailsShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the bankDetailsList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultBankDetailsShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the bankDetailsList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultBankDetailsShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBankDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultBankDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bankDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified is not null
        defaultBankDetailsShouldBeFound("lastModified.specified=true");

        // Get all the bankDetailsList where lastModified is null
        defaultBankDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultBankDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultBankDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultBankDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBankDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bankDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy is not null
        defaultBankDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bankDetailsList where lastModifiedBy is null
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBankDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBankDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBankDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBankDetailsByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);
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
        bankDetails.setPolicy(policy);
        policy.setBankDetails(bankDetails);
        bankDetailsRepository.saveAndFlush(bankDetails);
        Long policyId = policy.getId();

        // Get all the bankDetailsList where policy equals to policyId
        defaultBankDetailsShouldBeFound("policyId.equals=" + policyId);

        // Get all the bankDetailsList where policy equals to (policyId + 1)
        defaultBankDetailsShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankDetailsShouldBeFound(String filter) throws Exception {
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.intValue())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.intValue())))
            .andExpect(jsonPath("$.[*].ifcCode").value(hasItem(DEFAULT_IFC_CODE)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankDetailsShouldNotBeFound(String filter) throws Exception {
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBankDetails() throws Exception {
        // Get the bankDetails
        restBankDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Update the bankDetails
        BankDetails updatedBankDetails = bankDetailsRepository.findById(bankDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBankDetails are not directly saved in db
        em.detach(updatedBankDetails);
        updatedBankDetails
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .branchCode(UPDATED_BRANCH_CODE)
            .city(UPDATED_CITY)
            .contactNo(UPDATED_CONTACT_NO)
            .ifcCode(UPDATED_IFC_CODE)
            .account(UPDATED_ACCOUNT)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(updatedBankDetails);

        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankDetails.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBankDetails.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testBankDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBankDetails.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testBankDetails.getIfcCode()).isEqualTo(UPDATED_IFC_CODE);
        assertThat(testBankDetails.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testBankDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBankDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankDetailsWithPatch() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Update the bankDetails using partial update
        BankDetails partialUpdatedBankDetails = new BankDetails();
        partialUpdatedBankDetails.setId(bankDetails.getId());

        partialUpdatedBankDetails.city(UPDATED_CITY).account(UPDATED_ACCOUNT).lastModified(UPDATED_LAST_MODIFIED);

        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBankDetails.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBankDetails.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testBankDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBankDetails.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testBankDetails.getIfcCode()).isEqualTo(DEFAULT_IFC_CODE);
        assertThat(testBankDetails.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testBankDetails.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testBankDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBankDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBankDetailsWithPatch() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();

        // Update the bankDetails using partial update
        BankDetails partialUpdatedBankDetails = new BankDetails();
        partialUpdatedBankDetails.setId(bankDetails.getId());

        partialUpdatedBankDetails
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .branchCode(UPDATED_BRANCH_CODE)
            .city(UPDATED_CITY)
            .contactNo(UPDATED_CONTACT_NO)
            .ifcCode(UPDATED_IFC_CODE)
            .account(UPDATED_ACCOUNT)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
        BankDetails testBankDetails = bankDetailsList.get(bankDetailsList.size() - 1);
        assertThat(testBankDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBankDetails.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBankDetails.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testBankDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBankDetails.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testBankDetails.getIfcCode()).isEqualTo(UPDATED_IFC_CODE);
        assertThat(testBankDetails.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testBankDetails.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testBankDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBankDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankDetails() throws Exception {
        int databaseSizeBeforeUpdate = bankDetailsRepository.findAll().size();
        bankDetails.setId(count.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetails in the database
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankDetails() throws Exception {
        // Initialize the database
        bankDetailsRepository.saveAndFlush(bankDetails);

        int databaseSizeBeforeDelete = bankDetailsRepository.findAll().size();

        // Delete the bankDetails
        restBankDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BankDetails> bankDetailsList = bankDetailsRepository.findAll();
        assertThat(bankDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
