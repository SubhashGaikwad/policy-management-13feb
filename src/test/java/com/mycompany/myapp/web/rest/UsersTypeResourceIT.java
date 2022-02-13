package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.domain.UsersType;
import com.mycompany.myapp.repository.UsersTypeRepository;
import com.mycompany.myapp.service.criteria.UsersTypeCriteria;
import com.mycompany.myapp.service.dto.UsersTypeDTO;
import com.mycompany.myapp.service.mapper.UsersTypeMapper;
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
 * Integration tests for the {@link UsersTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UsersTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/users-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsersTypeRepository usersTypeRepository;

    @Autowired
    private UsersTypeMapper usersTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsersTypeMockMvc;

    private UsersType usersType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersType createEntity(EntityManager em) {
        UsersType usersType = new UsersType()
            .name(DEFAULT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return usersType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UsersType createUpdatedEntity(EntityManager em) {
        UsersType usersType = new UsersType()
            .name(UPDATED_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return usersType;
    }

    @BeforeEach
    public void initTest() {
        usersType = createEntity(em);
    }

    @Test
    @Transactional
    void createUsersType() throws Exception {
        int databaseSizeBeforeCreate = usersTypeRepository.findAll().size();
        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);
        restUsersTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UsersType testUsersType = usersTypeList.get(usersTypeList.size() - 1);
        assertThat(testUsersType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsersType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testUsersType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createUsersTypeWithExistingId() throws Exception {
        // Create the UsersType with an existing ID
        usersType.setId(1L);
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        int databaseSizeBeforeCreate = usersTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersTypeRepository.findAll().size();
        // set the field null
        usersType.setLastModified(null);

        // Create the UsersType, which fails.
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        restUsersTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersTypeDTO)))
            .andExpect(status().isBadRequest());

        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = usersTypeRepository.findAll().size();
        // set the field null
        usersType.setLastModifiedBy(null);

        // Create the UsersType, which fails.
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        restUsersTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersTypeDTO)))
            .andExpect(status().isBadRequest());

        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsersTypes() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getUsersType() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get the usersType
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, usersType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usersType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getUsersTypesByIdFiltering() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        Long id = usersType.getId();

        defaultUsersTypeShouldBeFound("id.equals=" + id);
        defaultUsersTypeShouldNotBeFound("id.notEquals=" + id);

        defaultUsersTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsersTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultUsersTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsersTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name equals to DEFAULT_NAME
        defaultUsersTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the usersTypeList where name equals to UPDATED_NAME
        defaultUsersTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name not equals to DEFAULT_NAME
        defaultUsersTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the usersTypeList where name not equals to UPDATED_NAME
        defaultUsersTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUsersTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the usersTypeList where name equals to UPDATED_NAME
        defaultUsersTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name is not null
        defaultUsersTypeShouldBeFound("name.specified=true");

        // Get all the usersTypeList where name is null
        defaultUsersTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name contains DEFAULT_NAME
        defaultUsersTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the usersTypeList where name contains UPDATED_NAME
        defaultUsersTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUsersTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where name does not contain DEFAULT_NAME
        defaultUsersTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the usersTypeList where name does not contain UPDATED_NAME
        defaultUsersTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultUsersTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the usersTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultUsersTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the usersTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the usersTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified is not null
        defaultUsersTypeShouldBeFound("lastModified.specified=true");

        // Get all the usersTypeList where lastModified is null
        defaultUsersTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultUsersTypeShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the usersTypeList where lastModified contains UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultUsersTypeShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the usersTypeList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultUsersTypeShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultUsersTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the usersTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the usersTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the usersTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy is not null
        defaultUsersTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the usersTypeList where lastModifiedBy is null
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultUsersTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the usersTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUsersTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        // Get all the usersTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultUsersTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the usersTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultUsersTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUsersTypesByPolicyUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);
        PolicyUsers policyUsers;
        if (TestUtil.findAll(em, PolicyUsers.class).isEmpty()) {
            policyUsers = PolicyUsersResourceIT.createEntity(em);
            em.persist(policyUsers);
            em.flush();
        } else {
            policyUsers = TestUtil.findAll(em, PolicyUsers.class).get(0);
        }
        em.persist(policyUsers);
        em.flush();
        usersType.setPolicyUsers(policyUsers);
        policyUsers.setUsersType(usersType);
        usersTypeRepository.saveAndFlush(usersType);
        Long policyUsersId = policyUsers.getId();

        // Get all the usersTypeList where policyUsers equals to policyUsersId
        defaultUsersTypeShouldBeFound("policyUsersId.equals=" + policyUsersId);

        // Get all the usersTypeList where policyUsers equals to (policyUsersId + 1)
        defaultUsersTypeShouldNotBeFound("policyUsersId.equals=" + (policyUsersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsersTypeShouldBeFound(String filter) throws Exception {
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usersType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsersTypeShouldNotBeFound(String filter) throws Exception {
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsersTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsersType() throws Exception {
        // Get the usersType
        restUsersTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsersType() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();

        // Update the usersType
        UsersType updatedUsersType = usersTypeRepository.findById(usersType.getId()).get();
        // Disconnect from session so that the updates on updatedUsersType are not directly saved in db
        em.detach(updatedUsersType);
        updatedUsersType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(updatedUsersType);

        restUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usersTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
        UsersType testUsersType = usersTypeList.get(usersTypeList.size() - 1);
        assertThat(testUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsersType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testUsersType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usersTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usersTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsersTypeWithPatch() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();

        // Update the usersType using partial update
        UsersType partialUpdatedUsersType = new UsersType();
        partialUpdatedUsersType.setId(usersType.getId());

        partialUpdatedUsersType.name(UPDATED_NAME);

        restUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsersType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsersType))
            )
            .andExpect(status().isOk());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
        UsersType testUsersType = usersTypeList.get(usersTypeList.size() - 1);
        assertThat(testUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsersType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testUsersType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateUsersTypeWithPatch() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();

        // Update the usersType using partial update
        UsersType partialUpdatedUsersType = new UsersType();
        partialUpdatedUsersType.setId(usersType.getId());

        partialUpdatedUsersType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsersType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsersType))
            )
            .andExpect(status().isOk());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
        UsersType testUsersType = usersTypeList.get(usersTypeList.size() - 1);
        assertThat(testUsersType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsersType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testUsersType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usersTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsersType() throws Exception {
        int databaseSizeBeforeUpdate = usersTypeRepository.findAll().size();
        usersType.setId(count.incrementAndGet());

        // Create the UsersType
        UsersTypeDTO usersTypeDTO = usersTypeMapper.toDto(usersType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsersTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usersTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UsersType in the database
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsersType() throws Exception {
        // Initialize the database
        usersTypeRepository.saveAndFlush(usersType);

        int databaseSizeBeforeDelete = usersTypeRepository.findAll().size();

        // Delete the usersType
        restUsersTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, usersType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UsersType> usersTypeList = usersTypeRepository.findAll();
        assertThat(usersTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
