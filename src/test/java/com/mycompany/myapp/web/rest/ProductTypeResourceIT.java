package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductDetails;
import com.mycompany.myapp.domain.ProductType;
import com.mycompany.myapp.repository.ProductTypeRepository;
import com.mycompany.myapp.service.criteria.ProductTypeCriteria;
import com.mycompany.myapp.service.dto.ProductTypeDTO;
import com.mycompany.myapp.service.mapper.ProductTypeMapper;
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
 * Integration tests for the {@link ProductTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTypeMockMvc;

    private ProductType productType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductType createEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .name(DEFAULT_NAME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return productType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductType createUpdatedEntity(EntityManager em) {
        ProductType productType = new ProductType()
            .name(UPDATED_NAME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return productType;
    }

    @BeforeEach
    public void initTest() {
        productType = createEntity(em);
    }

    @Test
    @Transactional
    void createProductType() throws Exception {
        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();
        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);
        restProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductTypeWithExistingId() throws Exception {
        // Create the ProductType with an existing ID
        productType.setId(1L);
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        int databaseSizeBeforeCreate = productTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTypeRepository.findAll().size();
        // set the field null
        productType.setLastModified(null);

        // Create the ProductType, which fails.
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        restProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = productTypeRepository.findAll().size();
        // set the field null
        productType.setLastModifiedBy(null);

        // Create the ProductType, which fails.
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        restProductTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductTypes() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get the productType
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, productType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getProductTypesByIdFiltering() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        Long id = productType.getId();

        defaultProductTypeShouldBeFound("id.equals=" + id);
        defaultProductTypeShouldNotBeFound("id.notEquals=" + id);

        defaultProductTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name equals to DEFAULT_NAME
        defaultProductTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productTypeList where name equals to UPDATED_NAME
        defaultProductTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name not equals to DEFAULT_NAME
        defaultProductTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productTypeList where name not equals to UPDATED_NAME
        defaultProductTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productTypeList where name equals to UPDATED_NAME
        defaultProductTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name is not null
        defaultProductTypeShouldBeFound("name.specified=true");

        // Get all the productTypeList where name is null
        defaultProductTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name contains DEFAULT_NAME
        defaultProductTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productTypeList where name contains UPDATED_NAME
        defaultProductTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where name does not contain DEFAULT_NAME
        defaultProductTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productTypeList where name does not contain UPDATED_NAME
        defaultProductTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultProductTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultProductTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified is not null
        defaultProductTypeShouldBeFound("lastModified.specified=true");

        // Get all the productTypeList where lastModified is null
        defaultProductTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultProductTypeShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTypeList where lastModified contains UPDATED_LAST_MODIFIED
        defaultProductTypeShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultProductTypeShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the productTypeList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultProductTypeShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy is not null
        defaultProductTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productTypeList where lastModifiedBy is null
        defaultProductTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        // Get all the productTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTypesByProductDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);
        ProductDetails productDetails;
        if (TestUtil.findAll(em, ProductDetails.class).isEmpty()) {
            productDetails = ProductDetailsResourceIT.createEntity(em);
            em.persist(productDetails);
            em.flush();
        } else {
            productDetails = TestUtil.findAll(em, ProductDetails.class).get(0);
        }
        em.persist(productDetails);
        em.flush();
        productType.setProductDetails(productDetails);
        productDetails.setProductType(productType);
        productTypeRepository.saveAndFlush(productType);
        Long productDetailsId = productDetails.getId();

        // Get all the productTypeList where productDetails equals to productDetailsId
        defaultProductTypeShouldBeFound("productDetailsId.equals=" + productDetailsId);

        // Get all the productTypeList where productDetails equals to (productDetailsId + 1)
        defaultProductTypeShouldNotBeFound("productDetailsId.equals=" + (productDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTypeShouldBeFound(String filter) throws Exception {
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTypeShouldNotBeFound(String filter) throws Exception {
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductType() throws Exception {
        // Get the productType
        restProductTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType
        ProductType updatedProductType = productTypeRepository.findById(productType.getId()).get();
        // Disconnect from session so that the updates on updatedProductType are not directly saved in db
        em.detach(updatedProductType);
        updatedProductType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(updatedProductType);

        restProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTypeWithPatch() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType using partial update
        ProductType partialUpdatedProductType = new ProductType();
        partialUpdatedProductType.setId(productType.getId());

        partialUpdatedProductType.name(UPDATED_NAME);

        restProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductType))
            )
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductTypeWithPatch() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();

        // Update the productType using partial update
        ProductType partialUpdatedProductType = new ProductType();
        partialUpdatedProductType.setId(productType.getId());

        partialUpdatedProductType.name(UPDATED_NAME).lastModified(UPDATED_LAST_MODIFIED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductType))
            )
            .andExpect(status().isOk());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductType testProductType = productTypeList.get(productTypeList.size() - 1);
        assertThat(testProductType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductType() throws Exception {
        int databaseSizeBeforeUpdate = productTypeRepository.findAll().size();
        productType.setId(count.incrementAndGet());

        // Create the ProductType
        ProductTypeDTO productTypeDTO = productTypeMapper.toDto(productType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductType in the database
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductType() throws Exception {
        // Initialize the database
        productTypeRepository.saveAndFlush(productType);

        int databaseSizeBeforeDelete = productTypeRepository.findAll().size();

        // Delete the productType
        restProductTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, productType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductType> productTypeList = productTypeRepository.findAll();
        assertThat(productTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
