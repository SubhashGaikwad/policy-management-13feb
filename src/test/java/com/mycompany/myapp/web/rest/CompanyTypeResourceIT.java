package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.CompanyType;
import com.mycompany.myapp.repository.CompanyTypeRepository;
import com.mycompany.myapp.service.criteria.CompanyTypeCriteria;
import com.mycompany.myapp.service.dto.CompanyTypeDTO;
import com.mycompany.myapp.service.mapper.CompanyTypeMapper;
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
 * Integration tests for the {@link CompanyTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private CompanyTypeMapper companyTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyTypeMockMvc;

    private CompanyType companyType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyType createEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .name(DEFAULT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return companyType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyType createUpdatedEntity(EntityManager em) {
        CompanyType companyType = new CompanyType()
            .name(UPDATED_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return companyType;
    }

    @BeforeEach
    public void initTest() {
        companyType = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyType() throws Exception {
        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();
        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);
        restCompanyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCompanyType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createCompanyTypeWithExistingId() throws Exception {
        // Create the CompanyType with an existing ID
        companyType.setId(1L);
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        int databaseSizeBeforeCreate = companyTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyTypeRepository.findAll().size();
        // set the field null
        companyType.setLastModified(null);

        // Create the CompanyType, which fails.
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        restCompanyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyTypeRepository.findAll().size();
        // set the field null
        companyType.setLastModifiedBy(null);

        // Create the CompanyType, which fails.
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        restCompanyTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyTypes() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get the companyType
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, companyType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getCompanyTypesByIdFiltering() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        Long id = companyType.getId();

        defaultCompanyTypeShouldBeFound("id.equals=" + id);
        defaultCompanyTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name equals to DEFAULT_NAME
        defaultCompanyTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyTypeList where name equals to UPDATED_NAME
        defaultCompanyTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name not equals to DEFAULT_NAME
        defaultCompanyTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyTypeList where name not equals to UPDATED_NAME
        defaultCompanyTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyTypeList where name equals to UPDATED_NAME
        defaultCompanyTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name is not null
        defaultCompanyTypeShouldBeFound("name.specified=true");

        // Get all the companyTypeList where name is null
        defaultCompanyTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name contains DEFAULT_NAME
        defaultCompanyTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyTypeList where name contains UPDATED_NAME
        defaultCompanyTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where name does not contain DEFAULT_NAME
        defaultCompanyTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyTypeList where name does not contain UPDATED_NAME
        defaultCompanyTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultCompanyTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultCompanyTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the companyTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified is not null
        defaultCompanyTypeShouldBeFound("lastModified.specified=true");

        // Get all the companyTypeList where lastModified is null
        defaultCompanyTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultCompanyTypeShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyTypeList where lastModified contains UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultCompanyTypeShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the companyTypeList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultCompanyTypeShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultCompanyTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the companyTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy is not null
        defaultCompanyTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the companyTypeList where lastModifiedBy is null
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultCompanyTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        // Get all the companyTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultCompanyTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the companyTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultCompanyTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllCompanyTypesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        em.persist(company);
        em.flush();
        companyType.setCompany(company);
        company.setCompanyType(companyType);
        companyTypeRepository.saveAndFlush(companyType);
        Long companyId = company.getId();

        // Get all the companyTypeList where company equals to companyId
        defaultCompanyTypeShouldBeFound("companyId.equals=" + companyId);

        // Get all the companyTypeList where company equals to (companyId + 1)
        defaultCompanyTypeShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyTypeShouldBeFound(String filter) throws Exception {
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyTypeShouldNotBeFound(String filter) throws Exception {
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCompanyType() throws Exception {
        // Get the companyType
        restCompanyTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType
        CompanyType updatedCompanyType = companyTypeRepository.findById(companyType.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyType are not directly saved in db
        em.detach(updatedCompanyType);
        updatedCompanyType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(updatedCompanyType);

        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompanyType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyTypeWithPatch() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType using partial update
        CompanyType partialUpdatedCompanyType = new CompanyType();
        partialUpdatedCompanyType.setId(companyType.getId());

        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyType))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testCompanyType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateCompanyTypeWithPatch() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();

        // Update the companyType using partial update
        CompanyType partialUpdatedCompanyType = new CompanyType();
        partialUpdatedCompanyType.setId(companyType.getId());

        partialUpdatedCompanyType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyType))
            )
            .andExpect(status().isOk());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
        CompanyType testCompanyType = companyTypeList.get(companyTypeList.size() - 1);
        assertThat(testCompanyType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testCompanyType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyType() throws Exception {
        int databaseSizeBeforeUpdate = companyTypeRepository.findAll().size();
        companyType.setId(count.incrementAndGet());

        // Create the CompanyType
        CompanyTypeDTO companyTypeDTO = companyTypeMapper.toDto(companyType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyType in the database
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyType() throws Exception {
        // Initialize the database
        companyTypeRepository.saveAndFlush(companyType);

        int databaseSizeBeforeDelete = companyTypeRepository.findAll().size();

        // Delete the companyType
        restCompanyTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyType> companyTypeList = companyTypeRepository.findAll();
        assertThat(companyTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
