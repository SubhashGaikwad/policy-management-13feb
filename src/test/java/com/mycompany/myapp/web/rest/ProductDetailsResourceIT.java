package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductDetails;
import com.mycompany.myapp.domain.ProductType;
import com.mycompany.myapp.repository.ProductDetailsRepository;
import com.mycompany.myapp.service.criteria.ProductDetailsCriteria;
import com.mycompany.myapp.service.dto.ProductDetailsDTO;
import com.mycompany.myapp.service.mapper.ProductDetailsMapper;
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
 * Integration tests for the {@link ProductDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductDetailsResourceIT {

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURS = "AAAAAAAAAA";
    private static final String UPDATED_FEATURS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/product-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDetailsMockMvc;

    private ProductDetails productDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .details(DEFAULT_DETAILS)
            .featurs(DEFAULT_FEATURS)
            .activationDate(DEFAULT_ACTIVATION_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return productDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDetails createUpdatedEntity(EntityManager em) {
        ProductDetails productDetails = new ProductDetails()
            .details(UPDATED_DETAILS)
            .featurs(UPDATED_FEATURS)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return productDetails;
    }

    @BeforeEach
    public void initTest() {
        productDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createProductDetails() throws Exception {
        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();
        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);
        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testProductDetails.getFeaturs()).isEqualTo(DEFAULT_FEATURS);
        assertThat(testProductDetails.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testProductDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductDetailsWithExistingId() throws Exception {
        // Create the ProductDetails with an existing ID
        productDetails.setId(1L);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        int databaseSizeBeforeCreate = productDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkActivationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailsRepository.findAll().size();
        // set the field null
        productDetails.setActivationDate(null);

        // Create the ProductDetails, which fails.
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailsRepository.findAll().size();
        // set the field null
        productDetails.setLastModified(null);

        // Create the ProductDetails, which fails.
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = productDetailsRepository.findAll().size();
        // set the field null
        productDetails.setLastModifiedBy(null);

        // Create the ProductDetails, which fails.
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        restProductDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].featurs").value(hasItem(DEFAULT_FEATURS)))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get the productDetails
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, productDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDetails.getId().intValue()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.featurs").value(DEFAULT_FEATURS))
            .andExpect(jsonPath("$.activationDate").value(DEFAULT_ACTIVATION_DATE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getProductDetailsByIdFiltering() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        Long id = productDetails.getId();

        defaultProductDetailsShouldBeFound("id.equals=" + id);
        defaultProductDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultProductDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details equals to DEFAULT_DETAILS
        defaultProductDetailsShouldBeFound("details.equals=" + DEFAULT_DETAILS);

        // Get all the productDetailsList where details equals to UPDATED_DETAILS
        defaultProductDetailsShouldNotBeFound("details.equals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details not equals to DEFAULT_DETAILS
        defaultProductDetailsShouldNotBeFound("details.notEquals=" + DEFAULT_DETAILS);

        // Get all the productDetailsList where details not equals to UPDATED_DETAILS
        defaultProductDetailsShouldBeFound("details.notEquals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details in DEFAULT_DETAILS or UPDATED_DETAILS
        defaultProductDetailsShouldBeFound("details.in=" + DEFAULT_DETAILS + "," + UPDATED_DETAILS);

        // Get all the productDetailsList where details equals to UPDATED_DETAILS
        defaultProductDetailsShouldNotBeFound("details.in=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details is not null
        defaultProductDetailsShouldBeFound("details.specified=true");

        // Get all the productDetailsList where details is null
        defaultProductDetailsShouldNotBeFound("details.specified=false");
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details contains DEFAULT_DETAILS
        defaultProductDetailsShouldBeFound("details.contains=" + DEFAULT_DETAILS);

        // Get all the productDetailsList where details contains UPDATED_DETAILS
        defaultProductDetailsShouldNotBeFound("details.contains=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where details does not contain DEFAULT_DETAILS
        defaultProductDetailsShouldNotBeFound("details.doesNotContain=" + DEFAULT_DETAILS);

        // Get all the productDetailsList where details does not contain UPDATED_DETAILS
        defaultProductDetailsShouldBeFound("details.doesNotContain=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs equals to DEFAULT_FEATURS
        defaultProductDetailsShouldBeFound("featurs.equals=" + DEFAULT_FEATURS);

        // Get all the productDetailsList where featurs equals to UPDATED_FEATURS
        defaultProductDetailsShouldNotBeFound("featurs.equals=" + UPDATED_FEATURS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs not equals to DEFAULT_FEATURS
        defaultProductDetailsShouldNotBeFound("featurs.notEquals=" + DEFAULT_FEATURS);

        // Get all the productDetailsList where featurs not equals to UPDATED_FEATURS
        defaultProductDetailsShouldBeFound("featurs.notEquals=" + UPDATED_FEATURS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs in DEFAULT_FEATURS or UPDATED_FEATURS
        defaultProductDetailsShouldBeFound("featurs.in=" + DEFAULT_FEATURS + "," + UPDATED_FEATURS);

        // Get all the productDetailsList where featurs equals to UPDATED_FEATURS
        defaultProductDetailsShouldNotBeFound("featurs.in=" + UPDATED_FEATURS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs is not null
        defaultProductDetailsShouldBeFound("featurs.specified=true");

        // Get all the productDetailsList where featurs is null
        defaultProductDetailsShouldNotBeFound("featurs.specified=false");
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs contains DEFAULT_FEATURS
        defaultProductDetailsShouldBeFound("featurs.contains=" + DEFAULT_FEATURS);

        // Get all the productDetailsList where featurs contains UPDATED_FEATURS
        defaultProductDetailsShouldNotBeFound("featurs.contains=" + UPDATED_FEATURS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByFeatursNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where featurs does not contain DEFAULT_FEATURS
        defaultProductDetailsShouldNotBeFound("featurs.doesNotContain=" + DEFAULT_FEATURS);

        // Get all the productDetailsList where featurs does not contain UPDATED_FEATURS
        defaultProductDetailsShouldBeFound("featurs.doesNotContain=" + UPDATED_FEATURS);
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate equals to DEFAULT_ACTIVATION_DATE
        defaultProductDetailsShouldBeFound("activationDate.equals=" + DEFAULT_ACTIVATION_DATE);

        // Get all the productDetailsList where activationDate equals to UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldNotBeFound("activationDate.equals=" + UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate not equals to DEFAULT_ACTIVATION_DATE
        defaultProductDetailsShouldNotBeFound("activationDate.notEquals=" + DEFAULT_ACTIVATION_DATE);

        // Get all the productDetailsList where activationDate not equals to UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldBeFound("activationDate.notEquals=" + UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate in DEFAULT_ACTIVATION_DATE or UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldBeFound("activationDate.in=" + DEFAULT_ACTIVATION_DATE + "," + UPDATED_ACTIVATION_DATE);

        // Get all the productDetailsList where activationDate equals to UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldNotBeFound("activationDate.in=" + UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate is not null
        defaultProductDetailsShouldBeFound("activationDate.specified=true");

        // Get all the productDetailsList where activationDate is null
        defaultProductDetailsShouldNotBeFound("activationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate contains DEFAULT_ACTIVATION_DATE
        defaultProductDetailsShouldBeFound("activationDate.contains=" + DEFAULT_ACTIVATION_DATE);

        // Get all the productDetailsList where activationDate contains UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldNotBeFound("activationDate.contains=" + UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductDetailsByActivationDateNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where activationDate does not contain DEFAULT_ACTIVATION_DATE
        defaultProductDetailsShouldNotBeFound("activationDate.doesNotContain=" + DEFAULT_ACTIVATION_DATE);

        // Get all the productDetailsList where activationDate does not contain UPDATED_ACTIVATION_DATE
        defaultProductDetailsShouldBeFound("activationDate.doesNotContain=" + UPDATED_ACTIVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultProductDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified is not null
        defaultProductDetailsShouldBeFound("lastModified.specified=true");

        // Get all the productDetailsList where lastModified is null
        defaultProductDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultProductDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the productDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultProductDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the productDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultProductDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy is not null
        defaultProductDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productDetailsList where lastModifiedBy is null
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        // Get all the productDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductDetailsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);
        ProductType productType;
        if (TestUtil.findAll(em, ProductType.class).isEmpty()) {
            productType = ProductTypeResourceIT.createEntity(em);
            em.persist(productType);
            em.flush();
        } else {
            productType = TestUtil.findAll(em, ProductType.class).get(0);
        }
        em.persist(productType);
        em.flush();
        productDetails.setProductType(productType);
        productDetailsRepository.saveAndFlush(productDetails);
        Long productTypeId = productType.getId();

        // Get all the productDetailsList where productType equals to productTypeId
        defaultProductDetailsShouldBeFound("productTypeId.equals=" + productTypeId);

        // Get all the productDetailsList where productType equals to (productTypeId + 1)
        defaultProductDetailsShouldNotBeFound("productTypeId.equals=" + (productTypeId + 1));
    }

    @Test
    @Transactional
    void getAllProductDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);
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
        productDetails.setProduct(product);
        product.setProductDetails(productDetails);
        productDetailsRepository.saveAndFlush(productDetails);
        Long productId = product.getId();

        // Get all the productDetailsList where product equals to productId
        defaultProductDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the productDetailsList where product equals to (productId + 1)
        defaultProductDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductDetailsShouldBeFound(String filter) throws Exception {
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].featurs").value(hasItem(DEFAULT_FEATURS)))
            .andExpect(jsonPath("$.[*].activationDate").value(hasItem(DEFAULT_ACTIVATION_DATE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductDetailsShouldNotBeFound(String filter) throws Exception {
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductDetails() throws Exception {
        // Get the productDetails
        restProductDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails
        ProductDetails updatedProductDetails = productDetailsRepository.findById(productDetails.getId()).get();
        // Disconnect from session so that the updates on updatedProductDetails are not directly saved in db
        em.detach(updatedProductDetails);
        updatedProductDetails
            .details(UPDATED_DETAILS)
            .featurs(UPDATED_FEATURS)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(updatedProductDetails);

        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProductDetails.getFeaturs()).isEqualTo(UPDATED_FEATURS);
        assertThat(testProductDetails.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testProductDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductDetailsWithPatch() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails using partial update
        ProductDetails partialUpdatedProductDetails = new ProductDetails();
        partialUpdatedProductDetails.setId(productDetails.getId());

        partialUpdatedProductDetails.details(UPDATED_DETAILS);

        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetails))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProductDetails.getFeaturs()).isEqualTo(DEFAULT_FEATURS);
        assertThat(testProductDetails.getActivationDate()).isEqualTo(DEFAULT_ACTIVATION_DATE);
        assertThat(testProductDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductDetailsWithPatch() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();

        // Update the productDetails using partial update
        ProductDetails partialUpdatedProductDetails = new ProductDetails();
        partialUpdatedProductDetails.setId(productDetails.getId());

        partialUpdatedProductDetails
            .details(UPDATED_DETAILS)
            .featurs(UPDATED_FEATURS)
            .activationDate(UPDATED_ACTIVATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDetails))
            )
            .andExpect(status().isOk());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
        ProductDetails testProductDetails = productDetailsList.get(productDetailsList.size() - 1);
        assertThat(testProductDetails.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testProductDetails.getFeaturs()).isEqualTo(UPDATED_FEATURS);
        assertThat(testProductDetails.getActivationDate()).isEqualTo(UPDATED_ACTIVATION_DATE);
        assertThat(testProductDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductDetails() throws Exception {
        int databaseSizeBeforeUpdate = productDetailsRepository.findAll().size();
        productDetails.setId(count.incrementAndGet());

        // Create the ProductDetails
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.toDto(productDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDetails in the database
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductDetails() throws Exception {
        // Initialize the database
        productDetailsRepository.saveAndFlush(productDetails);

        int databaseSizeBeforeDelete = productDetailsRepository.findAll().size();

        // Delete the productDetails
        restProductDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, productDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDetails> productDetailsList = productDetailsRepository.findAll();
        assertThat(productDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
