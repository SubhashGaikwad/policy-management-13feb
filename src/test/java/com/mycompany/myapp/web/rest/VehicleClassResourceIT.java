package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.domain.VehicleClass;
import com.mycompany.myapp.repository.VehicleClassRepository;
import com.mycompany.myapp.service.criteria.VehicleClassCriteria;
import com.mycompany.myapp.service.dto.VehicleClassDTO;
import com.mycompany.myapp.service.mapper.VehicleClassMapper;
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
 * Integration tests for the {@link VehicleClassResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleClassResourceIT {

    private static final Long DEFAULT_NAME = 1L;
    private static final Long UPDATED_NAME = 2L;
    private static final Long SMALLER_NAME = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleClassRepository vehicleClassRepository;

    @Autowired
    private VehicleClassMapper vehicleClassMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleClassMockMvc;

    private VehicleClass vehicleClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleClass createEntity(EntityManager em) {
        VehicleClass vehicleClass = new VehicleClass()
            .name(DEFAULT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return vehicleClass;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleClass createUpdatedEntity(EntityManager em) {
        VehicleClass vehicleClass = new VehicleClass()
            .name(UPDATED_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return vehicleClass;
    }

    @BeforeEach
    public void initTest() {
        vehicleClass = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleClass() throws Exception {
        int databaseSizeBeforeCreate = vehicleClassRepository.findAll().size();
        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);
        restVehicleClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleClass.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVehicleClass.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createVehicleClassWithExistingId() throws Exception {
        // Create the VehicleClass with an existing ID
        vehicleClass.setId(1L);
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        int databaseSizeBeforeCreate = vehicleClassRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleClassRepository.findAll().size();
        // set the field null
        vehicleClass.setLastModified(null);

        // Create the VehicleClass, which fails.
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        restVehicleClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleClassRepository.findAll().size();
        // set the field null
        vehicleClass.setLastModifiedBy(null);

        // Create the VehicleClass, which fails.
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        restVehicleClassMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleClasses() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get the vehicleClass
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getVehicleClassesByIdFiltering() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        Long id = vehicleClass.getId();

        defaultVehicleClassShouldBeFound("id.equals=" + id);
        defaultVehicleClassShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleClassShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleClassShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleClassShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleClassShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name equals to DEFAULT_NAME
        defaultVehicleClassShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name equals to UPDATED_NAME
        defaultVehicleClassShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name not equals to DEFAULT_NAME
        defaultVehicleClassShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name not equals to UPDATED_NAME
        defaultVehicleClassShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVehicleClassShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vehicleClassList where name equals to UPDATED_NAME
        defaultVehicleClassShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name is not null
        defaultVehicleClassShouldBeFound("name.specified=true");

        // Get all the vehicleClassList where name is null
        defaultVehicleClassShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name is greater than or equal to DEFAULT_NAME
        defaultVehicleClassShouldBeFound("name.greaterThanOrEqual=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name is greater than or equal to UPDATED_NAME
        defaultVehicleClassShouldNotBeFound("name.greaterThanOrEqual=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name is less than or equal to DEFAULT_NAME
        defaultVehicleClassShouldBeFound("name.lessThanOrEqual=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name is less than or equal to SMALLER_NAME
        defaultVehicleClassShouldNotBeFound("name.lessThanOrEqual=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name is less than DEFAULT_NAME
        defaultVehicleClassShouldNotBeFound("name.lessThan=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name is less than UPDATED_NAME
        defaultVehicleClassShouldBeFound("name.lessThan=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByNameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where name is greater than DEFAULT_NAME
        defaultVehicleClassShouldNotBeFound("name.greaterThan=" + DEFAULT_NAME);

        // Get all the vehicleClassList where name is greater than SMALLER_NAME
        defaultVehicleClassShouldBeFound("name.greaterThan=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultVehicleClassShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleClassList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultVehicleClassShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleClassList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the vehicleClassList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified is not null
        defaultVehicleClassShouldBeFound("lastModified.specified=true");

        // Get all the vehicleClassList where lastModified is null
        defaultVehicleClassShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultVehicleClassShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleClassList where lastModified contains UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultVehicleClassShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleClassList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultVehicleClassShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultVehicleClassShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleClassList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleClassList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the vehicleClassList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy is not null
        defaultVehicleClassShouldBeFound("lastModifiedBy.specified=true");

        // Get all the vehicleClassList where lastModifiedBy is null
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultVehicleClassShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleClassList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        // Get all the vehicleClassList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultVehicleClassShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleClassList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultVehicleClassShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleClassesByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);
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
        vehicleClass.setPolicy(policy);
        policy.setVehicleClass(vehicleClass);
        vehicleClassRepository.saveAndFlush(vehicleClass);
        Long policyId = policy.getId();

        // Get all the vehicleClassList where policy equals to policyId
        defaultVehicleClassShouldBeFound("policyId.equals=" + policyId);

        // Get all the vehicleClassList where policy equals to (policyId + 1)
        defaultVehicleClassShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleClassShouldBeFound(String filter) throws Exception {
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleClassShouldNotBeFound(String filter) throws Exception {
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleClassMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleClass() throws Exception {
        // Get the vehicleClass
        restVehicleClassMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();

        // Update the vehicleClass
        VehicleClass updatedVehicleClass = vehicleClassRepository.findById(vehicleClass.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleClass are not directly saved in db
        em.detach(updatedVehicleClass);
        updatedVehicleClass.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(updatedVehicleClass);

        restVehicleClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleClassDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleClass.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVehicleClass.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleClassDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleClassWithPatch() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();

        // Update the vehicleClass using partial update
        VehicleClass partialUpdatedVehicleClass = new VehicleClass();
        partialUpdatedVehicleClass.setId(vehicleClass.getId());

        partialUpdatedVehicleClass.lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restVehicleClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleClass))
            )
            .andExpect(status().isOk());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleClass.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVehicleClass.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateVehicleClassWithPatch() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();

        // Update the vehicleClass using partial update
        VehicleClass partialUpdatedVehicleClass = new VehicleClass();
        partialUpdatedVehicleClass.setId(vehicleClass.getId());

        partialUpdatedVehicleClass.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restVehicleClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleClass))
            )
            .andExpect(status().isOk());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
        VehicleClass testVehicleClass = vehicleClassList.get(vehicleClassList.size() - 1);
        assertThat(testVehicleClass.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleClass.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVehicleClass.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleClassDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleClass() throws Exception {
        int databaseSizeBeforeUpdate = vehicleClassRepository.findAll().size();
        vehicleClass.setId(count.incrementAndGet());

        // Create the VehicleClass
        VehicleClassDTO vehicleClassDTO = vehicleClassMapper.toDto(vehicleClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleClassMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleClassDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleClass in the database
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleClass() throws Exception {
        // Initialize the database
        vehicleClassRepository.saveAndFlush(vehicleClass);

        int databaseSizeBeforeDelete = vehicleClassRepository.findAll().size();

        // Delete the vehicleClass
        restVehicleClassMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleClass.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleClass> vehicleClassList = vehicleClassRepository.findAll();
        assertThat(vehicleClassList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
