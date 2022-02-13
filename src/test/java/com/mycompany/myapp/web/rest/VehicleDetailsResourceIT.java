package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ParameterLookup;
import com.mycompany.myapp.domain.VehicleDetails;
import com.mycompany.myapp.domain.enumeration.Zone;
import com.mycompany.myapp.repository.VehicleDetailsRepository;
import com.mycompany.myapp.service.criteria.VehicleDetailsCriteria;
import com.mycompany.myapp.service.dto.VehicleDetailsDTO;
import com.mycompany.myapp.service.mapper.VehicleDetailsMapper;
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
 * Integration tests for the {@link VehicleDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VehicleDetailsResourceIT {

    private static final Long DEFAULT_NAME = 1L;
    private static final Long UPDATED_NAME = 2L;
    private static final Long SMALLER_NAME = 1L - 1L;

    private static final String DEFAULT_INVOICE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_IDV = "AAAAAAAAAA";
    private static final String UPDATED_IDV = "BBBBBBBBBB";

    private static final String DEFAULT_ENGIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ENGIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CHASSIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_SEATING_CAPACITY = 1L;
    private static final Long UPDATED_SEATING_CAPACITY = 2L;
    private static final Long SMALLER_SEATING_CAPACITY = 1L - 1L;

    private static final Zone DEFAULT_ZONE = Zone.A;
    private static final Zone UPDATED_ZONE = Zone.B;

    private static final String DEFAULT_YEAR_OF_MANUFACTURING = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_OF_MANUFACTURING = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_DATE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vehicle-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VehicleDetailsRepository vehicleDetailsRepository;

    @Autowired
    private VehicleDetailsMapper vehicleDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVehicleDetailsMockMvc;

    private VehicleDetails vehicleDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleDetails createEntity(EntityManager em) {
        VehicleDetails vehicleDetails = new VehicleDetails()
            .name(DEFAULT_NAME)
            .invoiceValue(DEFAULT_INVOICE_VALUE)
            .idv(DEFAULT_IDV)
            .enginNumber(DEFAULT_ENGIN_NUMBER)
            .chassisNumber(DEFAULT_CHASSIS_NUMBER)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .seatingCapacity(DEFAULT_SEATING_CAPACITY)
            .zone(DEFAULT_ZONE)
            .yearOfManufacturing(DEFAULT_YEAR_OF_MANUFACTURING)
            .registrationDate(DEFAULT_REGISTRATION_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return vehicleDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleDetails createUpdatedEntity(EntityManager em) {
        VehicleDetails vehicleDetails = new VehicleDetails()
            .name(UPDATED_NAME)
            .invoiceValue(UPDATED_INVOICE_VALUE)
            .idv(UPDATED_IDV)
            .enginNumber(UPDATED_ENGIN_NUMBER)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .zone(UPDATED_ZONE)
            .yearOfManufacturing(UPDATED_YEAR_OF_MANUFACTURING)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return vehicleDetails;
    }

    @BeforeEach
    public void initTest() {
        vehicleDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createVehicleDetails() throws Exception {
        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();
        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);
        restVehicleDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleDetails.getInvoiceValue()).isEqualTo(DEFAULT_INVOICE_VALUE);
        assertThat(testVehicleDetails.getIdv()).isEqualTo(DEFAULT_IDV);
        assertThat(testVehicleDetails.getEnginNumber()).isEqualTo(DEFAULT_ENGIN_NUMBER);
        assertThat(testVehicleDetails.getChassisNumber()).isEqualTo(DEFAULT_CHASSIS_NUMBER);
        assertThat(testVehicleDetails.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testVehicleDetails.getSeatingCapacity()).isEqualTo(DEFAULT_SEATING_CAPACITY);
        assertThat(testVehicleDetails.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testVehicleDetails.getYearOfManufacturing()).isEqualTo(DEFAULT_YEAR_OF_MANUFACTURING);
        assertThat(testVehicleDetails.getRegistrationDate()).isEqualTo(DEFAULT_REGISTRATION_DATE);
        assertThat(testVehicleDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVehicleDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createVehicleDetailsWithExistingId() throws Exception {
        // Create the VehicleDetails with an existing ID
        vehicleDetails.setId(1L);
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        int databaseSizeBeforeCreate = vehicleDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setLastModified(null);

        // Create the VehicleDetails, which fails.
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        restVehicleDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleDetailsRepository.findAll().size();
        // set the field null
        vehicleDetails.setLastModifiedBy(null);

        // Create the VehicleDetails, which fails.
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        restVehicleDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].invoiceValue").value(hasItem(DEFAULT_INVOICE_VALUE)))
            .andExpect(jsonPath("$.[*].idv").value(hasItem(DEFAULT_IDV)))
            .andExpect(jsonPath("$.[*].enginNumber").value(hasItem(DEFAULT_ENGIN_NUMBER)))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].seatingCapacity").value(hasItem(DEFAULT_SEATING_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
            .andExpect(jsonPath("$.[*].yearOfManufacturing").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURING)))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get the vehicleDetails
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, vehicleDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.intValue()))
            .andExpect(jsonPath("$.invoiceValue").value(DEFAULT_INVOICE_VALUE))
            .andExpect(jsonPath("$.idv").value(DEFAULT_IDV))
            .andExpect(jsonPath("$.enginNumber").value(DEFAULT_ENGIN_NUMBER))
            .andExpect(jsonPath("$.chassisNumber").value(DEFAULT_CHASSIS_NUMBER))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.seatingCapacity").value(DEFAULT_SEATING_CAPACITY.intValue()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.toString()))
            .andExpect(jsonPath("$.yearOfManufacturing").value(DEFAULT_YEAR_OF_MANUFACTURING))
            .andExpect(jsonPath("$.registrationDate").value(DEFAULT_REGISTRATION_DATE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getVehicleDetailsByIdFiltering() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        Long id = vehicleDetails.getId();

        defaultVehicleDetailsShouldBeFound("id.equals=" + id);
        defaultVehicleDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultVehicleDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVehicleDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultVehicleDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVehicleDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name equals to DEFAULT_NAME
        defaultVehicleDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name equals to UPDATED_NAME
        defaultVehicleDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name not equals to DEFAULT_NAME
        defaultVehicleDetailsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name not equals to UPDATED_NAME
        defaultVehicleDetailsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultVehicleDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the vehicleDetailsList where name equals to UPDATED_NAME
        defaultVehicleDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name is not null
        defaultVehicleDetailsShouldBeFound("name.specified=true");

        // Get all the vehicleDetailsList where name is null
        defaultVehicleDetailsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name is greater than or equal to DEFAULT_NAME
        defaultVehicleDetailsShouldBeFound("name.greaterThanOrEqual=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name is greater than or equal to UPDATED_NAME
        defaultVehicleDetailsShouldNotBeFound("name.greaterThanOrEqual=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name is less than or equal to DEFAULT_NAME
        defaultVehicleDetailsShouldBeFound("name.lessThanOrEqual=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name is less than or equal to SMALLER_NAME
        defaultVehicleDetailsShouldNotBeFound("name.lessThanOrEqual=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name is less than DEFAULT_NAME
        defaultVehicleDetailsShouldNotBeFound("name.lessThan=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name is less than UPDATED_NAME
        defaultVehicleDetailsShouldBeFound("name.lessThan=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByNameIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where name is greater than DEFAULT_NAME
        defaultVehicleDetailsShouldNotBeFound("name.greaterThan=" + DEFAULT_NAME);

        // Get all the vehicleDetailsList where name is greater than SMALLER_NAME
        defaultVehicleDetailsShouldBeFound("name.greaterThan=" + SMALLER_NAME);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue equals to DEFAULT_INVOICE_VALUE
        defaultVehicleDetailsShouldBeFound("invoiceValue.equals=" + DEFAULT_INVOICE_VALUE);

        // Get all the vehicleDetailsList where invoiceValue equals to UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.equals=" + UPDATED_INVOICE_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue not equals to DEFAULT_INVOICE_VALUE
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.notEquals=" + DEFAULT_INVOICE_VALUE);

        // Get all the vehicleDetailsList where invoiceValue not equals to UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldBeFound("invoiceValue.notEquals=" + UPDATED_INVOICE_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue in DEFAULT_INVOICE_VALUE or UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldBeFound("invoiceValue.in=" + DEFAULT_INVOICE_VALUE + "," + UPDATED_INVOICE_VALUE);

        // Get all the vehicleDetailsList where invoiceValue equals to UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.in=" + UPDATED_INVOICE_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue is not null
        defaultVehicleDetailsShouldBeFound("invoiceValue.specified=true");

        // Get all the vehicleDetailsList where invoiceValue is null
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue contains DEFAULT_INVOICE_VALUE
        defaultVehicleDetailsShouldBeFound("invoiceValue.contains=" + DEFAULT_INVOICE_VALUE);

        // Get all the vehicleDetailsList where invoiceValue contains UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.contains=" + UPDATED_INVOICE_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByInvoiceValueNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where invoiceValue does not contain DEFAULT_INVOICE_VALUE
        defaultVehicleDetailsShouldNotBeFound("invoiceValue.doesNotContain=" + DEFAULT_INVOICE_VALUE);

        // Get all the vehicleDetailsList where invoiceValue does not contain UPDATED_INVOICE_VALUE
        defaultVehicleDetailsShouldBeFound("invoiceValue.doesNotContain=" + UPDATED_INVOICE_VALUE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv equals to DEFAULT_IDV
        defaultVehicleDetailsShouldBeFound("idv.equals=" + DEFAULT_IDV);

        // Get all the vehicleDetailsList where idv equals to UPDATED_IDV
        defaultVehicleDetailsShouldNotBeFound("idv.equals=" + UPDATED_IDV);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv not equals to DEFAULT_IDV
        defaultVehicleDetailsShouldNotBeFound("idv.notEquals=" + DEFAULT_IDV);

        // Get all the vehicleDetailsList where idv not equals to UPDATED_IDV
        defaultVehicleDetailsShouldBeFound("idv.notEquals=" + UPDATED_IDV);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv in DEFAULT_IDV or UPDATED_IDV
        defaultVehicleDetailsShouldBeFound("idv.in=" + DEFAULT_IDV + "," + UPDATED_IDV);

        // Get all the vehicleDetailsList where idv equals to UPDATED_IDV
        defaultVehicleDetailsShouldNotBeFound("idv.in=" + UPDATED_IDV);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv is not null
        defaultVehicleDetailsShouldBeFound("idv.specified=true");

        // Get all the vehicleDetailsList where idv is null
        defaultVehicleDetailsShouldNotBeFound("idv.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv contains DEFAULT_IDV
        defaultVehicleDetailsShouldBeFound("idv.contains=" + DEFAULT_IDV);

        // Get all the vehicleDetailsList where idv contains UPDATED_IDV
        defaultVehicleDetailsShouldNotBeFound("idv.contains=" + UPDATED_IDV);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByIdvNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where idv does not contain DEFAULT_IDV
        defaultVehicleDetailsShouldNotBeFound("idv.doesNotContain=" + DEFAULT_IDV);

        // Get all the vehicleDetailsList where idv does not contain UPDATED_IDV
        defaultVehicleDetailsShouldBeFound("idv.doesNotContain=" + UPDATED_IDV);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber equals to DEFAULT_ENGIN_NUMBER
        defaultVehicleDetailsShouldBeFound("enginNumber.equals=" + DEFAULT_ENGIN_NUMBER);

        // Get all the vehicleDetailsList where enginNumber equals to UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldNotBeFound("enginNumber.equals=" + UPDATED_ENGIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber not equals to DEFAULT_ENGIN_NUMBER
        defaultVehicleDetailsShouldNotBeFound("enginNumber.notEquals=" + DEFAULT_ENGIN_NUMBER);

        // Get all the vehicleDetailsList where enginNumber not equals to UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldBeFound("enginNumber.notEquals=" + UPDATED_ENGIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber in DEFAULT_ENGIN_NUMBER or UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldBeFound("enginNumber.in=" + DEFAULT_ENGIN_NUMBER + "," + UPDATED_ENGIN_NUMBER);

        // Get all the vehicleDetailsList where enginNumber equals to UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldNotBeFound("enginNumber.in=" + UPDATED_ENGIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber is not null
        defaultVehicleDetailsShouldBeFound("enginNumber.specified=true");

        // Get all the vehicleDetailsList where enginNumber is null
        defaultVehicleDetailsShouldNotBeFound("enginNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber contains DEFAULT_ENGIN_NUMBER
        defaultVehicleDetailsShouldBeFound("enginNumber.contains=" + DEFAULT_ENGIN_NUMBER);

        // Get all the vehicleDetailsList where enginNumber contains UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldNotBeFound("enginNumber.contains=" + UPDATED_ENGIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByEnginNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where enginNumber does not contain DEFAULT_ENGIN_NUMBER
        defaultVehicleDetailsShouldNotBeFound("enginNumber.doesNotContain=" + DEFAULT_ENGIN_NUMBER);

        // Get all the vehicleDetailsList where enginNumber does not contain UPDATED_ENGIN_NUMBER
        defaultVehicleDetailsShouldBeFound("enginNumber.doesNotContain=" + UPDATED_ENGIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber equals to DEFAULT_CHASSIS_NUMBER
        defaultVehicleDetailsShouldBeFound("chassisNumber.equals=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleDetailsList where chassisNumber equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.equals=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber not equals to DEFAULT_CHASSIS_NUMBER
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.notEquals=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleDetailsList where chassisNumber not equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldBeFound("chassisNumber.notEquals=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber in DEFAULT_CHASSIS_NUMBER or UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldBeFound("chassisNumber.in=" + DEFAULT_CHASSIS_NUMBER + "," + UPDATED_CHASSIS_NUMBER);

        // Get all the vehicleDetailsList where chassisNumber equals to UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.in=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber is not null
        defaultVehicleDetailsShouldBeFound("chassisNumber.specified=true");

        // Get all the vehicleDetailsList where chassisNumber is null
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber contains DEFAULT_CHASSIS_NUMBER
        defaultVehicleDetailsShouldBeFound("chassisNumber.contains=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleDetailsList where chassisNumber contains UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.contains=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByChassisNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where chassisNumber does not contain DEFAULT_CHASSIS_NUMBER
        defaultVehicleDetailsShouldNotBeFound("chassisNumber.doesNotContain=" + DEFAULT_CHASSIS_NUMBER);

        // Get all the vehicleDetailsList where chassisNumber does not contain UPDATED_CHASSIS_NUMBER
        defaultVehicleDetailsShouldBeFound("chassisNumber.doesNotContain=" + UPDATED_CHASSIS_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber equals to DEFAULT_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldBeFound("registrationNumber.equals=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the vehicleDetailsList where registrationNumber equals to UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.equals=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber not equals to DEFAULT_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.notEquals=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the vehicleDetailsList where registrationNumber not equals to UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldBeFound("registrationNumber.notEquals=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber in DEFAULT_REGISTRATION_NUMBER or UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldBeFound("registrationNumber.in=" + DEFAULT_REGISTRATION_NUMBER + "," + UPDATED_REGISTRATION_NUMBER);

        // Get all the vehicleDetailsList where registrationNumber equals to UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.in=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber is not null
        defaultVehicleDetailsShouldBeFound("registrationNumber.specified=true");

        // Get all the vehicleDetailsList where registrationNumber is null
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber contains DEFAULT_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldBeFound("registrationNumber.contains=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the vehicleDetailsList where registrationNumber contains UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.contains=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationNumber does not contain DEFAULT_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldNotBeFound("registrationNumber.doesNotContain=" + DEFAULT_REGISTRATION_NUMBER);

        // Get all the vehicleDetailsList where registrationNumber does not contain UPDATED_REGISTRATION_NUMBER
        defaultVehicleDetailsShouldBeFound("registrationNumber.doesNotContain=" + UPDATED_REGISTRATION_NUMBER);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity equals to DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.equals=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity equals to UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.equals=" + UPDATED_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity not equals to DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.notEquals=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity not equals to UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.notEquals=" + UPDATED_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity in DEFAULT_SEATING_CAPACITY or UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.in=" + DEFAULT_SEATING_CAPACITY + "," + UPDATED_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity equals to UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.in=" + UPDATED_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity is not null
        defaultVehicleDetailsShouldBeFound("seatingCapacity.specified=true");

        // Get all the vehicleDetailsList where seatingCapacity is null
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity is greater than or equal to DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.greaterThanOrEqual=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity is greater than or equal to UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.greaterThanOrEqual=" + UPDATED_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity is less than or equal to DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.lessThanOrEqual=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity is less than or equal to SMALLER_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.lessThanOrEqual=" + SMALLER_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity is less than DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.lessThan=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity is less than UPDATED_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.lessThan=" + UPDATED_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsBySeatingCapacityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where seatingCapacity is greater than DEFAULT_SEATING_CAPACITY
        defaultVehicleDetailsShouldNotBeFound("seatingCapacity.greaterThan=" + DEFAULT_SEATING_CAPACITY);

        // Get all the vehicleDetailsList where seatingCapacity is greater than SMALLER_SEATING_CAPACITY
        defaultVehicleDetailsShouldBeFound("seatingCapacity.greaterThan=" + SMALLER_SEATING_CAPACITY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where zone equals to DEFAULT_ZONE
        defaultVehicleDetailsShouldBeFound("zone.equals=" + DEFAULT_ZONE);

        // Get all the vehicleDetailsList where zone equals to UPDATED_ZONE
        defaultVehicleDetailsShouldNotBeFound("zone.equals=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByZoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where zone not equals to DEFAULT_ZONE
        defaultVehicleDetailsShouldNotBeFound("zone.notEquals=" + DEFAULT_ZONE);

        // Get all the vehicleDetailsList where zone not equals to UPDATED_ZONE
        defaultVehicleDetailsShouldBeFound("zone.notEquals=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByZoneIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where zone in DEFAULT_ZONE or UPDATED_ZONE
        defaultVehicleDetailsShouldBeFound("zone.in=" + DEFAULT_ZONE + "," + UPDATED_ZONE);

        // Get all the vehicleDetailsList where zone equals to UPDATED_ZONE
        defaultVehicleDetailsShouldNotBeFound("zone.in=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByZoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where zone is not null
        defaultVehicleDetailsShouldBeFound("zone.specified=true");

        // Get all the vehicleDetailsList where zone is null
        defaultVehicleDetailsShouldNotBeFound("zone.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing equals to DEFAULT_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.equals=" + DEFAULT_YEAR_OF_MANUFACTURING);

        // Get all the vehicleDetailsList where yearOfManufacturing equals to UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.equals=" + UPDATED_YEAR_OF_MANUFACTURING);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing not equals to DEFAULT_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.notEquals=" + DEFAULT_YEAR_OF_MANUFACTURING);

        // Get all the vehicleDetailsList where yearOfManufacturing not equals to UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.notEquals=" + UPDATED_YEAR_OF_MANUFACTURING);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing in DEFAULT_YEAR_OF_MANUFACTURING or UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.in=" + DEFAULT_YEAR_OF_MANUFACTURING + "," + UPDATED_YEAR_OF_MANUFACTURING);

        // Get all the vehicleDetailsList where yearOfManufacturing equals to UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.in=" + UPDATED_YEAR_OF_MANUFACTURING);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing is not null
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.specified=true");

        // Get all the vehicleDetailsList where yearOfManufacturing is null
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing contains DEFAULT_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.contains=" + DEFAULT_YEAR_OF_MANUFACTURING);

        // Get all the vehicleDetailsList where yearOfManufacturing contains UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.contains=" + UPDATED_YEAR_OF_MANUFACTURING);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByYearOfManufacturingNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where yearOfManufacturing does not contain DEFAULT_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldNotBeFound("yearOfManufacturing.doesNotContain=" + DEFAULT_YEAR_OF_MANUFACTURING);

        // Get all the vehicleDetailsList where yearOfManufacturing does not contain UPDATED_YEAR_OF_MANUFACTURING
        defaultVehicleDetailsShouldBeFound("yearOfManufacturing.doesNotContain=" + UPDATED_YEAR_OF_MANUFACTURING);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate equals to DEFAULT_REGISTRATION_DATE
        defaultVehicleDetailsShouldBeFound("registrationDate.equals=" + DEFAULT_REGISTRATION_DATE);

        // Get all the vehicleDetailsList where registrationDate equals to UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldNotBeFound("registrationDate.equals=" + UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate not equals to DEFAULT_REGISTRATION_DATE
        defaultVehicleDetailsShouldNotBeFound("registrationDate.notEquals=" + DEFAULT_REGISTRATION_DATE);

        // Get all the vehicleDetailsList where registrationDate not equals to UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldBeFound("registrationDate.notEquals=" + UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate in DEFAULT_REGISTRATION_DATE or UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldBeFound("registrationDate.in=" + DEFAULT_REGISTRATION_DATE + "," + UPDATED_REGISTRATION_DATE);

        // Get all the vehicleDetailsList where registrationDate equals to UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldNotBeFound("registrationDate.in=" + UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate is not null
        defaultVehicleDetailsShouldBeFound("registrationDate.specified=true");

        // Get all the vehicleDetailsList where registrationDate is null
        defaultVehicleDetailsShouldNotBeFound("registrationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate contains DEFAULT_REGISTRATION_DATE
        defaultVehicleDetailsShouldBeFound("registrationDate.contains=" + DEFAULT_REGISTRATION_DATE);

        // Get all the vehicleDetailsList where registrationDate contains UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldNotBeFound("registrationDate.contains=" + UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByRegistrationDateNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where registrationDate does not contain DEFAULT_REGISTRATION_DATE
        defaultVehicleDetailsShouldNotBeFound("registrationDate.doesNotContain=" + DEFAULT_REGISTRATION_DATE);

        // Get all the vehicleDetailsList where registrationDate does not contain UPDATED_REGISTRATION_DATE
        defaultVehicleDetailsShouldBeFound("registrationDate.doesNotContain=" + UPDATED_REGISTRATION_DATE);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultVehicleDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultVehicleDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the vehicleDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified is not null
        defaultVehicleDetailsShouldBeFound("lastModified.specified=true");

        // Get all the vehicleDetailsList where lastModified is null
        defaultVehicleDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultVehicleDetailsShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleDetailsList where lastModified contains UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultVehicleDetailsShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the vehicleDetailsList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultVehicleDetailsShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the vehicleDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy is not null
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the vehicleDetailsList where lastModifiedBy is null
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        // Get all the vehicleDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vehicleDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultVehicleDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVehicleDetailsByParameterLookupIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);
        ParameterLookup parameterLookup;
        if (TestUtil.findAll(em, ParameterLookup.class).isEmpty()) {
            parameterLookup = ParameterLookupResourceIT.createEntity(em);
            em.persist(parameterLookup);
            em.flush();
        } else {
            parameterLookup = TestUtil.findAll(em, ParameterLookup.class).get(0);
        }
        em.persist(parameterLookup);
        em.flush();
        vehicleDetails.addParameterLookup(parameterLookup);
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);
        Long parameterLookupId = parameterLookup.getId();

        // Get all the vehicleDetailsList where parameterLookup equals to parameterLookupId
        defaultVehicleDetailsShouldBeFound("parameterLookupId.equals=" + parameterLookupId);

        // Get all the vehicleDetailsList where parameterLookup equals to (parameterLookupId + 1)
        defaultVehicleDetailsShouldNotBeFound("parameterLookupId.equals=" + (parameterLookupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVehicleDetailsShouldBeFound(String filter) throws Exception {
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.intValue())))
            .andExpect(jsonPath("$.[*].invoiceValue").value(hasItem(DEFAULT_INVOICE_VALUE)))
            .andExpect(jsonPath("$.[*].idv").value(hasItem(DEFAULT_IDV)))
            .andExpect(jsonPath("$.[*].enginNumber").value(hasItem(DEFAULT_ENGIN_NUMBER)))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].seatingCapacity").value(hasItem(DEFAULT_SEATING_CAPACITY.intValue())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
            .andExpect(jsonPath("$.[*].yearOfManufacturing").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURING)))
            .andExpect(jsonPath("$.[*].registrationDate").value(hasItem(DEFAULT_REGISTRATION_DATE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVehicleDetailsShouldNotBeFound(String filter) throws Exception {
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVehicleDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVehicleDetails() throws Exception {
        // Get the vehicleDetails
        restVehicleDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Update the vehicleDetails
        VehicleDetails updatedVehicleDetails = vehicleDetailsRepository.findById(vehicleDetails.getId()).get();
        // Disconnect from session so that the updates on updatedVehicleDetails are not directly saved in db
        em.detach(updatedVehicleDetails);
        updatedVehicleDetails
            .name(UPDATED_NAME)
            .invoiceValue(UPDATED_INVOICE_VALUE)
            .idv(UPDATED_IDV)
            .enginNumber(UPDATED_ENGIN_NUMBER)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .zone(UPDATED_ZONE)
            .yearOfManufacturing(UPDATED_YEAR_OF_MANUFACTURING)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(updatedVehicleDetails);

        restVehicleDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleDetails.getInvoiceValue()).isEqualTo(UPDATED_INVOICE_VALUE);
        assertThat(testVehicleDetails.getIdv()).isEqualTo(UPDATED_IDV);
        assertThat(testVehicleDetails.getEnginNumber()).isEqualTo(UPDATED_ENGIN_NUMBER);
        assertThat(testVehicleDetails.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testVehicleDetails.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testVehicleDetails.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testVehicleDetails.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testVehicleDetails.getYearOfManufacturing()).isEqualTo(UPDATED_YEAR_OF_MANUFACTURING);
        assertThat(testVehicleDetails.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testVehicleDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVehicleDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vehicleDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVehicleDetailsWithPatch() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Update the vehicleDetails using partial update
        VehicleDetails partialUpdatedVehicleDetails = new VehicleDetails();
        partialUpdatedVehicleDetails.setId(vehicleDetails.getId());

        partialUpdatedVehicleDetails
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .zone(UPDATED_ZONE)
            .registrationDate(UPDATED_REGISTRATION_DATE);

        restVehicleDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleDetails))
            )
            .andExpect(status().isOk());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVehicleDetails.getInvoiceValue()).isEqualTo(DEFAULT_INVOICE_VALUE);
        assertThat(testVehicleDetails.getIdv()).isEqualTo(DEFAULT_IDV);
        assertThat(testVehicleDetails.getEnginNumber()).isEqualTo(DEFAULT_ENGIN_NUMBER);
        assertThat(testVehicleDetails.getChassisNumber()).isEqualTo(DEFAULT_CHASSIS_NUMBER);
        assertThat(testVehicleDetails.getRegistrationNumber()).isEqualTo(DEFAULT_REGISTRATION_NUMBER);
        assertThat(testVehicleDetails.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testVehicleDetails.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testVehicleDetails.getYearOfManufacturing()).isEqualTo(DEFAULT_YEAR_OF_MANUFACTURING);
        assertThat(testVehicleDetails.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testVehicleDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVehicleDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateVehicleDetailsWithPatch() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();

        // Update the vehicleDetails using partial update
        VehicleDetails partialUpdatedVehicleDetails = new VehicleDetails();
        partialUpdatedVehicleDetails.setId(vehicleDetails.getId());

        partialUpdatedVehicleDetails
            .name(UPDATED_NAME)
            .invoiceValue(UPDATED_INVOICE_VALUE)
            .idv(UPDATED_IDV)
            .enginNumber(UPDATED_ENGIN_NUMBER)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .seatingCapacity(UPDATED_SEATING_CAPACITY)
            .zone(UPDATED_ZONE)
            .yearOfManufacturing(UPDATED_YEAR_OF_MANUFACTURING)
            .registrationDate(UPDATED_REGISTRATION_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restVehicleDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVehicleDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVehicleDetails))
            )
            .andExpect(status().isOk());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
        VehicleDetails testVehicleDetails = vehicleDetailsList.get(vehicleDetailsList.size() - 1);
        assertThat(testVehicleDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVehicleDetails.getInvoiceValue()).isEqualTo(UPDATED_INVOICE_VALUE);
        assertThat(testVehicleDetails.getIdv()).isEqualTo(UPDATED_IDV);
        assertThat(testVehicleDetails.getEnginNumber()).isEqualTo(UPDATED_ENGIN_NUMBER);
        assertThat(testVehicleDetails.getChassisNumber()).isEqualTo(UPDATED_CHASSIS_NUMBER);
        assertThat(testVehicleDetails.getRegistrationNumber()).isEqualTo(UPDATED_REGISTRATION_NUMBER);
        assertThat(testVehicleDetails.getSeatingCapacity()).isEqualTo(UPDATED_SEATING_CAPACITY);
        assertThat(testVehicleDetails.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testVehicleDetails.getYearOfManufacturing()).isEqualTo(UPDATED_YEAR_OF_MANUFACTURING);
        assertThat(testVehicleDetails.getRegistrationDate()).isEqualTo(UPDATED_REGISTRATION_DATE);
        assertThat(testVehicleDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVehicleDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vehicleDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVehicleDetails() throws Exception {
        int databaseSizeBeforeUpdate = vehicleDetailsRepository.findAll().size();
        vehicleDetails.setId(count.incrementAndGet());

        // Create the VehicleDetails
        VehicleDetailsDTO vehicleDetailsDTO = vehicleDetailsMapper.toDto(vehicleDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVehicleDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vehicleDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VehicleDetails in the database
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVehicleDetails() throws Exception {
        // Initialize the database
        vehicleDetailsRepository.saveAndFlush(vehicleDetails);

        int databaseSizeBeforeDelete = vehicleDetailsRepository.findAll().size();

        // Delete the vehicleDetails
        restVehicleDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, vehicleDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VehicleDetails> vehicleDetailsList = vehicleDetailsRepository.findAll();
        assertThat(vehicleDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
