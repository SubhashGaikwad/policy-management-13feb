package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Rider;
import com.mycompany.myapp.repository.RiderRepository;
import com.mycompany.myapp.service.criteria.RiderCriteria;
import com.mycompany.myapp.service.dto.RiderDTO;
import com.mycompany.myapp.service.mapper.RiderMapper;
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
 * Integration tests for the {@link RiderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RiderResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMM_DATE = "AAAAAAAAAA";
    private static final String UPDATED_COMM_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_SUM = "AAAAAAAAAA";
    private static final String UPDATED_SUM = "BBBBBBBBBB";

    private static final String DEFAULT_TERM = "AAAAAAAAAA";
    private static final String UPDATED_TERM = "BBBBBBBBBB";

    private static final String DEFAULT_PPT = "AAAAAAAAAA";
    private static final String UPDATED_PPT = "BBBBBBBBBB";

    private static final Long DEFAULT_PREMIUM = 1L;
    private static final Long UPDATED_PREMIUM = 2L;
    private static final Long SMALLER_PREMIUM = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/riders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private RiderMapper riderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRiderMockMvc;

    private Rider rider;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rider createEntity(EntityManager em) {
        Rider rider = new Rider()
            .name(DEFAULT_NAME)
            .commDate(DEFAULT_COMM_DATE)
            .sum(DEFAULT_SUM)
            .term(DEFAULT_TERM)
            .ppt(DEFAULT_PPT)
            .premium(DEFAULT_PREMIUM)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return rider;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rider createUpdatedEntity(EntityManager em) {
        Rider rider = new Rider()
            .name(UPDATED_NAME)
            .commDate(UPDATED_COMM_DATE)
            .sum(UPDATED_SUM)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .premium(UPDATED_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return rider;
    }

    @BeforeEach
    public void initTest() {
        rider = createEntity(em);
    }

    @Test
    @Transactional
    void createRider() throws Exception {
        int databaseSizeBeforeCreate = riderRepository.findAll().size();
        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);
        restRiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isCreated());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeCreate + 1);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRider.getCommDate()).isEqualTo(DEFAULT_COMM_DATE);
        assertThat(testRider.getSum()).isEqualTo(DEFAULT_SUM);
        assertThat(testRider.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testRider.getPpt()).isEqualTo(DEFAULT_PPT);
        assertThat(testRider.getPremium()).isEqualTo(DEFAULT_PREMIUM);
        assertThat(testRider.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRider.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createRiderWithExistingId() throws Exception {
        // Create the Rider with an existing ID
        rider.setId(1L);
        RiderDTO riderDTO = riderMapper.toDto(rider);

        int databaseSizeBeforeCreate = riderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = riderRepository.findAll().size();
        // set the field null
        rider.setLastModified(null);

        // Create the Rider, which fails.
        RiderDTO riderDTO = riderMapper.toDto(rider);

        restRiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isBadRequest());

        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = riderRepository.findAll().size();
        // set the field null
        rider.setLastModifiedBy(null);

        // Create the Rider, which fails.
        RiderDTO riderDTO = riderMapper.toDto(rider);

        restRiderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isBadRequest());

        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRiders() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList
        restRiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].commDate").value(hasItem(DEFAULT_COMM_DATE)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].ppt").value(hasItem(DEFAULT_PPT)))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get the rider
        restRiderMockMvc
            .perform(get(ENTITY_API_URL_ID, rider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rider.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.commDate").value(DEFAULT_COMM_DATE))
            .andExpect(jsonPath("$.sum").value(DEFAULT_SUM))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.ppt").value(DEFAULT_PPT))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getRidersByIdFiltering() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        Long id = rider.getId();

        defaultRiderShouldBeFound("id.equals=" + id);
        defaultRiderShouldNotBeFound("id.notEquals=" + id);

        defaultRiderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRiderShouldNotBeFound("id.greaterThan=" + id);

        defaultRiderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRiderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRidersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name equals to DEFAULT_NAME
        defaultRiderShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the riderList where name equals to UPDATED_NAME
        defaultRiderShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRidersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name not equals to DEFAULT_NAME
        defaultRiderShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the riderList where name not equals to UPDATED_NAME
        defaultRiderShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRidersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRiderShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the riderList where name equals to UPDATED_NAME
        defaultRiderShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRidersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name is not null
        defaultRiderShouldBeFound("name.specified=true");

        // Get all the riderList where name is null
        defaultRiderShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByNameContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name contains DEFAULT_NAME
        defaultRiderShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the riderList where name contains UPDATED_NAME
        defaultRiderShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRidersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where name does not contain DEFAULT_NAME
        defaultRiderShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the riderList where name does not contain UPDATED_NAME
        defaultRiderShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllRidersByCommDateIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate equals to DEFAULT_COMM_DATE
        defaultRiderShouldBeFound("commDate.equals=" + DEFAULT_COMM_DATE);

        // Get all the riderList where commDate equals to UPDATED_COMM_DATE
        defaultRiderShouldNotBeFound("commDate.equals=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllRidersByCommDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate not equals to DEFAULT_COMM_DATE
        defaultRiderShouldNotBeFound("commDate.notEquals=" + DEFAULT_COMM_DATE);

        // Get all the riderList where commDate not equals to UPDATED_COMM_DATE
        defaultRiderShouldBeFound("commDate.notEquals=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllRidersByCommDateIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate in DEFAULT_COMM_DATE or UPDATED_COMM_DATE
        defaultRiderShouldBeFound("commDate.in=" + DEFAULT_COMM_DATE + "," + UPDATED_COMM_DATE);

        // Get all the riderList where commDate equals to UPDATED_COMM_DATE
        defaultRiderShouldNotBeFound("commDate.in=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllRidersByCommDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate is not null
        defaultRiderShouldBeFound("commDate.specified=true");

        // Get all the riderList where commDate is null
        defaultRiderShouldNotBeFound("commDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByCommDateContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate contains DEFAULT_COMM_DATE
        defaultRiderShouldBeFound("commDate.contains=" + DEFAULT_COMM_DATE);

        // Get all the riderList where commDate contains UPDATED_COMM_DATE
        defaultRiderShouldNotBeFound("commDate.contains=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllRidersByCommDateNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where commDate does not contain DEFAULT_COMM_DATE
        defaultRiderShouldNotBeFound("commDate.doesNotContain=" + DEFAULT_COMM_DATE);

        // Get all the riderList where commDate does not contain UPDATED_COMM_DATE
        defaultRiderShouldBeFound("commDate.doesNotContain=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllRidersBySumIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum equals to DEFAULT_SUM
        defaultRiderShouldBeFound("sum.equals=" + DEFAULT_SUM);

        // Get all the riderList where sum equals to UPDATED_SUM
        defaultRiderShouldNotBeFound("sum.equals=" + UPDATED_SUM);
    }

    @Test
    @Transactional
    void getAllRidersBySumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum not equals to DEFAULT_SUM
        defaultRiderShouldNotBeFound("sum.notEquals=" + DEFAULT_SUM);

        // Get all the riderList where sum not equals to UPDATED_SUM
        defaultRiderShouldBeFound("sum.notEquals=" + UPDATED_SUM);
    }

    @Test
    @Transactional
    void getAllRidersBySumIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum in DEFAULT_SUM or UPDATED_SUM
        defaultRiderShouldBeFound("sum.in=" + DEFAULT_SUM + "," + UPDATED_SUM);

        // Get all the riderList where sum equals to UPDATED_SUM
        defaultRiderShouldNotBeFound("sum.in=" + UPDATED_SUM);
    }

    @Test
    @Transactional
    void getAllRidersBySumIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum is not null
        defaultRiderShouldBeFound("sum.specified=true");

        // Get all the riderList where sum is null
        defaultRiderShouldNotBeFound("sum.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersBySumContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum contains DEFAULT_SUM
        defaultRiderShouldBeFound("sum.contains=" + DEFAULT_SUM);

        // Get all the riderList where sum contains UPDATED_SUM
        defaultRiderShouldNotBeFound("sum.contains=" + UPDATED_SUM);
    }

    @Test
    @Transactional
    void getAllRidersBySumNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where sum does not contain DEFAULT_SUM
        defaultRiderShouldNotBeFound("sum.doesNotContain=" + DEFAULT_SUM);

        // Get all the riderList where sum does not contain UPDATED_SUM
        defaultRiderShouldBeFound("sum.doesNotContain=" + UPDATED_SUM);
    }

    @Test
    @Transactional
    void getAllRidersByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term equals to DEFAULT_TERM
        defaultRiderShouldBeFound("term.equals=" + DEFAULT_TERM);

        // Get all the riderList where term equals to UPDATED_TERM
        defaultRiderShouldNotBeFound("term.equals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllRidersByTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term not equals to DEFAULT_TERM
        defaultRiderShouldNotBeFound("term.notEquals=" + DEFAULT_TERM);

        // Get all the riderList where term not equals to UPDATED_TERM
        defaultRiderShouldBeFound("term.notEquals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllRidersByTermIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term in DEFAULT_TERM or UPDATED_TERM
        defaultRiderShouldBeFound("term.in=" + DEFAULT_TERM + "," + UPDATED_TERM);

        // Get all the riderList where term equals to UPDATED_TERM
        defaultRiderShouldNotBeFound("term.in=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllRidersByTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term is not null
        defaultRiderShouldBeFound("term.specified=true");

        // Get all the riderList where term is null
        defaultRiderShouldNotBeFound("term.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByTermContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term contains DEFAULT_TERM
        defaultRiderShouldBeFound("term.contains=" + DEFAULT_TERM);

        // Get all the riderList where term contains UPDATED_TERM
        defaultRiderShouldNotBeFound("term.contains=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllRidersByTermNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where term does not contain DEFAULT_TERM
        defaultRiderShouldNotBeFound("term.doesNotContain=" + DEFAULT_TERM);

        // Get all the riderList where term does not contain UPDATED_TERM
        defaultRiderShouldBeFound("term.doesNotContain=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllRidersByPptIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt equals to DEFAULT_PPT
        defaultRiderShouldBeFound("ppt.equals=" + DEFAULT_PPT);

        // Get all the riderList where ppt equals to UPDATED_PPT
        defaultRiderShouldNotBeFound("ppt.equals=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllRidersByPptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt not equals to DEFAULT_PPT
        defaultRiderShouldNotBeFound("ppt.notEquals=" + DEFAULT_PPT);

        // Get all the riderList where ppt not equals to UPDATED_PPT
        defaultRiderShouldBeFound("ppt.notEquals=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllRidersByPptIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt in DEFAULT_PPT or UPDATED_PPT
        defaultRiderShouldBeFound("ppt.in=" + DEFAULT_PPT + "," + UPDATED_PPT);

        // Get all the riderList where ppt equals to UPDATED_PPT
        defaultRiderShouldNotBeFound("ppt.in=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllRidersByPptIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt is not null
        defaultRiderShouldBeFound("ppt.specified=true");

        // Get all the riderList where ppt is null
        defaultRiderShouldNotBeFound("ppt.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByPptContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt contains DEFAULT_PPT
        defaultRiderShouldBeFound("ppt.contains=" + DEFAULT_PPT);

        // Get all the riderList where ppt contains UPDATED_PPT
        defaultRiderShouldNotBeFound("ppt.contains=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllRidersByPptNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where ppt does not contain DEFAULT_PPT
        defaultRiderShouldNotBeFound("ppt.doesNotContain=" + DEFAULT_PPT);

        // Get all the riderList where ppt does not contain UPDATED_PPT
        defaultRiderShouldBeFound("ppt.doesNotContain=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium equals to DEFAULT_PREMIUM
        defaultRiderShouldBeFound("premium.equals=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium equals to UPDATED_PREMIUM
        defaultRiderShouldNotBeFound("premium.equals=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium not equals to DEFAULT_PREMIUM
        defaultRiderShouldNotBeFound("premium.notEquals=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium not equals to UPDATED_PREMIUM
        defaultRiderShouldBeFound("premium.notEquals=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium in DEFAULT_PREMIUM or UPDATED_PREMIUM
        defaultRiderShouldBeFound("premium.in=" + DEFAULT_PREMIUM + "," + UPDATED_PREMIUM);

        // Get all the riderList where premium equals to UPDATED_PREMIUM
        defaultRiderShouldNotBeFound("premium.in=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium is not null
        defaultRiderShouldBeFound("premium.specified=true");

        // Get all the riderList where premium is null
        defaultRiderShouldNotBeFound("premium.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium is greater than or equal to DEFAULT_PREMIUM
        defaultRiderShouldBeFound("premium.greaterThanOrEqual=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium is greater than or equal to UPDATED_PREMIUM
        defaultRiderShouldNotBeFound("premium.greaterThanOrEqual=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium is less than or equal to DEFAULT_PREMIUM
        defaultRiderShouldBeFound("premium.lessThanOrEqual=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium is less than or equal to SMALLER_PREMIUM
        defaultRiderShouldNotBeFound("premium.lessThanOrEqual=" + SMALLER_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium is less than DEFAULT_PREMIUM
        defaultRiderShouldNotBeFound("premium.lessThan=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium is less than UPDATED_PREMIUM
        defaultRiderShouldBeFound("premium.lessThan=" + UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where premium is greater than DEFAULT_PREMIUM
        defaultRiderShouldNotBeFound("premium.greaterThan=" + DEFAULT_PREMIUM);

        // Get all the riderList where premium is greater than SMALLER_PREMIUM
        defaultRiderShouldBeFound("premium.greaterThan=" + SMALLER_PREMIUM);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRiderShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the riderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRiderShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultRiderShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the riderList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultRiderShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRiderShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the riderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRiderShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified is not null
        defaultRiderShouldBeFound("lastModified.specified=true");

        // Get all the riderList where lastModified is null
        defaultRiderShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultRiderShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the riderList where lastModified contains UPDATED_LAST_MODIFIED
        defaultRiderShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultRiderShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the riderList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultRiderShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRiderShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the riderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRiderShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the riderList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the riderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy is not null
        defaultRiderShouldBeFound("lastModifiedBy.specified=true");

        // Get all the riderList where lastModifiedBy is null
        defaultRiderShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRiderShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the riderList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRidersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        // Get all the riderList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRiderShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the riderList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRiderShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRiderShouldBeFound(String filter) throws Exception {
        restRiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rider.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].commDate").value(hasItem(DEFAULT_COMM_DATE)))
            .andExpect(jsonPath("$.[*].sum").value(hasItem(DEFAULT_SUM)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].ppt").value(hasItem(DEFAULT_PPT)))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restRiderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRiderShouldNotBeFound(String filter) throws Exception {
        restRiderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRiderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRider() throws Exception {
        // Get the rider
        restRiderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Update the rider
        Rider updatedRider = riderRepository.findById(rider.getId()).get();
        // Disconnect from session so that the updates on updatedRider are not directly saved in db
        em.detach(updatedRider);
        updatedRider
            .name(UPDATED_NAME)
            .commDate(UPDATED_COMM_DATE)
            .sum(UPDATED_SUM)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .premium(UPDATED_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        RiderDTO riderDTO = riderMapper.toDto(updatedRider);

        restRiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(riderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRider.getCommDate()).isEqualTo(UPDATED_COMM_DATE);
        assertThat(testRider.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testRider.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testRider.getPpt()).isEqualTo(UPDATED_PPT);
        assertThat(testRider.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testRider.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRider.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, riderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(riderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(riderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRiderWithPatch() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Update the rider using partial update
        Rider partialUpdatedRider = new Rider();
        partialUpdatedRider.setId(rider.getId());

        partialUpdatedRider
            .name(UPDATED_NAME)
            .sum(UPDATED_SUM)
            .ppt(UPDATED_PPT)
            .premium(UPDATED_PREMIUM)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRider.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRider))
            )
            .andExpect(status().isOk());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRider.getCommDate()).isEqualTo(DEFAULT_COMM_DATE);
        assertThat(testRider.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testRider.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testRider.getPpt()).isEqualTo(UPDATED_PPT);
        assertThat(testRider.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testRider.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRider.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateRiderWithPatch() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        int databaseSizeBeforeUpdate = riderRepository.findAll().size();

        // Update the rider using partial update
        Rider partialUpdatedRider = new Rider();
        partialUpdatedRider.setId(rider.getId());

        partialUpdatedRider
            .name(UPDATED_NAME)
            .commDate(UPDATED_COMM_DATE)
            .sum(UPDATED_SUM)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .premium(UPDATED_PREMIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restRiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRider.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRider))
            )
            .andExpect(status().isOk());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
        Rider testRider = riderList.get(riderList.size() - 1);
        assertThat(testRider.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRider.getCommDate()).isEqualTo(UPDATED_COMM_DATE);
        assertThat(testRider.getSum()).isEqualTo(UPDATED_SUM);
        assertThat(testRider.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testRider.getPpt()).isEqualTo(UPDATED_PPT);
        assertThat(testRider.getPremium()).isEqualTo(UPDATED_PREMIUM);
        assertThat(testRider.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRider.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, riderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(riderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(riderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRider() throws Exception {
        int databaseSizeBeforeUpdate = riderRepository.findAll().size();
        rider.setId(count.incrementAndGet());

        // Create the Rider
        RiderDTO riderDTO = riderMapper.toDto(rider);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRiderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(riderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rider in the database
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRider() throws Exception {
        // Initialize the database
        riderRepository.saveAndFlush(rider);

        int databaseSizeBeforeDelete = riderRepository.findAll().size();

        // Delete the rider
        restRiderMockMvc
            .perform(delete(ENTITY_API_URL_ID, rider.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rider> riderList = riderRepository.findAll();
        assertThat(riderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
