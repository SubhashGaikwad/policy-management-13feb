package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.service.criteria.AddressCriteria;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_LANDMARK = "AAAAAAAAAA";
    private static final String UPDATED_LANDMARK = "BBBBBBBBBB";

    private static final String DEFAULT_TALUKA = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final Long DEFAULT_PINCODE = 1L;
    private static final Long UPDATED_PINCODE = 2L;
    private static final Long SMALLER_PINCODE = 1L - 1L;

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .area(DEFAULT_AREA)
            .landmark(DEFAULT_LANDMARK)
            .taluka(DEFAULT_TALUKA)
            .district(DEFAULT_DISTRICT)
            .state(DEFAULT_STATE)
            .pincode(DEFAULT_PINCODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .area(UPDATED_AREA)
            .landmark(UPDATED_LANDMARK)
            .taluka(UPDATED_TALUKA)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testAddress.getLandmark()).isEqualTo(DEFAULT_LANDMARK);
        assertThat(testAddress.getTaluka()).isEqualTo(DEFAULT_TALUKA);
        assertThat(testAddress.getDistrict()).isEqualTo(DEFAULT_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setLastModified(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setLastModifiedBy(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].taluka").value(hasItem(DEFAULT_TALUKA)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.landmark").value(DEFAULT_LANDMARK))
            .andExpect(jsonPath("$.taluka").value(DEFAULT_TALUKA))
            .andExpect(jsonPath("$.district").value(DEFAULT_DISTRICT))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area equals to DEFAULT_AREA
        defaultAddressShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the addressList where area equals to UPDATED_AREA
        defaultAddressShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAddressesByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area not equals to DEFAULT_AREA
        defaultAddressShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the addressList where area not equals to UPDATED_AREA
        defaultAddressShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAddressesByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area in DEFAULT_AREA or UPDATED_AREA
        defaultAddressShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the addressList where area equals to UPDATED_AREA
        defaultAddressShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAddressesByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area is not null
        defaultAddressShouldBeFound("area.specified=true");

        // Get all the addressList where area is null
        defaultAddressShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAreaContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area contains DEFAULT_AREA
        defaultAddressShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the addressList where area contains UPDATED_AREA
        defaultAddressShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAddressesByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where area does not contain DEFAULT_AREA
        defaultAddressShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the addressList where area does not contain UPDATED_AREA
        defaultAddressShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark equals to DEFAULT_LANDMARK
        defaultAddressShouldBeFound("landmark.equals=" + DEFAULT_LANDMARK);

        // Get all the addressList where landmark equals to UPDATED_LANDMARK
        defaultAddressShouldNotBeFound("landmark.equals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark not equals to DEFAULT_LANDMARK
        defaultAddressShouldNotBeFound("landmark.notEquals=" + DEFAULT_LANDMARK);

        // Get all the addressList where landmark not equals to UPDATED_LANDMARK
        defaultAddressShouldBeFound("landmark.notEquals=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark in DEFAULT_LANDMARK or UPDATED_LANDMARK
        defaultAddressShouldBeFound("landmark.in=" + DEFAULT_LANDMARK + "," + UPDATED_LANDMARK);

        // Get all the addressList where landmark equals to UPDATED_LANDMARK
        defaultAddressShouldNotBeFound("landmark.in=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark is not null
        defaultAddressShouldBeFound("landmark.specified=true");

        // Get all the addressList where landmark is null
        defaultAddressShouldNotBeFound("landmark.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark contains DEFAULT_LANDMARK
        defaultAddressShouldBeFound("landmark.contains=" + DEFAULT_LANDMARK);

        // Get all the addressList where landmark contains UPDATED_LANDMARK
        defaultAddressShouldNotBeFound("landmark.contains=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandmarkNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landmark does not contain DEFAULT_LANDMARK
        defaultAddressShouldNotBeFound("landmark.doesNotContain=" + DEFAULT_LANDMARK);

        // Get all the addressList where landmark does not contain UPDATED_LANDMARK
        defaultAddressShouldBeFound("landmark.doesNotContain=" + UPDATED_LANDMARK);
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka equals to DEFAULT_TALUKA
        defaultAddressShouldBeFound("taluka.equals=" + DEFAULT_TALUKA);

        // Get all the addressList where taluka equals to UPDATED_TALUKA
        defaultAddressShouldNotBeFound("taluka.equals=" + UPDATED_TALUKA);
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka not equals to DEFAULT_TALUKA
        defaultAddressShouldNotBeFound("taluka.notEquals=" + DEFAULT_TALUKA);

        // Get all the addressList where taluka not equals to UPDATED_TALUKA
        defaultAddressShouldBeFound("taluka.notEquals=" + UPDATED_TALUKA);
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka in DEFAULT_TALUKA or UPDATED_TALUKA
        defaultAddressShouldBeFound("taluka.in=" + DEFAULT_TALUKA + "," + UPDATED_TALUKA);

        // Get all the addressList where taluka equals to UPDATED_TALUKA
        defaultAddressShouldNotBeFound("taluka.in=" + UPDATED_TALUKA);
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka is not null
        defaultAddressShouldBeFound("taluka.specified=true");

        // Get all the addressList where taluka is null
        defaultAddressShouldNotBeFound("taluka.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka contains DEFAULT_TALUKA
        defaultAddressShouldBeFound("taluka.contains=" + DEFAULT_TALUKA);

        // Get all the addressList where taluka contains UPDATED_TALUKA
        defaultAddressShouldNotBeFound("taluka.contains=" + UPDATED_TALUKA);
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where taluka does not contain DEFAULT_TALUKA
        defaultAddressShouldNotBeFound("taluka.doesNotContain=" + DEFAULT_TALUKA);

        // Get all the addressList where taluka does not contain UPDATED_TALUKA
        defaultAddressShouldBeFound("taluka.doesNotContain=" + UPDATED_TALUKA);
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district equals to DEFAULT_DISTRICT
        defaultAddressShouldBeFound("district.equals=" + DEFAULT_DISTRICT);

        // Get all the addressList where district equals to UPDATED_DISTRICT
        defaultAddressShouldNotBeFound("district.equals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district not equals to DEFAULT_DISTRICT
        defaultAddressShouldNotBeFound("district.notEquals=" + DEFAULT_DISTRICT);

        // Get all the addressList where district not equals to UPDATED_DISTRICT
        defaultAddressShouldBeFound("district.notEquals=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district in DEFAULT_DISTRICT or UPDATED_DISTRICT
        defaultAddressShouldBeFound("district.in=" + DEFAULT_DISTRICT + "," + UPDATED_DISTRICT);

        // Get all the addressList where district equals to UPDATED_DISTRICT
        defaultAddressShouldNotBeFound("district.in=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district is not null
        defaultAddressShouldBeFound("district.specified=true");

        // Get all the addressList where district is null
        defaultAddressShouldNotBeFound("district.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district contains DEFAULT_DISTRICT
        defaultAddressShouldBeFound("district.contains=" + DEFAULT_DISTRICT);

        // Get all the addressList where district contains UPDATED_DISTRICT
        defaultAddressShouldNotBeFound("district.contains=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where district does not contain DEFAULT_DISTRICT
        defaultAddressShouldNotBeFound("district.doesNotContain=" + DEFAULT_DISTRICT);

        // Get all the addressList where district does not contain UPDATED_DISTRICT
        defaultAddressShouldBeFound("district.doesNotContain=" + UPDATED_DISTRICT);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state equals to DEFAULT_STATE
        defaultAddressShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state not equals to DEFAULT_STATE
        defaultAddressShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the addressList where state not equals to UPDATED_STATE
        defaultAddressShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state in DEFAULT_STATE or UPDATED_STATE
        defaultAddressShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state is not null
        defaultAddressShouldBeFound("state.specified=true");

        // Get all the addressList where state is null
        defaultAddressShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByStateContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state contains DEFAULT_STATE
        defaultAddressShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the addressList where state contains UPDATED_STATE
        defaultAddressShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state does not contain DEFAULT_STATE
        defaultAddressShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the addressList where state does not contain UPDATED_STATE
        defaultAddressShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode equals to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode not equals to DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode not equals to UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is not null
        defaultAddressShouldBeFound("pincode.specified=true");

        // Get all the addressList where pincode is null
        defaultAddressShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is greater than or equal to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.greaterThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode is greater than or equal to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.greaterThanOrEqual=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is less than or equal to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.lessThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode is less than or equal to SMALLER_PINCODE
        defaultAddressShouldNotBeFound("pincode.lessThanOrEqual=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is less than DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.lessThan=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode is less than UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.lessThan=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is greater than DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.greaterThan=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode is greater than SMALLER_PINCODE
        defaultAddressShouldBeFound("pincode.greaterThan=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified is not null
        defaultAddressShouldBeFound("lastModified.specified=true");

        // Get all the addressList where lastModified is null
        defaultAddressShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified contains UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy is not null
        defaultAddressShouldBeFound("lastModifiedBy.specified=true");

        // Get all the addressList where lastModifiedBy is null
        defaultAddressShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByPolicyUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
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
        address.setPolicyUsers(policyUsers);
        addressRepository.saveAndFlush(address);
        Long policyUsersId = policyUsers.getId();

        // Get all the addressList where policyUsers equals to policyUsersId
        defaultAddressShouldBeFound("policyUsersId.equals=" + policyUsersId);

        // Get all the addressList where policyUsers equals to (policyUsersId + 1)
        defaultAddressShouldNotBeFound("policyUsersId.equals=" + (policyUsersId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);
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
        address.setCompany(company);
        addressRepository.saveAndFlush(address);
        Long companyId = company.getId();

        // Get all the addressList where company equals to companyId
        defaultAddressShouldBeFound("companyId.equals=" + companyId);

        // Get all the addressList where company equals to (companyId + 1)
        defaultAddressShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].landmark").value(hasItem(DEFAULT_LANDMARK)))
            .andExpect(jsonPath("$.[*].taluka").value(hasItem(DEFAULT_TALUKA)))
            .andExpect(jsonPath("$.[*].district").value(hasItem(DEFAULT_DISTRICT)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .area(UPDATED_AREA)
            .landmark(UPDATED_LANDMARK)
            .taluka(UPDATED_TALUKA)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getTaluka()).isEqualTo(UPDATED_TALUKA);
        assertThat(testAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .landmark(UPDATED_LANDMARK)
            .taluka(UPDATED_TALUKA)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getTaluka()).isEqualTo(UPDATED_TALUKA);
        assertThat(testAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .area(UPDATED_AREA)
            .landmark(UPDATED_LANDMARK)
            .taluka(UPDATED_TALUKA)
            .district(UPDATED_DISTRICT)
            .state(UPDATED_STATE)
            .pincode(UPDATED_PINCODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testAddress.getLandmark()).isEqualTo(UPDATED_LANDMARK);
        assertThat(testAddress.getTaluka()).isEqualTo(UPDATED_TALUKA);
        assertThat(testAddress.getDistrict()).isEqualTo(UPDATED_DISTRICT);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
