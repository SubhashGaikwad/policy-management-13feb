package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ParameterLookup;
import com.mycompany.myapp.domain.VehicleDetails;
import com.mycompany.myapp.domain.enumeration.ParameterType;
import com.mycompany.myapp.repository.ParameterLookupRepository;
import com.mycompany.myapp.service.criteria.ParameterLookupCriteria;
import com.mycompany.myapp.service.dto.ParameterLookupDTO;
import com.mycompany.myapp.service.mapper.ParameterLookupMapper;
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
 * Integration tests for the {@link ParameterLookupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParameterLookupResourceIT {

    private static final Long DEFAULT_NAME = 1L;
    private static final Long UPDATED_NAME = 2L;
    private static final Long SMALLER_NAME = 1L - 1L;

    private static final ParameterType DEFAULT_TYPE = ParameterType.MAKE;
    private static final ParameterType UPDATED_TYPE = ParameterType.MODEL;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parameter-lookups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParameterLookupRepository parameterLookupRepository;

    @Autowired
    private ParameterLookupMapper parameterLookupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParameterLookupMockMvc;

    private ParameterLookup parameterLookup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParameterLookup createEntity(EntityManager em) {
        ParameterLookup parameterLookup = new ParameterLookup()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return parameterLookup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParameterLookup createUpdatedEntity(EntityManager em) {
        ParameterLookup parameterLookup = new ParameterLookup()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return parameterLookup;
    }

    @BeforeEach
    public void initTest() {
        parameterLookup = createEntity(em);
    }

    @Test
    @Transactional
    void createParameterLookup() throws Exception {
        int databaseSizeBeforeCreate = parameterLookupRepository.findAll().size();
        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);
        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeCreate + 1);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParameterLookup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createParameterLookupWithExistingId() throws Exception {
        // Create the ParameterLookup with an existing ID
        parameterLookup.setId(1L);
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        int databaseSizeBeforeCreate = parameterLookupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = parameterLookupRepository.findAll().size();
        // set the field null
        parameterLookup.setLastModified(null);

        // Create the ParameterLookup, which fails.
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = parameterLookupRepository.findAll().size();
        // set the field null
        parameterLookup.setLastModifiedBy(null);

        // Create the ParameterLookup, which fails.
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        restParameterLookupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParameterLookups() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get the parameterLookup
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL_ID, parameterLookup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parameterLookup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getParameterLookupsByIdFiltering() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        Long id = parameterLookup.getId();

        defaultParameterLookupShouldBeFound("id.equals=" + id);
        defaultParameterLookupShouldNotBeFound("id.notEquals=" + id);

        defaultParameterLookupShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultParameterLookupShouldNotBeFound("id.greaterThan=" + id);

        defaultParameterLookupShouldBeFound("id.lessThanOrEqual=" + id);
        defaultParameterLookupShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name equals to DEFAULT_NAME
        defaultParameterLookupShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name equals to UPDATED_NAME
        defaultParameterLookupShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name not equals to DEFAULT_NAME
        defaultParameterLookupShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name not equals to UPDATED_NAME
        defaultParameterLookupShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name in DEFAULT_NAME or UPDATED_NAME
        defaultParameterLookupShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the parameterLookupList where name equals to UPDATED_NAME
        defaultParameterLookupShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name is not null
        defaultParameterLookupShouldBeFound("name.specified=true");

        // Get all the parameterLookupList where name is null
        defaultParameterLookupShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name is greater than or equal to DEFAULT_NAME
        defaultParameterLookupShouldBeFound("name.greaterThanOrEqual=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name is greater than or equal to UPDATED_NAME
        defaultParameterLookupShouldNotBeFound("name.greaterThanOrEqual=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name is less than or equal to DEFAULT_NAME
        defaultParameterLookupShouldBeFound("name.lessThanOrEqual=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name is less than or equal to SMALLER_NAME
        defaultParameterLookupShouldNotBeFound("name.lessThanOrEqual=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsLessThanSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name is less than DEFAULT_NAME
        defaultParameterLookupShouldNotBeFound("name.lessThan=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name is less than UPDATED_NAME
        defaultParameterLookupShouldBeFound("name.lessThan=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByNameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where name is greater than DEFAULT_NAME
        defaultParameterLookupShouldNotBeFound("name.greaterThan=" + DEFAULT_NAME);

        // Get all the parameterLookupList where name is greater than SMALLER_NAME
        defaultParameterLookupShouldBeFound("name.greaterThan=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where type equals to DEFAULT_TYPE
        defaultParameterLookupShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the parameterLookupList where type equals to UPDATED_TYPE
        defaultParameterLookupShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where type not equals to DEFAULT_TYPE
        defaultParameterLookupShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the parameterLookupList where type not equals to UPDATED_TYPE
        defaultParameterLookupShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultParameterLookupShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the parameterLookupList where type equals to UPDATED_TYPE
        defaultParameterLookupShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where type is not null
        defaultParameterLookupShouldBeFound("type.specified=true");

        // Get all the parameterLookupList where type is null
        defaultParameterLookupShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified is not null
        defaultParameterLookupShouldBeFound("lastModified.specified=true");

        // Get all the parameterLookupList where lastModified is null
        defaultParameterLookupShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified contains UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultParameterLookupShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the parameterLookupList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultParameterLookupShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy is not null
        defaultParameterLookupShouldBeFound("lastModifiedBy.specified=true");

        // Get all the parameterLookupList where lastModifiedBy is null
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        // Get all the parameterLookupList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultParameterLookupShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the parameterLookupList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultParameterLookupShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllParameterLookupsByVehicleDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);
        VehicleDetails vehicleDetails;
        if (TestUtil.findAll(em, VehicleDetails.class).isEmpty()) {
            vehicleDetails = VehicleDetailsResourceIT.createEntity(em);
            em.persist(vehicleDetails);
            em.flush();
        } else {
            vehicleDetails = TestUtil.findAll(em, VehicleDetails.class).get(0);
        }
        em.persist(vehicleDetails);
        em.flush();
        parameterLookup.setVehicleDetails(vehicleDetails);
        parameterLookupRepository.saveAndFlush(parameterLookup);
        Long vehicleDetailsId = vehicleDetails.getId();

        // Get all the parameterLookupList where vehicleDetails equals to vehicleDetailsId
        defaultParameterLookupShouldBeFound("vehicleDetailsId.equals=" + vehicleDetailsId);

        // Get all the parameterLookupList where vehicleDetails equals to (vehicleDetailsId + 1)
        defaultParameterLookupShouldNotBeFound("vehicleDetailsId.equals=" + (vehicleDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParameterLookupShouldBeFound(String filter) throws Exception {
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parameterLookup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParameterLookupShouldNotBeFound(String filter) throws Exception {
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParameterLookupMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingParameterLookup() throws Exception {
        // Get the parameterLookup
        restParameterLookupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup
        ParameterLookup updatedParameterLookup = parameterLookupRepository.findById(parameterLookup.getId()).get();
        // Disconnect from session so that the updates on updatedParameterLookup are not directly saved in db
        em.detach(updatedParameterLookup);
        updatedParameterLookup
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(updatedParameterLookup);

        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParameterLookup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParameterLookupWithPatch() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup using partial update
        ParameterLookup partialUpdatedParameterLookup = new ParameterLookup();
        partialUpdatedParameterLookup.setId(parameterLookup.getId());

        partialUpdatedParameterLookup.lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameterLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameterLookup))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParameterLookup.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateParameterLookupWithPatch() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();

        // Update the parameterLookup using partial update
        ParameterLookup partialUpdatedParameterLookup = new ParameterLookup();
        partialUpdatedParameterLookup.setId(parameterLookup.getId());

        partialUpdatedParameterLookup
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParameterLookup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParameterLookup))
            )
            .andExpect(status().isOk());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
        ParameterLookup testParameterLookup = parameterLookupList.get(parameterLookupList.size() - 1);
        assertThat(testParameterLookup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParameterLookup.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testParameterLookup.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testParameterLookup.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parameterLookupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParameterLookup() throws Exception {
        int databaseSizeBeforeUpdate = parameterLookupRepository.findAll().size();
        parameterLookup.setId(count.incrementAndGet());

        // Create the ParameterLookup
        ParameterLookupDTO parameterLookupDTO = parameterLookupMapper.toDto(parameterLookup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParameterLookupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parameterLookupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParameterLookup in the database
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParameterLookup() throws Exception {
        // Initialize the database
        parameterLookupRepository.saveAndFlush(parameterLookup);

        int databaseSizeBeforeDelete = parameterLookupRepository.findAll().size();

        // Delete the parameterLookup
        restParameterLookupMockMvc
            .perform(delete(ENTITY_API_URL_ID, parameterLookup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParameterLookup> parameterLookupList = parameterLookupRepository.findAll();
        assertThat(parameterLookupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
