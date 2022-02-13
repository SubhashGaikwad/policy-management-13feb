package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.CompanyType;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.repository.CompanyRepository;
import com.mycompany.myapp.service.criteria.CompanyCriteria;
import com.mycompany.myapp.service.dto.CompanyDTO;
import com.mycompany.myapp.service.mapper.CompanyMapper;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyResourceIT {

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

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .branch(DEFAULT_BRANCH)
            .brnachCode(DEFAULT_BRNACH_CODE)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .contactNo(DEFAULT_CONTACT_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompany.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testCompany.getBrnachCode()).isEqualTo(DEFAULT_BRNACH_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCompany.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testCompany.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCompanyWithExistingId() throws Exception {
        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setLastModified(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setLastModifiedBy(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].brnachCode").value(hasItem(DEFAULT_BRNACH_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.brnachCode").value(DEFAULT_BRNACH_CODE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name not equals to DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyList where name not equals to UPDATED_NAME
        defaultCompanyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address equals to DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address not equals to DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address not equals to UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address is not null
        defaultCompanyShouldBeFound("address.specified=true");

        // Get all the companyList where address is null
        defaultCompanyShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address contains DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the companyList where address contains UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address does not contain DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the companyList where address does not contain UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch equals to DEFAULT_BRANCH
        defaultCompanyShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the companyList where branch equals to UPDATED_BRANCH
        defaultCompanyShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch not equals to DEFAULT_BRANCH
        defaultCompanyShouldNotBeFound("branch.notEquals=" + DEFAULT_BRANCH);

        // Get all the companyList where branch not equals to UPDATED_BRANCH
        defaultCompanyShouldBeFound("branch.notEquals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultCompanyShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the companyList where branch equals to UPDATED_BRANCH
        defaultCompanyShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch is not null
        defaultCompanyShouldBeFound("branch.specified=true");

        // Get all the companyList where branch is null
        defaultCompanyShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch contains DEFAULT_BRANCH
        defaultCompanyShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the companyList where branch contains UPDATED_BRANCH
        defaultCompanyShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllCompaniesByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where branch does not contain DEFAULT_BRANCH
        defaultCompanyShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the companyList where branch does not contain UPDATED_BRANCH
        defaultCompanyShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode equals to DEFAULT_BRNACH_CODE
        defaultCompanyShouldBeFound("brnachCode.equals=" + DEFAULT_BRNACH_CODE);

        // Get all the companyList where brnachCode equals to UPDATED_BRNACH_CODE
        defaultCompanyShouldNotBeFound("brnachCode.equals=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode not equals to DEFAULT_BRNACH_CODE
        defaultCompanyShouldNotBeFound("brnachCode.notEquals=" + DEFAULT_BRNACH_CODE);

        // Get all the companyList where brnachCode not equals to UPDATED_BRNACH_CODE
        defaultCompanyShouldBeFound("brnachCode.notEquals=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode in DEFAULT_BRNACH_CODE or UPDATED_BRNACH_CODE
        defaultCompanyShouldBeFound("brnachCode.in=" + DEFAULT_BRNACH_CODE + "," + UPDATED_BRNACH_CODE);

        // Get all the companyList where brnachCode equals to UPDATED_BRNACH_CODE
        defaultCompanyShouldNotBeFound("brnachCode.in=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode is not null
        defaultCompanyShouldBeFound("brnachCode.specified=true");

        // Get all the companyList where brnachCode is null
        defaultCompanyShouldNotBeFound("brnachCode.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode contains DEFAULT_BRNACH_CODE
        defaultCompanyShouldBeFound("brnachCode.contains=" + DEFAULT_BRNACH_CODE);

        // Get all the companyList where brnachCode contains UPDATED_BRNACH_CODE
        defaultCompanyShouldNotBeFound("brnachCode.contains=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByBrnachCodeNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where brnachCode does not contain DEFAULT_BRNACH_CODE
        defaultCompanyShouldNotBeFound("brnachCode.doesNotContain=" + DEFAULT_BRNACH_CODE);

        // Get all the companyList where brnachCode does not contain UPDATED_BRNACH_CODE
        defaultCompanyShouldBeFound("brnachCode.doesNotContain=" + UPDATED_BRNACH_CODE);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email not equals to DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyList where email not equals to UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email contains DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyList where email contains UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email does not contain DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyList where email does not contain UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultCompanyShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the companyList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCompanyShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultCompanyShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the companyList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultCompanyShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultCompanyShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the companyList where imageUrl equals to UPDATED_IMAGE_URL
        defaultCompanyShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl is not null
        defaultCompanyShouldBeFound("imageUrl.specified=true");

        // Get all the companyList where imageUrl is null
        defaultCompanyShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl contains DEFAULT_IMAGE_URL
        defaultCompanyShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the companyList where imageUrl contains UPDATED_IMAGE_URL
        defaultCompanyShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultCompanyShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the companyList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultCompanyShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo equals to DEFAULT_CONTACT_NO
        defaultCompanyShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the companyList where contactNo equals to UPDATED_CONTACT_NO
        defaultCompanyShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultCompanyShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the companyList where contactNo not equals to UPDATED_CONTACT_NO
        defaultCompanyShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultCompanyShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the companyList where contactNo equals to UPDATED_CONTACT_NO
        defaultCompanyShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo is not null
        defaultCompanyShouldBeFound("contactNo.specified=true");

        // Get all the companyList where contactNo is null
        defaultCompanyShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo contains DEFAULT_CONTACT_NO
        defaultCompanyShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the companyList where contactNo contains UPDATED_CONTACT_NO
        defaultCompanyShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCompaniesByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultCompanyShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the companyList where contactNo does not contain UPDATED_CONTACT_NO
        defaultCompanyShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the companyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified is not null
        defaultCompanyShouldBeFound("lastModified.specified=true");

        // Get all the companyList where lastModified is null
        defaultCompanyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyList where lastModified contains UPDATED_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultCompanyShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultCompanyShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy is not null
        defaultCompanyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the companyList where lastModifiedBy is null
        defaultCompanyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCompanyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCompanyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompaniesByCompanyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        CompanyType companyType;
        if (TestUtil.findAll(em, CompanyType.class).isEmpty()) {
            companyType = CompanyTypeResourceIT.createEntity(em);
            em.persist(companyType);
            em.flush();
        } else {
            companyType = TestUtil.findAll(em, CompanyType.class).get(0);
        }
        em.persist(companyType);
        em.flush();
        company.setCompanyType(companyType);
        companyRepository.saveAndFlush(company);
        Long companyTypeId = companyType.getId();

        // Get all the companyList where companyType equals to companyTypeId
        defaultCompanyShouldBeFound("companyTypeId.equals=" + companyTypeId);

        // Get all the companyList where companyType equals to (companyTypeId + 1)
        defaultCompanyShouldNotBeFound("companyTypeId.equals=" + (companyTypeId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        company.addProduct(product);
        companyRepository.saveAndFlush(company);
        Long productId = product.getId();

        // Get all the companyList where product equals to productId
        defaultCompanyShouldBeFound("productId.equals=" + productId);

        // Get all the companyList where product equals to (productId + 1)
        defaultCompanyShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        company.addAddress(address);
        companyRepository.saveAndFlush(company);
        Long addressId = address.getId();

        // Get all the companyList where address equals to addressId
        defaultCompanyShouldBeFound("addressId.equals=" + addressId);

        // Get all the companyList where address equals to (addressId + 1)
        defaultCompanyShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllCompaniesByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
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
        company.setPolicy(policy);
        policy.setCompany(company);
        companyRepository.saveAndFlush(company);
        Long policyId = policy.getId();

        // Get all the companyList where policy equals to policyId
        defaultCompanyShouldBeFound("policyId.equals=" + policyId);

        // Get all the companyList where policy equals to (policyId + 1)
        defaultCompanyShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].brnachCode").value(hasItem(DEFAULT_BRNACH_CODE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCompany.getBrnachCode()).isEqualTo(UPDATED_BRNACH_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCompany.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCompany.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany.address(UPDATED_ADDRESS).branch(UPDATED_BRANCH).email(UPDATED_EMAIL).contactNo(UPDATED_CONTACT_NO);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCompany.getBrnachCode()).isEqualTo(DEFAULT_BRNACH_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testCompany.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCompany.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCompanyWithPatch() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company using partial update
        Company partialUpdatedCompany = new Company();
        partialUpdatedCompany.setId(company.getId());

        partialUpdatedCompany
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .branch(UPDATED_BRANCH)
            .brnachCode(UPDATED_BRNACH_CODE)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .contactNo(UPDATED_CONTACT_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testCompany.getBrnachCode()).isEqualTo(UPDATED_BRNACH_CODE);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testCompany.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testCompany.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompany.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();
        company.setId(count.incrementAndGet());

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
