package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.domain.PremiunDetails;
import com.mycompany.myapp.repository.PremiunDetailsRepository;
import com.mycompany.myapp.service.criteria.PremiunDetailsCriteria;
import com.mycompany.myapp.service.dto.PremiunDetailsDTO;
import com.mycompany.myapp.service.mapper.PremiunDetailsMapper;
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
 * Integration tests for the {@link PremiunDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PremiunDetailsResourceIT {

    private static final Long DEFAULT_PREMIUM = 1L;
    private static final Long UPDATED_PREMIUM = 2L;
    private static final Long SMALLER_PREMIUM = 1L - 1L;

    private static final Long DEFAULT_OTHER_LOADING = 1L;
    private static final Long UPDATED_OTHER_LOADING = 2L;
    private static final Long SMALLER_OTHER_LOADING = 1L - 1L;

    private static final Long DEFAULT_OTHER_DISCOUNT = 1L;
    private static final Long UPDATED_OTHER_DISCOUNT = 2L;
    private static final Long SMALLER_OTHER_DISCOUNT = 1L - 1L;

    private static final Long DEFAULT_ADD_ON_PREMIUM = 1L;
    private static final Long UPDATED_ADD_ON_PREMIUM = 2L;
    private static final Long SMALLER_ADD_ON_PREMIUM = 1L - 1L;

    private static final Long DEFAULT_LIABILITY_PREMIUM = 1L;
    private static final Long UPDATED_LIABILITY_PREMIUM = 2L;
    private static final Long SMALLER_LIABILITY_PREMIUM = 1L - 1L;

    private static final Long DEFAULT_OD_PREMIUM = 1L;
    private static final Long UPDATED_OD_PREMIUM = 2L;
    private static final Long SMALLER_OD_PREMIUM = 1L - 1L;

    private static final Boolean DEFAULT_PERSONAL_ACCIDENT_DISCOUNT = false;
    private static final Boolean UPDATED_PERSONAL_ACCIDENT_DISCOUNT = true;

    private static final Long DEFAULT_PERSONAL_ACCIDENT = 1L;
    private static final Long UPDATED_PERSONAL_ACCIDENT = 2L;
    private static final Long SMALLER_PERSONAL_ACCIDENT = 1L - 1L;

    private static final Long DEFAULT_GROSS_PREMIUM = 1L;
    private static final Long UPDATED_GROSS_PREMIUM = 2L;
    private static final Long SMALLER_GROSS_PREMIUM = 1L - 1L;

    private static final Long DEFAULT_GST = 1L;
    private static final Long UPDATED_GST = 2L;
    private static final Long SMALLER_GST = 1L - 1L;

    private static final Long DEFAULT_NET_PREMIUM = 1L;
    private static final Long UPDATED_NET_PREMIUM = 2L;
    private static final Long SMALLER_NET_PREMIUM = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/premiun-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PremiunDetailsRepository premiunDetailsRepository;

    @Autowired
    private PremiunDetailsMapper premiunDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPremiunDetailsMockMvc;

    private PremiunDetails premiunDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PremiunDetails createEntity(EntityManager em) {
        PremiunDetails premiunDetails = new PremiunDetails()
            .premium(DEFAULT_PREMIUM)
            .otherLoading(DEFAULT_OTHER_LOADING)
            .otherDiscount(DEFAULT_OTHER_DISCOUNT)
            .addOnPremium(DEFAULT_ADD_ON_PREMIUM)
            .liabilityPremium(DEFAULT_LIABILITY_PREMIUM)
            .odPremium(DEFAULT_OD_PREMIUM)
            .personalAccidentDiscount(DEFAULT_PERSONAL_ACCIDENT_DISCOUNT)
            .personalAccident(DEFAULT_PERSONAL_ACCIDENT)
            .grossPremium(DEFAULT_GROSS_PREMIUM)
            .gst(DEFAULT_GST)
            .netPremium(DEFAULT_NET_PREMIUM)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return premiunDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PremiunDetails createUpdatedEntity(EntityManager em) {
        PremiunDetails premiunDetails = new PremiunDetails()
            .premium(UPDATED_PREMIUM)
            .otherLoading(UPDATED_OTHER_LOADING)
            .otherDiscount(UPDATED_OTHER_DISCOUNT)
            .addOnPremium(UPDATED_ADD_ON_PREMIUM)
            .liabilityPremium(UPDATED_LIABILITY_PREMIUM)
            .odPremium(UPDATED_OD_PREMIUM)
            .personalAccidentDiscount(UPDATED_PERSONAL_ACCIDENT_DISCOUNT)
            .personalAccident(UPDATED_PERSONAL_ACCIDENT)
            .grossPremium(UPDATED_GROSS_PREMIUM)
            .gst(UPDATED_GST)
            .netPremium(UPDATED_NET_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return premiunDetails;
    }

    @BeforeEach
    public void initTest() {
        premiunDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createPremiunDetails() throws Exception {
        int databaseSizeBeforeCreate = premiunDetailsRepository.findAll().size();
        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);
        restPremiunDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PremiunDetails testPremiunDetails = premiunDetailsList.get(premiunDetailsList.size() - 1);
        assertThat(testPremiunDetails.getPremium()).isEqualTo(DEFAULT_PREMIUM);
        assertThat(testPremiunDetails.getOtherLoading()).isEqualTo(DEFAULT_OTHER_LOADING);
        assertThat(testPremiunDetails.getOtherDiscount()).isEqualTo(DEFAULT_OTHER_DISCOUNT);
        assertThat(testPremiunDetails.getAddOnPremium()).isEqualTo(DEFAULT_ADD_ON_PREMIUM);
        assertThat(testPremiunDetails.getLiabilityPremium()).isEqualTo(DEFAULT_LIABILITY_PREMIUM);
        assertThat(testPremiunDetails.getOdPremium()).isEqualTo(DEFAULT_OD_PREMIUM);
        assertThat(testPremiunDetails.getPersonalAccidentDiscount()).isEqualTo(DEFAULT_PERSONAL_ACCIDENT_DISCOUNT);
        assertThat(testPremiunDetails.getPersonalAccident()).isEqualTo(DEFAULT_PERSONAL_ACCIDENT);
        assertThat(testPremiunDetails.getGrossPremium()).isEqualTo(DEFAULT_GROSS_PREMIUM);
        assertThat(testPremiunDetails.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testPremiunDetails.getNetPremium()).isEqualTo(DEFAULT_NET_PREMIUM);
        assertThat(testPremiunDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPremiunDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPremiunDetailsWithExistingId() throws Exception {
        // Create the PremiunDetails with an existing ID
        premiunDetails.setId(1L);
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        int databaseSizeBeforeCreate = premiunDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPremiunDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = premiunDetailsRepository.findAll().size();
        // set the field null
        premiunDetails.setLastModified(null);

        // Create the PremiunDetails, which fails.
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        restPremiunDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = premiunDetailsRepository.findAll().size();
        // set the field null
        premiunDetails.setLastModifiedBy(null);

        // Create the PremiunDetails, which fails.
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        restPremiunDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPremiunDetails() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(premiunDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].otherLoading").value(hasItem(DEFAULT_OTHER_LOADING.intValue())))
            .andExpect(jsonPath("$.[*].otherDiscount").value(hasItem(DEFAULT_OTHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].addOnPremium").value(hasItem(DEFAULT_ADD_ON_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].liabilityPremium").value(hasItem(DEFAULT_LIABILITY_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].odPremium").value(hasItem(DEFAULT_OD_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].personalAccidentDiscount").value(hasItem(DEFAULT_PERSONAL_ACCIDENT_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].personalAccident").value(hasItem(DEFAULT_PERSONAL_ACCIDENT.intValue())))
            .andExpect(jsonPath("$.[*].grossPremium").value(hasItem(DEFAULT_GROSS_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST.intValue())))
            .andExpect(jsonPath("$.[*].netPremium").value(hasItem(DEFAULT_NET_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPremiunDetails() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get the premiunDetails
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, premiunDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(premiunDetails.getId().intValue()))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.intValue()))
            .andExpect(jsonPath("$.otherLoading").value(DEFAULT_OTHER_LOADING.intValue()))
            .andExpect(jsonPath("$.otherDiscount").value(DEFAULT_OTHER_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.addOnPremium").value(DEFAULT_ADD_ON_PREMIUM.intValue()))
            .andExpect(jsonPath("$.liabilityPremium").value(DEFAULT_LIABILITY_PREMIUM.intValue()))
            .andExpect(jsonPath("$.odPremium").value(DEFAULT_OD_PREMIUM.intValue()))
            .andExpect(jsonPath("$.personalAccidentDiscount").value(DEFAULT_PERSONAL_ACCIDENT_DISCOUNT.booleanValue()))
            .andExpect(jsonPath("$.personalAccident").value(DEFAULT_PERSONAL_ACCIDENT.intValue()))
            .andExpect(jsonPath("$.grossPremium").value(DEFAULT_GROSS_PREMIUM.intValue()))
            .andExpect(jsonPath("$.gst").value(DEFAULT_GST.intValue()))
            .andExpect(jsonPath("$.netPremium").value(DEFAULT_NET_PREMIUM.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPremiunDetailsByIdFiltering() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        Long id = premiunDetails.getId();

        defaultPremiunDetailsShouldBeFound("id.equals=" + id);
        defaultPremiunDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultPremiunDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPremiunDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultPremiunDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPremiunDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium equals to DEFAULT_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.equals=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium equals to UPDATED_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.equals=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium not equals to DEFAULT_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.notEquals=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium not equals to UPDATED_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.notEquals=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium in DEFAULT_PREMIUM or UPDATED_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.in=" + DEFAULT_PREMIUM + "," + UPDATED_PREMIUM);

        // Get all the premiunDetailsList where premium equals to UPDATED_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.in=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium is not null
        defaultPremiunDetailsShouldBeFound("premium.specified=true");

        // Get all the premiunDetailsList where premium is null
        defaultPremiunDetailsShouldNotBeFound("premium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium is greater than or equal to DEFAULT_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.greaterThanOrEqual=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium is greater than or equal to UPDATED_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.greaterThanOrEqual=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium is less than or equal to DEFAULT_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.lessThanOrEqual=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium is less than or equal to SMALLER_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.lessThanOrEqual=" + SMALLER_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium is less than DEFAULT_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.lessThan=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium is less than UPDATED_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.lessThan=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where premium is greater than DEFAULT_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("premium.greaterThan=" + DEFAULT_PREMIUM);

        // Get all the premiunDetailsList where premium is greater than SMALLER_PREMIUM
        defaultPremiunDetailsShouldBeFound("premium.greaterThan=" + SMALLER_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading equals to DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.equals=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading equals to UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.equals=" + UPDATED_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading not equals to DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.notEquals=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading not equals to UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.notEquals=" + UPDATED_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading in DEFAULT_OTHER_LOADING or UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.in=" + DEFAULT_OTHER_LOADING + "," + UPDATED_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading equals to UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.in=" + UPDATED_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading is not null
        defaultPremiunDetailsShouldBeFound("otherLoading.specified=true");

        // Get all the premiunDetailsList where otherLoading is null
        defaultPremiunDetailsShouldNotBeFound("otherLoading.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading is greater than or equal to DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.greaterThanOrEqual=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading is greater than or equal to UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.greaterThanOrEqual=" + UPDATED_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading is less than or equal to DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.lessThanOrEqual=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading is less than or equal to SMALLER_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.lessThanOrEqual=" + SMALLER_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading is less than DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.lessThan=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading is less than UPDATED_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.lessThan=" + UPDATED_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherLoadingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherLoading is greater than DEFAULT_OTHER_LOADING
        defaultPremiunDetailsShouldNotBeFound("otherLoading.greaterThan=" + DEFAULT_OTHER_LOADING);

        // Get all the premiunDetailsList where otherLoading is greater than SMALLER_OTHER_LOADING
        defaultPremiunDetailsShouldBeFound("otherLoading.greaterThan=" + SMALLER_OTHER_LOADING);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount equals to DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.equals=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount equals to UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.equals=" + UPDATED_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount not equals to DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.notEquals=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount not equals to UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.notEquals=" + UPDATED_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount in DEFAULT_OTHER_DISCOUNT or UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.in=" + DEFAULT_OTHER_DISCOUNT + "," + UPDATED_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount equals to UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.in=" + UPDATED_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount is not null
        defaultPremiunDetailsShouldBeFound("otherDiscount.specified=true");

        // Get all the premiunDetailsList where otherDiscount is null
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount is greater than or equal to DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.greaterThanOrEqual=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount is greater than or equal to UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.greaterThanOrEqual=" + UPDATED_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount is less than or equal to DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.lessThanOrEqual=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount is less than or equal to SMALLER_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.lessThanOrEqual=" + SMALLER_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount is less than DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.lessThan=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount is less than UPDATED_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.lessThan=" + UPDATED_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOtherDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where otherDiscount is greater than DEFAULT_OTHER_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("otherDiscount.greaterThan=" + DEFAULT_OTHER_DISCOUNT);

        // Get all the premiunDetailsList where otherDiscount is greater than SMALLER_OTHER_DISCOUNT
        defaultPremiunDetailsShouldBeFound("otherDiscount.greaterThan=" + SMALLER_OTHER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium equals to DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.equals=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium equals to UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.equals=" + UPDATED_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium not equals to DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.notEquals=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium not equals to UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.notEquals=" + UPDATED_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium in DEFAULT_ADD_ON_PREMIUM or UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.in=" + DEFAULT_ADD_ON_PREMIUM + "," + UPDATED_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium equals to UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.in=" + UPDATED_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium is not null
        defaultPremiunDetailsShouldBeFound("addOnPremium.specified=true");

        // Get all the premiunDetailsList where addOnPremium is null
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium is greater than or equal to DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.greaterThanOrEqual=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium is greater than or equal to UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.greaterThanOrEqual=" + UPDATED_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium is less than or equal to DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.lessThanOrEqual=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium is less than or equal to SMALLER_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.lessThanOrEqual=" + SMALLER_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium is less than DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.lessThan=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium is less than UPDATED_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.lessThan=" + UPDATED_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByAddOnPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where addOnPremium is greater than DEFAULT_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("addOnPremium.greaterThan=" + DEFAULT_ADD_ON_PREMIUM);

        // Get all the premiunDetailsList where addOnPremium is greater than SMALLER_ADD_ON_PREMIUM
        defaultPremiunDetailsShouldBeFound("addOnPremium.greaterThan=" + SMALLER_ADD_ON_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium equals to DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.equals=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium equals to UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.equals=" + UPDATED_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium not equals to DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.notEquals=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium not equals to UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.notEquals=" + UPDATED_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium in DEFAULT_LIABILITY_PREMIUM or UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.in=" + DEFAULT_LIABILITY_PREMIUM + "," + UPDATED_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium equals to UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.in=" + UPDATED_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium is not null
        defaultPremiunDetailsShouldBeFound("liabilityPremium.specified=true");

        // Get all the premiunDetailsList where liabilityPremium is null
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium is greater than or equal to DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.greaterThanOrEqual=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium is greater than or equal to UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.greaterThanOrEqual=" + UPDATED_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium is less than or equal to DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.lessThanOrEqual=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium is less than or equal to SMALLER_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.lessThanOrEqual=" + SMALLER_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium is less than DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.lessThan=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium is less than UPDATED_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.lessThan=" + UPDATED_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLiabilityPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where liabilityPremium is greater than DEFAULT_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("liabilityPremium.greaterThan=" + DEFAULT_LIABILITY_PREMIUM);

        // Get all the premiunDetailsList where liabilityPremium is greater than SMALLER_LIABILITY_PREMIUM
        defaultPremiunDetailsShouldBeFound("liabilityPremium.greaterThan=" + SMALLER_LIABILITY_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium equals to DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.equals=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium equals to UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.equals=" + UPDATED_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium not equals to DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.notEquals=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium not equals to UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.notEquals=" + UPDATED_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium in DEFAULT_OD_PREMIUM or UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.in=" + DEFAULT_OD_PREMIUM + "," + UPDATED_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium equals to UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.in=" + UPDATED_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium is not null
        defaultPremiunDetailsShouldBeFound("odPremium.specified=true");

        // Get all the premiunDetailsList where odPremium is null
        defaultPremiunDetailsShouldNotBeFound("odPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium is greater than or equal to DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.greaterThanOrEqual=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium is greater than or equal to UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.greaterThanOrEqual=" + UPDATED_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium is less than or equal to DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.lessThanOrEqual=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium is less than or equal to SMALLER_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.lessThanOrEqual=" + SMALLER_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium is less than DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.lessThan=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium is less than UPDATED_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.lessThan=" + UPDATED_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByOdPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where odPremium is greater than DEFAULT_OD_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("odPremium.greaterThan=" + DEFAULT_OD_PREMIUM);

        // Get all the premiunDetailsList where odPremium is greater than SMALLER_OD_PREMIUM
        defaultPremiunDetailsShouldBeFound("odPremium.greaterThan=" + SMALLER_OD_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccidentDiscount equals to DEFAULT_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldBeFound("personalAccidentDiscount.equals=" + DEFAULT_PERSONAL_ACCIDENT_DISCOUNT);

        // Get all the premiunDetailsList where personalAccidentDiscount equals to UPDATED_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("personalAccidentDiscount.equals=" + UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccidentDiscount not equals to DEFAULT_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("personalAccidentDiscount.notEquals=" + DEFAULT_PERSONAL_ACCIDENT_DISCOUNT);

        // Get all the premiunDetailsList where personalAccidentDiscount not equals to UPDATED_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldBeFound("personalAccidentDiscount.notEquals=" + UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccidentDiscount in DEFAULT_PERSONAL_ACCIDENT_DISCOUNT or UPDATED_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldBeFound(
            "personalAccidentDiscount.in=" + DEFAULT_PERSONAL_ACCIDENT_DISCOUNT + "," + UPDATED_PERSONAL_ACCIDENT_DISCOUNT
        );

        // Get all the premiunDetailsList where personalAccidentDiscount equals to UPDATED_PERSONAL_ACCIDENT_DISCOUNT
        defaultPremiunDetailsShouldNotBeFound("personalAccidentDiscount.in=" + UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccidentDiscount is not null
        defaultPremiunDetailsShouldBeFound("personalAccidentDiscount.specified=true");

        // Get all the premiunDetailsList where personalAccidentDiscount is null
        defaultPremiunDetailsShouldNotBeFound("personalAccidentDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident equals to DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.equals=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident equals to UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.equals=" + UPDATED_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident not equals to DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.notEquals=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident not equals to UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.notEquals=" + UPDATED_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident in DEFAULT_PERSONAL_ACCIDENT or UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.in=" + DEFAULT_PERSONAL_ACCIDENT + "," + UPDATED_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident equals to UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.in=" + UPDATED_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident is not null
        defaultPremiunDetailsShouldBeFound("personalAccident.specified=true");

        // Get all the premiunDetailsList where personalAccident is null
        defaultPremiunDetailsShouldNotBeFound("personalAccident.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident is greater than or equal to DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.greaterThanOrEqual=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident is greater than or equal to UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.greaterThanOrEqual=" + UPDATED_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident is less than or equal to DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.lessThanOrEqual=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident is less than or equal to SMALLER_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.lessThanOrEqual=" + SMALLER_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident is less than DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.lessThan=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident is less than UPDATED_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.lessThan=" + UPDATED_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPersonalAccidentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where personalAccident is greater than DEFAULT_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldNotBeFound("personalAccident.greaterThan=" + DEFAULT_PERSONAL_ACCIDENT);

        // Get all the premiunDetailsList where personalAccident is greater than SMALLER_PERSONAL_ACCIDENT
        defaultPremiunDetailsShouldBeFound("personalAccident.greaterThan=" + SMALLER_PERSONAL_ACCIDENT);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium equals to DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.equals=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium equals to UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.equals=" + UPDATED_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium not equals to DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.notEquals=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium not equals to UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.notEquals=" + UPDATED_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium in DEFAULT_GROSS_PREMIUM or UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.in=" + DEFAULT_GROSS_PREMIUM + "," + UPDATED_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium equals to UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.in=" + UPDATED_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium is not null
        defaultPremiunDetailsShouldBeFound("grossPremium.specified=true");

        // Get all the premiunDetailsList where grossPremium is null
        defaultPremiunDetailsShouldNotBeFound("grossPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium is greater than or equal to DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.greaterThanOrEqual=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium is greater than or equal to UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.greaterThanOrEqual=" + UPDATED_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium is less than or equal to DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.lessThanOrEqual=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium is less than or equal to SMALLER_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.lessThanOrEqual=" + SMALLER_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium is less than DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.lessThan=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium is less than UPDATED_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.lessThan=" + UPDATED_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGrossPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where grossPremium is greater than DEFAULT_GROSS_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("grossPremium.greaterThan=" + DEFAULT_GROSS_PREMIUM);

        // Get all the premiunDetailsList where grossPremium is greater than SMALLER_GROSS_PREMIUM
        defaultPremiunDetailsShouldBeFound("grossPremium.greaterThan=" + SMALLER_GROSS_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst equals to DEFAULT_GST
        defaultPremiunDetailsShouldBeFound("gst.equals=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst equals to UPDATED_GST
        defaultPremiunDetailsShouldNotBeFound("gst.equals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst not equals to DEFAULT_GST
        defaultPremiunDetailsShouldNotBeFound("gst.notEquals=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst not equals to UPDATED_GST
        defaultPremiunDetailsShouldBeFound("gst.notEquals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst in DEFAULT_GST or UPDATED_GST
        defaultPremiunDetailsShouldBeFound("gst.in=" + DEFAULT_GST + "," + UPDATED_GST);

        // Get all the premiunDetailsList where gst equals to UPDATED_GST
        defaultPremiunDetailsShouldNotBeFound("gst.in=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst is not null
        defaultPremiunDetailsShouldBeFound("gst.specified=true");

        // Get all the premiunDetailsList where gst is null
        defaultPremiunDetailsShouldNotBeFound("gst.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst is greater than or equal to DEFAULT_GST
        defaultPremiunDetailsShouldBeFound("gst.greaterThanOrEqual=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst is greater than or equal to UPDATED_GST
        defaultPremiunDetailsShouldNotBeFound("gst.greaterThanOrEqual=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst is less than or equal to DEFAULT_GST
        defaultPremiunDetailsShouldBeFound("gst.lessThanOrEqual=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst is less than or equal to SMALLER_GST
        defaultPremiunDetailsShouldNotBeFound("gst.lessThanOrEqual=" + SMALLER_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst is less than DEFAULT_GST
        defaultPremiunDetailsShouldNotBeFound("gst.lessThan=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst is less than UPDATED_GST
        defaultPremiunDetailsShouldBeFound("gst.lessThan=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByGstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where gst is greater than DEFAULT_GST
        defaultPremiunDetailsShouldNotBeFound("gst.greaterThan=" + DEFAULT_GST);

        // Get all the premiunDetailsList where gst is greater than SMALLER_GST
        defaultPremiunDetailsShouldBeFound("gst.greaterThan=" + SMALLER_GST);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium equals to DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.equals=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium equals to UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.equals=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium not equals to DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.notEquals=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium not equals to UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.notEquals=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium in DEFAULT_NET_PREMIUM or UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.in=" + DEFAULT_NET_PREMIUM + "," + UPDATED_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium equals to UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.in=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium is not null
        defaultPremiunDetailsShouldBeFound("netPremium.specified=true");

        // Get all the premiunDetailsList where netPremium is null
        defaultPremiunDetailsShouldNotBeFound("netPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium is greater than or equal to DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.greaterThanOrEqual=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium is greater than or equal to UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.greaterThanOrEqual=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium is less than or equal to DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.lessThanOrEqual=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium is less than or equal to SMALLER_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.lessThanOrEqual=" + SMALLER_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium is less than DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.lessThan=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium is less than UPDATED_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.lessThan=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByNetPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where netPremium is greater than DEFAULT_NET_PREMIUM
        defaultPremiunDetailsShouldNotBeFound("netPremium.greaterThan=" + DEFAULT_NET_PREMIUM);

        // Get all the premiunDetailsList where netPremium is greater than SMALLER_NET_PREMIUM
        defaultPremiunDetailsShouldBeFound("netPremium.greaterThan=" + SMALLER_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPremiunDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the premiunDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPremiunDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the premiunDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the premiunDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified is not null
        defaultPremiunDetailsShouldBeFound("lastModified.specified=true");

        // Get all the premiunDetailsList where lastModified is null
        defaultPremiunDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPremiunDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the premiunDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPremiunDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the premiunDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPremiunDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the premiunDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the premiunDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the premiunDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy is not null
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the premiunDetailsList where lastModifiedBy is null
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the premiunDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        // Get all the premiunDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the premiunDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPremiunDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPremiunDetailsByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);
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
        premiunDetails.setPolicy(policy);
        policy.setPremiunDetails(premiunDetails);
        premiunDetailsRepository.saveAndFlush(premiunDetails);
        Long policyId = policy.getId();

        // Get all the premiunDetailsList where policy equals to policyId
        defaultPremiunDetailsShouldBeFound("policyId.equals=" + policyId);

        // Get all the premiunDetailsList where policy equals to (policyId + 1)
        defaultPremiunDetailsShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPremiunDetailsShouldBeFound(String filter) throws Exception {
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(premiunDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].otherLoading").value(hasItem(DEFAULT_OTHER_LOADING.intValue())))
            .andExpect(jsonPath("$.[*].otherDiscount").value(hasItem(DEFAULT_OTHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].addOnPremium").value(hasItem(DEFAULT_ADD_ON_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].liabilityPremium").value(hasItem(DEFAULT_LIABILITY_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].odPremium").value(hasItem(DEFAULT_OD_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].personalAccidentDiscount").value(hasItem(DEFAULT_PERSONAL_ACCIDENT_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].personalAccident").value(hasItem(DEFAULT_PERSONAL_ACCIDENT.intValue())))
            .andExpect(jsonPath("$.[*].grossPremium").value(hasItem(DEFAULT_GROSS_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST.intValue())))
            .andExpect(jsonPath("$.[*].netPremium").value(hasItem(DEFAULT_NET_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPremiunDetailsShouldNotBeFound(String filter) throws Exception {
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPremiunDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPremiunDetails() throws Exception {
        // Get the premiunDetails
        restPremiunDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPremiunDetails() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();

        // Update the premiunDetails
        PremiunDetails updatedPremiunDetails = premiunDetailsRepository.findById(premiunDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPremiunDetails are not directly saved in db
        em.detach(updatedPremiunDetails);
        updatedPremiunDetails
            .premium(UPDATED_PREMIUM)
            .otherLoading(UPDATED_OTHER_LOADING)
            .otherDiscount(UPDATED_OTHER_DISCOUNT)
            .addOnPremium(UPDATED_ADD_ON_PREMIUM)
            .liabilityPremium(UPDATED_LIABILITY_PREMIUM)
            .odPremium(UPDATED_OD_PREMIUM)
            .personalAccidentDiscount(UPDATED_PERSONAL_ACCIDENT_DISCOUNT)
            .personalAccident(UPDATED_PERSONAL_ACCIDENT)
            .grossPremium(UPDATED_GROSS_PREMIUM)
            .gst(UPDATED_GST)
            .netPremium(UPDATED_NET_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(updatedPremiunDetails);

        restPremiunDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, premiunDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
        PremiunDetails testPremiunDetails = premiunDetailsList.get(premiunDetailsList.size() - 1);
        assertThat(testPremiunDetails.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testPremiunDetails.getOtherLoading()).isEqualTo(UPDATED_OTHER_LOADING);
        assertThat(testPremiunDetails.getOtherDiscount()).isEqualTo(UPDATED_OTHER_DISCOUNT);
        assertThat(testPremiunDetails.getAddOnPremium()).isEqualTo(UPDATED_ADD_ON_PREMIUM);
        assertThat(testPremiunDetails.getLiabilityPremium()).isEqualTo(UPDATED_LIABILITY_PREMIUM);
        assertThat(testPremiunDetails.getOdPremium()).isEqualTo(UPDATED_OD_PREMIUM);
        assertThat(testPremiunDetails.getPersonalAccidentDiscount()).isEqualTo(UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
        assertThat(testPremiunDetails.getPersonalAccident()).isEqualTo(UPDATED_PERSONAL_ACCIDENT);
        assertThat(testPremiunDetails.getGrossPremium()).isEqualTo(UPDATED_GROSS_PREMIUM);
        assertThat(testPremiunDetails.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testPremiunDetails.getNetPremium()).isEqualTo(UPDATED_NET_PREMIUM);
        assertThat(testPremiunDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPremiunDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, premiunDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePremiunDetailsWithPatch() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();

        // Update the premiunDetails using partial update
        PremiunDetails partialUpdatedPremiunDetails = new PremiunDetails();
        partialUpdatedPremiunDetails.setId(premiunDetails.getId());

        partialUpdatedPremiunDetails
            .otherDiscount(UPDATED_OTHER_DISCOUNT)
            .addOnPremium(UPDATED_ADD_ON_PREMIUM)
            .liabilityPremium(UPDATED_LIABILITY_PREMIUM)
            .personalAccidentDiscount(UPDATED_PERSONAL_ACCIDENT_DISCOUNT)
            .grossPremium(UPDATED_GROSS_PREMIUM)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPremiunDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremiunDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPremiunDetails))
            )
            .andExpect(status().isOk());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
        PremiunDetails testPremiunDetails = premiunDetailsList.get(premiunDetailsList.size() - 1);
        assertThat(testPremiunDetails.getPremium()).isEqualTo(DEFAULT_PREMIUM);
        assertThat(testPremiunDetails.getOtherLoading()).isEqualTo(DEFAULT_OTHER_LOADING);
        assertThat(testPremiunDetails.getOtherDiscount()).isEqualTo(UPDATED_OTHER_DISCOUNT);
        assertThat(testPremiunDetails.getAddOnPremium()).isEqualTo(UPDATED_ADD_ON_PREMIUM);
        assertThat(testPremiunDetails.getLiabilityPremium()).isEqualTo(UPDATED_LIABILITY_PREMIUM);
        assertThat(testPremiunDetails.getOdPremium()).isEqualTo(DEFAULT_OD_PREMIUM);
        assertThat(testPremiunDetails.getPersonalAccidentDiscount()).isEqualTo(UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
        assertThat(testPremiunDetails.getPersonalAccident()).isEqualTo(DEFAULT_PERSONAL_ACCIDENT);
        assertThat(testPremiunDetails.getGrossPremium()).isEqualTo(UPDATED_GROSS_PREMIUM);
        assertThat(testPremiunDetails.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testPremiunDetails.getNetPremium()).isEqualTo(DEFAULT_NET_PREMIUM);
        assertThat(testPremiunDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPremiunDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePremiunDetailsWithPatch() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();

        // Update the premiunDetails using partial update
        PremiunDetails partialUpdatedPremiunDetails = new PremiunDetails();
        partialUpdatedPremiunDetails.setId(premiunDetails.getId());

        partialUpdatedPremiunDetails
            .premium(UPDATED_PREMIUM)
            .otherLoading(UPDATED_OTHER_LOADING)
            .otherDiscount(UPDATED_OTHER_DISCOUNT)
            .addOnPremium(UPDATED_ADD_ON_PREMIUM)
            .liabilityPremium(UPDATED_LIABILITY_PREMIUM)
            .odPremium(UPDATED_OD_PREMIUM)
            .personalAccidentDiscount(UPDATED_PERSONAL_ACCIDENT_DISCOUNT)
            .personalAccident(UPDATED_PERSONAL_ACCIDENT)
            .grossPremium(UPDATED_GROSS_PREMIUM)
            .gst(UPDATED_GST)
            .netPremium(UPDATED_NET_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPremiunDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPremiunDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPremiunDetails))
            )
            .andExpect(status().isOk());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
        PremiunDetails testPremiunDetails = premiunDetailsList.get(premiunDetailsList.size() - 1);
        assertThat(testPremiunDetails.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testPremiunDetails.getOtherLoading()).isEqualTo(UPDATED_OTHER_LOADING);
        assertThat(testPremiunDetails.getOtherDiscount()).isEqualTo(UPDATED_OTHER_DISCOUNT);
        assertThat(testPremiunDetails.getAddOnPremium()).isEqualTo(UPDATED_ADD_ON_PREMIUM);
        assertThat(testPremiunDetails.getLiabilityPremium()).isEqualTo(UPDATED_LIABILITY_PREMIUM);
        assertThat(testPremiunDetails.getOdPremium()).isEqualTo(UPDATED_OD_PREMIUM);
        assertThat(testPremiunDetails.getPersonalAccidentDiscount()).isEqualTo(UPDATED_PERSONAL_ACCIDENT_DISCOUNT);
        assertThat(testPremiunDetails.getPersonalAccident()).isEqualTo(UPDATED_PERSONAL_ACCIDENT);
        assertThat(testPremiunDetails.getGrossPremium()).isEqualTo(UPDATED_GROSS_PREMIUM);
        assertThat(testPremiunDetails.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testPremiunDetails.getNetPremium()).isEqualTo(UPDATED_NET_PREMIUM);
        assertThat(testPremiunDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPremiunDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, premiunDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPremiunDetails() throws Exception {
        int databaseSizeBeforeUpdate = premiunDetailsRepository.findAll().size();
        premiunDetails.setId(count.incrementAndGet());

        // Create the PremiunDetails
        PremiunDetailsDTO premiunDetailsDTO = premiunDetailsMapper.toDto(premiunDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPremiunDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(premiunDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PremiunDetails in the database
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePremiunDetails() throws Exception {
        // Initialize the database
        premiunDetailsRepository.saveAndFlush(premiunDetails);

        int databaseSizeBeforeDelete = premiunDetailsRepository.findAll().size();

        // Delete the premiunDetails
        restPremiunDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, premiunDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PremiunDetails> premiunDetailsList = premiunDetailsRepository.findAll();
        assertThat(premiunDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
