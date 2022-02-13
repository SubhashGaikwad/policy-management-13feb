package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.domain.UsersType;
import com.mycompany.myapp.domain.enumeration.StatusInd;
import com.mycompany.myapp.repository.PolicyUsersRepository;
import com.mycompany.myapp.service.criteria.PolicyUsersCriteria;
import com.mycompany.myapp.service.dto.PolicyUsersDTO;
import com.mycompany.myapp.service.mapper.PolicyUsersMapper;
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
 * Integration tests for the {@link PolicyUsersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolicyUsersResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_HEAD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_HEAD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MARRIAGE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_MARRIAGE_DATE = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_TYPE_ID = 1L;
    private static final Long UPDATED_USER_TYPE_ID = 2L;
    private static final Long SMALLER_USER_TYPE_ID = 1L - 1L;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final StatusInd DEFAULT_STATUS = StatusInd.A;
    private static final StatusInd UPDATED_STATUS = StatusInd.I;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LICENCE_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD_NUBER = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD_NUBER = "BBBBBBBBBB";

    private static final String DEFAULT_PANCARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PANCARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ONE_TIME_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ONE_TIME_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_OTP_EXPIRY_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OTP_EXPIRY_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/policy-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolicyUsersRepository policyUsersRepository;

    @Autowired
    private PolicyUsersMapper policyUsersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyUsersMockMvc;

    private PolicyUsers policyUsers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyUsers createEntity(EntityManager em) {
        PolicyUsers policyUsers = new PolicyUsers()
            .groupCode(DEFAULT_GROUP_CODE)
            .groupHeadName(DEFAULT_GROUP_HEAD_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .marriageDate(DEFAULT_MARRIAGE_DATE)
            .userTypeId(DEFAULT_USER_TYPE_ID)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .imageUrl(DEFAULT_IMAGE_URL)
            .status(DEFAULT_STATUS)
            .activated(DEFAULT_ACTIVATED)
            .licenceExpiryDate(DEFAULT_LICENCE_EXPIRY_DATE)
            .mobileNo(DEFAULT_MOBILE_NO)
            .aadharCardNuber(DEFAULT_AADHAR_CARD_NUBER)
            .pancardNumber(DEFAULT_PANCARD_NUMBER)
            .oneTimePassword(DEFAULT_ONE_TIME_PASSWORD)
            .otpExpiryTime(DEFAULT_OTP_EXPIRY_TIME)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return policyUsers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PolicyUsers createUpdatedEntity(EntityManager em) {
        PolicyUsers policyUsers = new PolicyUsers()
            .groupCode(UPDATED_GROUP_CODE)
            .groupHeadName(UPDATED_GROUP_HEAD_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .marriageDate(UPDATED_MARRIAGE_DATE)
            .userTypeId(UPDATED_USER_TYPE_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .activated(UPDATED_ACTIVATED)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .aadharCardNuber(UPDATED_AADHAR_CARD_NUBER)
            .pancardNumber(UPDATED_PANCARD_NUMBER)
            .oneTimePassword(UPDATED_ONE_TIME_PASSWORD)
            .otpExpiryTime(UPDATED_OTP_EXPIRY_TIME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return policyUsers;
    }

    @BeforeEach
    public void initTest() {
        policyUsers = createEntity(em);
    }

    @Test
    @Transactional
    void createPolicyUsers() throws Exception {
        int databaseSizeBeforeCreate = policyUsersRepository.findAll().size();
        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);
        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeCreate + 1);
        PolicyUsers testPolicyUsers = policyUsersList.get(policyUsersList.size() - 1);
        assertThat(testPolicyUsers.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testPolicyUsers.getGroupHeadName()).isEqualTo(DEFAULT_GROUP_HEAD_NAME);
        assertThat(testPolicyUsers.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testPolicyUsers.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testPolicyUsers.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPolicyUsers.getMarriageDate()).isEqualTo(DEFAULT_MARRIAGE_DATE);
        assertThat(testPolicyUsers.getUserTypeId()).isEqualTo(DEFAULT_USER_TYPE_ID);
        assertThat(testPolicyUsers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testPolicyUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPolicyUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPolicyUsers.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testPolicyUsers.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicyUsers.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPolicyUsers.getLicenceExpiryDate()).isEqualTo(DEFAULT_LICENCE_EXPIRY_DATE);
        assertThat(testPolicyUsers.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testPolicyUsers.getAadharCardNuber()).isEqualTo(DEFAULT_AADHAR_CARD_NUBER);
        assertThat(testPolicyUsers.getPancardNumber()).isEqualTo(DEFAULT_PANCARD_NUMBER);
        assertThat(testPolicyUsers.getOneTimePassword()).isEqualTo(DEFAULT_ONE_TIME_PASSWORD);
        assertThat(testPolicyUsers.getOtpExpiryTime()).isEqualTo(DEFAULT_OTP_EXPIRY_TIME);
        assertThat(testPolicyUsers.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicyUsers.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPolicyUsersWithExistingId() throws Exception {
        // Create the PolicyUsers with an existing ID
        policyUsers.setId(1L);
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        int databaseSizeBeforeCreate = policyUsersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBirthDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setBirthDate(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMarriageDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setMarriageDate(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setUsername(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setPassword(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setActivated(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setLastModified(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyUsersRepository.findAll().size();
        // set the field null
        policyUsers.setLastModifiedBy(null);

        // Create the PolicyUsers, which fails.
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        restPolicyUsersMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPolicyUsers() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].groupHeadName").value(hasItem(DEFAULT_GROUP_HEAD_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].marriageDate").value(hasItem(DEFAULT_MARRIAGE_DATE)))
            .andExpect(jsonPath("$.[*].userTypeId").value(hasItem(DEFAULT_USER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].licenceExpiryDate").value(hasItem(DEFAULT_LICENCE_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].aadharCardNuber").value(hasItem(DEFAULT_AADHAR_CARD_NUBER)))
            .andExpect(jsonPath("$.[*].pancardNumber").value(hasItem(DEFAULT_PANCARD_NUMBER)))
            .andExpect(jsonPath("$.[*].oneTimePassword").value(hasItem(DEFAULT_ONE_TIME_PASSWORD)))
            .andExpect(jsonPath("$.[*].otpExpiryTime").value(hasItem(DEFAULT_OTP_EXPIRY_TIME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPolicyUsers() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get the policyUsers
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL_ID, policyUsers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policyUsers.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE))
            .andExpect(jsonPath("$.groupHeadName").value(DEFAULT_GROUP_HEAD_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
            .andExpect(jsonPath("$.marriageDate").value(DEFAULT_MARRIAGE_DATE))
            .andExpect(jsonPath("$.userTypeId").value(DEFAULT_USER_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.licenceExpiryDate").value(DEFAULT_LICENCE_EXPIRY_DATE))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.aadharCardNuber").value(DEFAULT_AADHAR_CARD_NUBER))
            .andExpect(jsonPath("$.pancardNumber").value(DEFAULT_PANCARD_NUMBER))
            .andExpect(jsonPath("$.oneTimePassword").value(DEFAULT_ONE_TIME_PASSWORD))
            .andExpect(jsonPath("$.otpExpiryTime").value(DEFAULT_OTP_EXPIRY_TIME))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPolicyUsersByIdFiltering() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        Long id = policyUsers.getId();

        defaultPolicyUsersShouldBeFound("id.equals=" + id);
        defaultPolicyUsersShouldNotBeFound("id.notEquals=" + id);

        defaultPolicyUsersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPolicyUsersShouldNotBeFound("id.greaterThan=" + id);

        defaultPolicyUsersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPolicyUsersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode equals to DEFAULT_GROUP_CODE
        defaultPolicyUsersShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the policyUsersList where groupCode equals to UPDATED_GROUP_CODE
        defaultPolicyUsersShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode not equals to DEFAULT_GROUP_CODE
        defaultPolicyUsersShouldNotBeFound("groupCode.notEquals=" + DEFAULT_GROUP_CODE);

        // Get all the policyUsersList where groupCode not equals to UPDATED_GROUP_CODE
        defaultPolicyUsersShouldBeFound("groupCode.notEquals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultPolicyUsersShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the policyUsersList where groupCode equals to UPDATED_GROUP_CODE
        defaultPolicyUsersShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode is not null
        defaultPolicyUsersShouldBeFound("groupCode.specified=true");

        // Get all the policyUsersList where groupCode is null
        defaultPolicyUsersShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode contains DEFAULT_GROUP_CODE
        defaultPolicyUsersShouldBeFound("groupCode.contains=" + DEFAULT_GROUP_CODE);

        // Get all the policyUsersList where groupCode contains UPDATED_GROUP_CODE
        defaultPolicyUsersShouldNotBeFound("groupCode.contains=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupCodeNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupCode does not contain DEFAULT_GROUP_CODE
        defaultPolicyUsersShouldNotBeFound("groupCode.doesNotContain=" + DEFAULT_GROUP_CODE);

        // Get all the policyUsersList where groupCode does not contain UPDATED_GROUP_CODE
        defaultPolicyUsersShouldBeFound("groupCode.doesNotContain=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName equals to DEFAULT_GROUP_HEAD_NAME
        defaultPolicyUsersShouldBeFound("groupHeadName.equals=" + DEFAULT_GROUP_HEAD_NAME);

        // Get all the policyUsersList where groupHeadName equals to UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldNotBeFound("groupHeadName.equals=" + UPDATED_GROUP_HEAD_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName not equals to DEFAULT_GROUP_HEAD_NAME
        defaultPolicyUsersShouldNotBeFound("groupHeadName.notEquals=" + DEFAULT_GROUP_HEAD_NAME);

        // Get all the policyUsersList where groupHeadName not equals to UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldBeFound("groupHeadName.notEquals=" + UPDATED_GROUP_HEAD_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName in DEFAULT_GROUP_HEAD_NAME or UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldBeFound("groupHeadName.in=" + DEFAULT_GROUP_HEAD_NAME + "," + UPDATED_GROUP_HEAD_NAME);

        // Get all the policyUsersList where groupHeadName equals to UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldNotBeFound("groupHeadName.in=" + UPDATED_GROUP_HEAD_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName is not null
        defaultPolicyUsersShouldBeFound("groupHeadName.specified=true");

        // Get all the policyUsersList where groupHeadName is null
        defaultPolicyUsersShouldNotBeFound("groupHeadName.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName contains DEFAULT_GROUP_HEAD_NAME
        defaultPolicyUsersShouldBeFound("groupHeadName.contains=" + DEFAULT_GROUP_HEAD_NAME);

        // Get all the policyUsersList where groupHeadName contains UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldNotBeFound("groupHeadName.contains=" + UPDATED_GROUP_HEAD_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByGroupHeadNameNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where groupHeadName does not contain DEFAULT_GROUP_HEAD_NAME
        defaultPolicyUsersShouldNotBeFound("groupHeadName.doesNotContain=" + DEFAULT_GROUP_HEAD_NAME);

        // Get all the policyUsersList where groupHeadName does not contain UPDATED_GROUP_HEAD_NAME
        defaultPolicyUsersShouldBeFound("groupHeadName.doesNotContain=" + UPDATED_GROUP_HEAD_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName equals to DEFAULT_FIRST_NAME
        defaultPolicyUsersShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the policyUsersList where firstName equals to UPDATED_FIRST_NAME
        defaultPolicyUsersShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName not equals to DEFAULT_FIRST_NAME
        defaultPolicyUsersShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the policyUsersList where firstName not equals to UPDATED_FIRST_NAME
        defaultPolicyUsersShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultPolicyUsersShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the policyUsersList where firstName equals to UPDATED_FIRST_NAME
        defaultPolicyUsersShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName is not null
        defaultPolicyUsersShouldBeFound("firstName.specified=true");

        // Get all the policyUsersList where firstName is null
        defaultPolicyUsersShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName contains DEFAULT_FIRST_NAME
        defaultPolicyUsersShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the policyUsersList where firstName contains UPDATED_FIRST_NAME
        defaultPolicyUsersShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where firstName does not contain DEFAULT_FIRST_NAME
        defaultPolicyUsersShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the policyUsersList where firstName does not contain UPDATED_FIRST_NAME
        defaultPolicyUsersShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName equals to DEFAULT_LAST_NAME
        defaultPolicyUsersShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the policyUsersList where lastName equals to UPDATED_LAST_NAME
        defaultPolicyUsersShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName not equals to DEFAULT_LAST_NAME
        defaultPolicyUsersShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the policyUsersList where lastName not equals to UPDATED_LAST_NAME
        defaultPolicyUsersShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultPolicyUsersShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the policyUsersList where lastName equals to UPDATED_LAST_NAME
        defaultPolicyUsersShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName is not null
        defaultPolicyUsersShouldBeFound("lastName.specified=true");

        // Get all the policyUsersList where lastName is null
        defaultPolicyUsersShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName contains DEFAULT_LAST_NAME
        defaultPolicyUsersShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the policyUsersList where lastName contains UPDATED_LAST_NAME
        defaultPolicyUsersShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastName does not contain DEFAULT_LAST_NAME
        defaultPolicyUsersShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the policyUsersList where lastName does not contain UPDATED_LAST_NAME
        defaultPolicyUsersShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultPolicyUsersShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the policyUsersList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultPolicyUsersShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the policyUsersList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the policyUsersList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate is not null
        defaultPolicyUsersShouldBeFound("birthDate.specified=true");

        // Get all the policyUsersList where birthDate is null
        defaultPolicyUsersShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate contains DEFAULT_BIRTH_DATE
        defaultPolicyUsersShouldBeFound("birthDate.contains=" + DEFAULT_BIRTH_DATE);

        // Get all the policyUsersList where birthDate contains UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldNotBeFound("birthDate.contains=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByBirthDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where birthDate does not contain DEFAULT_BIRTH_DATE
        defaultPolicyUsersShouldNotBeFound("birthDate.doesNotContain=" + DEFAULT_BIRTH_DATE);

        // Get all the policyUsersList where birthDate does not contain UPDATED_BIRTH_DATE
        defaultPolicyUsersShouldBeFound("birthDate.doesNotContain=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate equals to DEFAULT_MARRIAGE_DATE
        defaultPolicyUsersShouldBeFound("marriageDate.equals=" + DEFAULT_MARRIAGE_DATE);

        // Get all the policyUsersList where marriageDate equals to UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldNotBeFound("marriageDate.equals=" + UPDATED_MARRIAGE_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate not equals to DEFAULT_MARRIAGE_DATE
        defaultPolicyUsersShouldNotBeFound("marriageDate.notEquals=" + DEFAULT_MARRIAGE_DATE);

        // Get all the policyUsersList where marriageDate not equals to UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldBeFound("marriageDate.notEquals=" + UPDATED_MARRIAGE_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate in DEFAULT_MARRIAGE_DATE or UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldBeFound("marriageDate.in=" + DEFAULT_MARRIAGE_DATE + "," + UPDATED_MARRIAGE_DATE);

        // Get all the policyUsersList where marriageDate equals to UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldNotBeFound("marriageDate.in=" + UPDATED_MARRIAGE_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate is not null
        defaultPolicyUsersShouldBeFound("marriageDate.specified=true");

        // Get all the policyUsersList where marriageDate is null
        defaultPolicyUsersShouldNotBeFound("marriageDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate contains DEFAULT_MARRIAGE_DATE
        defaultPolicyUsersShouldBeFound("marriageDate.contains=" + DEFAULT_MARRIAGE_DATE);

        // Get all the policyUsersList where marriageDate contains UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldNotBeFound("marriageDate.contains=" + UPDATED_MARRIAGE_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMarriageDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where marriageDate does not contain DEFAULT_MARRIAGE_DATE
        defaultPolicyUsersShouldNotBeFound("marriageDate.doesNotContain=" + DEFAULT_MARRIAGE_DATE);

        // Get all the policyUsersList where marriageDate does not contain UPDATED_MARRIAGE_DATE
        defaultPolicyUsersShouldBeFound("marriageDate.doesNotContain=" + UPDATED_MARRIAGE_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId equals to DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.equals=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId equals to UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.equals=" + UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId not equals to DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.notEquals=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId not equals to UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.notEquals=" + UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId in DEFAULT_USER_TYPE_ID or UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.in=" + DEFAULT_USER_TYPE_ID + "," + UPDATED_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId equals to UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.in=" + UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId is not null
        defaultPolicyUsersShouldBeFound("userTypeId.specified=true");

        // Get all the policyUsersList where userTypeId is null
        defaultPolicyUsersShouldNotBeFound("userTypeId.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId is greater than or equal to DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.greaterThanOrEqual=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId is greater than or equal to UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.greaterThanOrEqual=" + UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId is less than or equal to DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.lessThanOrEqual=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId is less than or equal to SMALLER_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.lessThanOrEqual=" + SMALLER_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId is less than DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.lessThan=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId is less than UPDATED_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.lessThan=" + UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUserTypeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where userTypeId is greater than DEFAULT_USER_TYPE_ID
        defaultPolicyUsersShouldNotBeFound("userTypeId.greaterThan=" + DEFAULT_USER_TYPE_ID);

        // Get all the policyUsersList where userTypeId is greater than SMALLER_USER_TYPE_ID
        defaultPolicyUsersShouldBeFound("userTypeId.greaterThan=" + SMALLER_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username equals to DEFAULT_USERNAME
        defaultPolicyUsersShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the policyUsersList where username equals to UPDATED_USERNAME
        defaultPolicyUsersShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username not equals to DEFAULT_USERNAME
        defaultPolicyUsersShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the policyUsersList where username not equals to UPDATED_USERNAME
        defaultPolicyUsersShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultPolicyUsersShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the policyUsersList where username equals to UPDATED_USERNAME
        defaultPolicyUsersShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username is not null
        defaultPolicyUsersShouldBeFound("username.specified=true");

        // Get all the policyUsersList where username is null
        defaultPolicyUsersShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username contains DEFAULT_USERNAME
        defaultPolicyUsersShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the policyUsersList where username contains UPDATED_USERNAME
        defaultPolicyUsersShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where username does not contain DEFAULT_USERNAME
        defaultPolicyUsersShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the policyUsersList where username does not contain UPDATED_USERNAME
        defaultPolicyUsersShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password equals to DEFAULT_PASSWORD
        defaultPolicyUsersShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the policyUsersList where password equals to UPDATED_PASSWORD
        defaultPolicyUsersShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password not equals to DEFAULT_PASSWORD
        defaultPolicyUsersShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the policyUsersList where password not equals to UPDATED_PASSWORD
        defaultPolicyUsersShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultPolicyUsersShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the policyUsersList where password equals to UPDATED_PASSWORD
        defaultPolicyUsersShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password is not null
        defaultPolicyUsersShouldBeFound("password.specified=true");

        // Get all the policyUsersList where password is null
        defaultPolicyUsersShouldNotBeFound("password.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password contains DEFAULT_PASSWORD
        defaultPolicyUsersShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the policyUsersList where password contains UPDATED_PASSWORD
        defaultPolicyUsersShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where password does not contain DEFAULT_PASSWORD
        defaultPolicyUsersShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the policyUsersList where password does not contain UPDATED_PASSWORD
        defaultPolicyUsersShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email equals to DEFAULT_EMAIL
        defaultPolicyUsersShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the policyUsersList where email equals to UPDATED_EMAIL
        defaultPolicyUsersShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email not equals to DEFAULT_EMAIL
        defaultPolicyUsersShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the policyUsersList where email not equals to UPDATED_EMAIL
        defaultPolicyUsersShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPolicyUsersShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the policyUsersList where email equals to UPDATED_EMAIL
        defaultPolicyUsersShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email is not null
        defaultPolicyUsersShouldBeFound("email.specified=true");

        // Get all the policyUsersList where email is null
        defaultPolicyUsersShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email contains DEFAULT_EMAIL
        defaultPolicyUsersShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the policyUsersList where email contains UPDATED_EMAIL
        defaultPolicyUsersShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where email does not contain DEFAULT_EMAIL
        defaultPolicyUsersShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the policyUsersList where email does not contain UPDATED_EMAIL
        defaultPolicyUsersShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultPolicyUsersShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the policyUsersList where imageUrl equals to UPDATED_IMAGE_URL
        defaultPolicyUsersShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultPolicyUsersShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the policyUsersList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultPolicyUsersShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultPolicyUsersShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the policyUsersList where imageUrl equals to UPDATED_IMAGE_URL
        defaultPolicyUsersShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl is not null
        defaultPolicyUsersShouldBeFound("imageUrl.specified=true");

        // Get all the policyUsersList where imageUrl is null
        defaultPolicyUsersShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl contains DEFAULT_IMAGE_URL
        defaultPolicyUsersShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the policyUsersList where imageUrl contains UPDATED_IMAGE_URL
        defaultPolicyUsersShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultPolicyUsersShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the policyUsersList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultPolicyUsersShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where status equals to DEFAULT_STATUS
        defaultPolicyUsersShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the policyUsersList where status equals to UPDATED_STATUS
        defaultPolicyUsersShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where status not equals to DEFAULT_STATUS
        defaultPolicyUsersShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the policyUsersList where status not equals to UPDATED_STATUS
        defaultPolicyUsersShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPolicyUsersShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the policyUsersList where status equals to UPDATED_STATUS
        defaultPolicyUsersShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where status is not null
        defaultPolicyUsersShouldBeFound("status.specified=true");

        // Get all the policyUsersList where status is null
        defaultPolicyUsersShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where activated equals to DEFAULT_ACTIVATED
        defaultPolicyUsersShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the policyUsersList where activated equals to UPDATED_ACTIVATED
        defaultPolicyUsersShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where activated not equals to DEFAULT_ACTIVATED
        defaultPolicyUsersShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the policyUsersList where activated not equals to UPDATED_ACTIVATED
        defaultPolicyUsersShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultPolicyUsersShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the policyUsersList where activated equals to UPDATED_ACTIVATED
        defaultPolicyUsersShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where activated is not null
        defaultPolicyUsersShouldBeFound("activated.specified=true");

        // Get all the policyUsersList where activated is null
        defaultPolicyUsersShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate equals to DEFAULT_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.equals=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the policyUsersList where licenceExpiryDate equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.equals=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate not equals to DEFAULT_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.notEquals=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the policyUsersList where licenceExpiryDate not equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.notEquals=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate in DEFAULT_LICENCE_EXPIRY_DATE or UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.in=" + DEFAULT_LICENCE_EXPIRY_DATE + "," + UPDATED_LICENCE_EXPIRY_DATE);

        // Get all the policyUsersList where licenceExpiryDate equals to UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.in=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate is not null
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.specified=true");

        // Get all the policyUsersList where licenceExpiryDate is null
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate contains DEFAULT_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.contains=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the policyUsersList where licenceExpiryDate contains UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.contains=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLicenceExpiryDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where licenceExpiryDate does not contain DEFAULT_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldNotBeFound("licenceExpiryDate.doesNotContain=" + DEFAULT_LICENCE_EXPIRY_DATE);

        // Get all the policyUsersList where licenceExpiryDate does not contain UPDATED_LICENCE_EXPIRY_DATE
        defaultPolicyUsersShouldBeFound("licenceExpiryDate.doesNotContain=" + UPDATED_LICENCE_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultPolicyUsersShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the policyUsersList where mobileNo equals to UPDATED_MOBILE_NO
        defaultPolicyUsersShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo not equals to DEFAULT_MOBILE_NO
        defaultPolicyUsersShouldNotBeFound("mobileNo.notEquals=" + DEFAULT_MOBILE_NO);

        // Get all the policyUsersList where mobileNo not equals to UPDATED_MOBILE_NO
        defaultPolicyUsersShouldBeFound("mobileNo.notEquals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultPolicyUsersShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the policyUsersList where mobileNo equals to UPDATED_MOBILE_NO
        defaultPolicyUsersShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo is not null
        defaultPolicyUsersShouldBeFound("mobileNo.specified=true");

        // Get all the policyUsersList where mobileNo is null
        defaultPolicyUsersShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo contains DEFAULT_MOBILE_NO
        defaultPolicyUsersShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the policyUsersList where mobileNo contains UPDATED_MOBILE_NO
        defaultPolicyUsersShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultPolicyUsersShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the policyUsersList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultPolicyUsersShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber equals to DEFAULT_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldBeFound("aadharCardNuber.equals=" + DEFAULT_AADHAR_CARD_NUBER);

        // Get all the policyUsersList where aadharCardNuber equals to UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.equals=" + UPDATED_AADHAR_CARD_NUBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber not equals to DEFAULT_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.notEquals=" + DEFAULT_AADHAR_CARD_NUBER);

        // Get all the policyUsersList where aadharCardNuber not equals to UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldBeFound("aadharCardNuber.notEquals=" + UPDATED_AADHAR_CARD_NUBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber in DEFAULT_AADHAR_CARD_NUBER or UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldBeFound("aadharCardNuber.in=" + DEFAULT_AADHAR_CARD_NUBER + "," + UPDATED_AADHAR_CARD_NUBER);

        // Get all the policyUsersList where aadharCardNuber equals to UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.in=" + UPDATED_AADHAR_CARD_NUBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber is not null
        defaultPolicyUsersShouldBeFound("aadharCardNuber.specified=true");

        // Get all the policyUsersList where aadharCardNuber is null
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber contains DEFAULT_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldBeFound("aadharCardNuber.contains=" + DEFAULT_AADHAR_CARD_NUBER);

        // Get all the policyUsersList where aadharCardNuber contains UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.contains=" + UPDATED_AADHAR_CARD_NUBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAadharCardNuberNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where aadharCardNuber does not contain DEFAULT_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldNotBeFound("aadharCardNuber.doesNotContain=" + DEFAULT_AADHAR_CARD_NUBER);

        // Get all the policyUsersList where aadharCardNuber does not contain UPDATED_AADHAR_CARD_NUBER
        defaultPolicyUsersShouldBeFound("aadharCardNuber.doesNotContain=" + UPDATED_AADHAR_CARD_NUBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber equals to DEFAULT_PANCARD_NUMBER
        defaultPolicyUsersShouldBeFound("pancardNumber.equals=" + DEFAULT_PANCARD_NUMBER);

        // Get all the policyUsersList where pancardNumber equals to UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldNotBeFound("pancardNumber.equals=" + UPDATED_PANCARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber not equals to DEFAULT_PANCARD_NUMBER
        defaultPolicyUsersShouldNotBeFound("pancardNumber.notEquals=" + DEFAULT_PANCARD_NUMBER);

        // Get all the policyUsersList where pancardNumber not equals to UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldBeFound("pancardNumber.notEquals=" + UPDATED_PANCARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber in DEFAULT_PANCARD_NUMBER or UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldBeFound("pancardNumber.in=" + DEFAULT_PANCARD_NUMBER + "," + UPDATED_PANCARD_NUMBER);

        // Get all the policyUsersList where pancardNumber equals to UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldNotBeFound("pancardNumber.in=" + UPDATED_PANCARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber is not null
        defaultPolicyUsersShouldBeFound("pancardNumber.specified=true");

        // Get all the policyUsersList where pancardNumber is null
        defaultPolicyUsersShouldNotBeFound("pancardNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber contains DEFAULT_PANCARD_NUMBER
        defaultPolicyUsersShouldBeFound("pancardNumber.contains=" + DEFAULT_PANCARD_NUMBER);

        // Get all the policyUsersList where pancardNumber contains UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldNotBeFound("pancardNumber.contains=" + UPDATED_PANCARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPancardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where pancardNumber does not contain DEFAULT_PANCARD_NUMBER
        defaultPolicyUsersShouldNotBeFound("pancardNumber.doesNotContain=" + DEFAULT_PANCARD_NUMBER);

        // Get all the policyUsersList where pancardNumber does not contain UPDATED_PANCARD_NUMBER
        defaultPolicyUsersShouldBeFound("pancardNumber.doesNotContain=" + UPDATED_PANCARD_NUMBER);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword equals to DEFAULT_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldBeFound("oneTimePassword.equals=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the policyUsersList where oneTimePassword equals to UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.equals=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword not equals to DEFAULT_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.notEquals=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the policyUsersList where oneTimePassword not equals to UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldBeFound("oneTimePassword.notEquals=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword in DEFAULT_ONE_TIME_PASSWORD or UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldBeFound("oneTimePassword.in=" + DEFAULT_ONE_TIME_PASSWORD + "," + UPDATED_ONE_TIME_PASSWORD);

        // Get all the policyUsersList where oneTimePassword equals to UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.in=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword is not null
        defaultPolicyUsersShouldBeFound("oneTimePassword.specified=true");

        // Get all the policyUsersList where oneTimePassword is null
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword contains DEFAULT_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldBeFound("oneTimePassword.contains=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the policyUsersList where oneTimePassword contains UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.contains=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOneTimePasswordNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where oneTimePassword does not contain DEFAULT_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldNotBeFound("oneTimePassword.doesNotContain=" + DEFAULT_ONE_TIME_PASSWORD);

        // Get all the policyUsersList where oneTimePassword does not contain UPDATED_ONE_TIME_PASSWORD
        defaultPolicyUsersShouldBeFound("oneTimePassword.doesNotContain=" + UPDATED_ONE_TIME_PASSWORD);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime equals to DEFAULT_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldBeFound("otpExpiryTime.equals=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the policyUsersList where otpExpiryTime equals to UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.equals=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime not equals to DEFAULT_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.notEquals=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the policyUsersList where otpExpiryTime not equals to UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldBeFound("otpExpiryTime.notEquals=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime in DEFAULT_OTP_EXPIRY_TIME or UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldBeFound("otpExpiryTime.in=" + DEFAULT_OTP_EXPIRY_TIME + "," + UPDATED_OTP_EXPIRY_TIME);

        // Get all the policyUsersList where otpExpiryTime equals to UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.in=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime is not null
        defaultPolicyUsersShouldBeFound("otpExpiryTime.specified=true");

        // Get all the policyUsersList where otpExpiryTime is null
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime contains DEFAULT_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldBeFound("otpExpiryTime.contains=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the policyUsersList where otpExpiryTime contains UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.contains=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByOtpExpiryTimeNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where otpExpiryTime does not contain DEFAULT_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldNotBeFound("otpExpiryTime.doesNotContain=" + DEFAULT_OTP_EXPIRY_TIME);

        // Get all the policyUsersList where otpExpiryTime does not contain UPDATED_OTP_EXPIRY_TIME
        defaultPolicyUsersShouldBeFound("otpExpiryTime.doesNotContain=" + UPDATED_OTP_EXPIRY_TIME);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPolicyUsersShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPolicyUsersShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the policyUsersList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified is not null
        defaultPolicyUsersShouldBeFound("lastModified.specified=true");

        // Get all the policyUsersList where lastModified is null
        defaultPolicyUsersShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPolicyUsersShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPolicyUsersShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyUsersList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPolicyUsersShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the policyUsersList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy is not null
        defaultPolicyUsersShouldBeFound("lastModifiedBy.specified=true");

        // Get all the policyUsersList where lastModifiedBy is null
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        // Get all the policyUsersList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPolicyUsersShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyUsersList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPolicyUsersShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPolicyUsersByUsersTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);
        UsersType usersType;
        if (TestUtil.findAll(em, UsersType.class).isEmpty()) {
            usersType = UsersTypeResourceIT.createEntity(em);
            em.persist(usersType);
            em.flush();
        } else {
            usersType = TestUtil.findAll(em, UsersType.class).get(0);
        }
        em.persist(usersType);
        em.flush();
        policyUsers.setUsersType(usersType);
        policyUsersRepository.saveAndFlush(policyUsers);
        Long usersTypeId = usersType.getId();

        // Get all the policyUsersList where usersType equals to usersTypeId
        defaultPolicyUsersShouldBeFound("usersTypeId.equals=" + usersTypeId);

        // Get all the policyUsersList where usersType equals to (usersTypeId + 1)
        defaultPolicyUsersShouldNotBeFound("usersTypeId.equals=" + (usersTypeId + 1));
    }

    @Test
    @Transactional
    void getAllPolicyUsersByPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);
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
        policyUsers.addPolicy(policy);
        policyUsersRepository.saveAndFlush(policyUsers);
        Long policyId = policy.getId();

        // Get all the policyUsersList where policy equals to policyId
        defaultPolicyUsersShouldBeFound("policyId.equals=" + policyId);

        // Get all the policyUsersList where policy equals to (policyId + 1)
        defaultPolicyUsersShouldNotBeFound("policyId.equals=" + (policyId + 1));
    }

    @Test
    @Transactional
    void getAllPolicyUsersByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        policyUsers.addAddress(address);
        policyUsersRepository.saveAndFlush(policyUsers);
        Long addressId = address.getId();

        // Get all the policyUsersList where address equals to addressId
        defaultPolicyUsersShouldBeFound("addressId.equals=" + addressId);

        // Get all the policyUsersList where address equals to (addressId + 1)
        defaultPolicyUsersShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPolicyUsersShouldBeFound(String filter) throws Exception {
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policyUsers.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].groupHeadName").value(hasItem(DEFAULT_GROUP_HEAD_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].marriageDate").value(hasItem(DEFAULT_MARRIAGE_DATE)))
            .andExpect(jsonPath("$.[*].userTypeId").value(hasItem(DEFAULT_USER_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].licenceExpiryDate").value(hasItem(DEFAULT_LICENCE_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].aadharCardNuber").value(hasItem(DEFAULT_AADHAR_CARD_NUBER)))
            .andExpect(jsonPath("$.[*].pancardNumber").value(hasItem(DEFAULT_PANCARD_NUMBER)))
            .andExpect(jsonPath("$.[*].oneTimePassword").value(hasItem(DEFAULT_ONE_TIME_PASSWORD)))
            .andExpect(jsonPath("$.[*].otpExpiryTime").value(hasItem(DEFAULT_OTP_EXPIRY_TIME)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPolicyUsersShouldNotBeFound(String filter) throws Exception {
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPolicyUsersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPolicyUsers() throws Exception {
        // Get the policyUsers
        restPolicyUsersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPolicyUsers() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();

        // Update the policyUsers
        PolicyUsers updatedPolicyUsers = policyUsersRepository.findById(policyUsers.getId()).get();
        // Disconnect from session so that the updates on updatedPolicyUsers are not directly saved in db
        em.detach(updatedPolicyUsers);
        updatedPolicyUsers
            .groupCode(UPDATED_GROUP_CODE)
            .groupHeadName(UPDATED_GROUP_HEAD_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .marriageDate(UPDATED_MARRIAGE_DATE)
            .userTypeId(UPDATED_USER_TYPE_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .activated(UPDATED_ACTIVATED)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .aadharCardNuber(UPDATED_AADHAR_CARD_NUBER)
            .pancardNumber(UPDATED_PANCARD_NUMBER)
            .oneTimePassword(UPDATED_ONE_TIME_PASSWORD)
            .otpExpiryTime(UPDATED_OTP_EXPIRY_TIME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(updatedPolicyUsers);

        restPolicyUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsers testPolicyUsers = policyUsersList.get(policyUsersList.size() - 1);
        assertThat(testPolicyUsers.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testPolicyUsers.getGroupHeadName()).isEqualTo(UPDATED_GROUP_HEAD_NAME);
        assertThat(testPolicyUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPolicyUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPolicyUsers.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPolicyUsers.getMarriageDate()).isEqualTo(UPDATED_MARRIAGE_DATE);
        assertThat(testPolicyUsers.getUserTypeId()).isEqualTo(UPDATED_USER_TYPE_ID);
        assertThat(testPolicyUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPolicyUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPolicyUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolicyUsers.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPolicyUsers.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicyUsers.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPolicyUsers.getLicenceExpiryDate()).isEqualTo(UPDATED_LICENCE_EXPIRY_DATE);
        assertThat(testPolicyUsers.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testPolicyUsers.getAadharCardNuber()).isEqualTo(UPDATED_AADHAR_CARD_NUBER);
        assertThat(testPolicyUsers.getPancardNumber()).isEqualTo(UPDATED_PANCARD_NUMBER);
        assertThat(testPolicyUsers.getOneTimePassword()).isEqualTo(UPDATED_ONE_TIME_PASSWORD);
        assertThat(testPolicyUsers.getOtpExpiryTime()).isEqualTo(UPDATED_OTP_EXPIRY_TIME);
        assertThat(testPolicyUsers.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicyUsers.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyUsersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyUsersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolicyUsersWithPatch() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();

        // Update the policyUsers using partial update
        PolicyUsers partialUpdatedPolicyUsers = new PolicyUsers();
        partialUpdatedPolicyUsers.setId(policyUsers.getId());

        partialUpdatedPolicyUsers
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userTypeId(UPDATED_USER_TYPE_ID)
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .aadharCardNuber(UPDATED_AADHAR_CARD_NUBER)
            .pancardNumber(UPDATED_PANCARD_NUMBER);

        restPolicyUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicyUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicyUsers))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsers testPolicyUsers = policyUsersList.get(policyUsersList.size() - 1);
        assertThat(testPolicyUsers.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testPolicyUsers.getGroupHeadName()).isEqualTo(DEFAULT_GROUP_HEAD_NAME);
        assertThat(testPolicyUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPolicyUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPolicyUsers.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testPolicyUsers.getMarriageDate()).isEqualTo(DEFAULT_MARRIAGE_DATE);
        assertThat(testPolicyUsers.getUserTypeId()).isEqualTo(UPDATED_USER_TYPE_ID);
        assertThat(testPolicyUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPolicyUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPolicyUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolicyUsers.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testPolicyUsers.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicyUsers.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPolicyUsers.getLicenceExpiryDate()).isEqualTo(UPDATED_LICENCE_EXPIRY_DATE);
        assertThat(testPolicyUsers.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testPolicyUsers.getAadharCardNuber()).isEqualTo(UPDATED_AADHAR_CARD_NUBER);
        assertThat(testPolicyUsers.getPancardNumber()).isEqualTo(UPDATED_PANCARD_NUMBER);
        assertThat(testPolicyUsers.getOneTimePassword()).isEqualTo(DEFAULT_ONE_TIME_PASSWORD);
        assertThat(testPolicyUsers.getOtpExpiryTime()).isEqualTo(DEFAULT_OTP_EXPIRY_TIME);
        assertThat(testPolicyUsers.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicyUsers.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePolicyUsersWithPatch() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();

        // Update the policyUsers using partial update
        PolicyUsers partialUpdatedPolicyUsers = new PolicyUsers();
        partialUpdatedPolicyUsers.setId(policyUsers.getId());

        partialUpdatedPolicyUsers
            .groupCode(UPDATED_GROUP_CODE)
            .groupHeadName(UPDATED_GROUP_HEAD_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .marriageDate(UPDATED_MARRIAGE_DATE)
            .userTypeId(UPDATED_USER_TYPE_ID)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .imageUrl(UPDATED_IMAGE_URL)
            .status(UPDATED_STATUS)
            .activated(UPDATED_ACTIVATED)
            .licenceExpiryDate(UPDATED_LICENCE_EXPIRY_DATE)
            .mobileNo(UPDATED_MOBILE_NO)
            .aadharCardNuber(UPDATED_AADHAR_CARD_NUBER)
            .pancardNumber(UPDATED_PANCARD_NUMBER)
            .oneTimePassword(UPDATED_ONE_TIME_PASSWORD)
            .otpExpiryTime(UPDATED_OTP_EXPIRY_TIME)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPolicyUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicyUsers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicyUsers))
            )
            .andExpect(status().isOk());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
        PolicyUsers testPolicyUsers = policyUsersList.get(policyUsersList.size() - 1);
        assertThat(testPolicyUsers.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testPolicyUsers.getGroupHeadName()).isEqualTo(UPDATED_GROUP_HEAD_NAME);
        assertThat(testPolicyUsers.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testPolicyUsers.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testPolicyUsers.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testPolicyUsers.getMarriageDate()).isEqualTo(UPDATED_MARRIAGE_DATE);
        assertThat(testPolicyUsers.getUserTypeId()).isEqualTo(UPDATED_USER_TYPE_ID);
        assertThat(testPolicyUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testPolicyUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPolicyUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolicyUsers.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPolicyUsers.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicyUsers.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPolicyUsers.getLicenceExpiryDate()).isEqualTo(UPDATED_LICENCE_EXPIRY_DATE);
        assertThat(testPolicyUsers.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testPolicyUsers.getAadharCardNuber()).isEqualTo(UPDATED_AADHAR_CARD_NUBER);
        assertThat(testPolicyUsers.getPancardNumber()).isEqualTo(UPDATED_PANCARD_NUMBER);
        assertThat(testPolicyUsers.getOneTimePassword()).isEqualTo(UPDATED_ONE_TIME_PASSWORD);
        assertThat(testPolicyUsers.getOtpExpiryTime()).isEqualTo(UPDATED_OTP_EXPIRY_TIME);
        assertThat(testPolicyUsers.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicyUsers.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, policyUsersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolicyUsers() throws Exception {
        int databaseSizeBeforeUpdate = policyUsersRepository.findAll().size();
        policyUsers.setId(count.incrementAndGet());

        // Create the PolicyUsers
        PolicyUsersDTO policyUsersDTO = policyUsersMapper.toDto(policyUsers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyUsersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(policyUsersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PolicyUsers in the database
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolicyUsers() throws Exception {
        // Initialize the database
        policyUsersRepository.saveAndFlush(policyUsers);

        int databaseSizeBeforeDelete = policyUsersRepository.findAll().size();

        // Delete the policyUsers
        restPolicyUsersMockMvc
            .perform(delete(ENTITY_API_URL_ID, policyUsers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PolicyUsers> policyUsersList = policyUsersRepository.findAll();
        assertThat(policyUsersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
