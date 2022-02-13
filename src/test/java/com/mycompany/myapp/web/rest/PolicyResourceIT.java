package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Agency;
import com.mycompany.myapp.domain.BankDetails;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.domain.Nominee;
import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.domain.PolicyUsers;
import com.mycompany.myapp.domain.PremiunDetails;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.VehicleClass;
import com.mycompany.myapp.domain.enumeration.PolicyStatus;
import com.mycompany.myapp.domain.enumeration.PolicyType;
import com.mycompany.myapp.domain.enumeration.PremiumMode;
import com.mycompany.myapp.domain.enumeration.Zone;
import com.mycompany.myapp.repository.PolicyRepository;
import com.mycompany.myapp.service.criteria.PolicyCriteria;
import com.mycompany.myapp.service.dto.PolicyDTO;
import com.mycompany.myapp.service.mapper.PolicyMapper;
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
 * Integration tests for the {@link PolicyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PolicyResourceIT {

    private static final Long DEFAULT_POLICY_AMOUNT = 1L;
    private static final Long UPDATED_POLICY_AMOUNT = 2L;
    private static final Long SMALLER_POLICY_AMOUNT = 1L - 1L;

    private static final String DEFAULT_POLICY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_TERM = 1L;
    private static final Long UPDATED_TERM = 2L;
    private static final Long SMALLER_TERM = 1L - 1L;

    private static final Long DEFAULT_PPT = 1L;
    private static final Long UPDATED_PPT = 2L;
    private static final Long SMALLER_PPT = 1L - 1L;

    private static final String DEFAULT_COMM_DATE = "AAAAAAAAAA";
    private static final String UPDATED_COMM_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSER_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_SUM_ASSURED_AMOUNT = 1L;
    private static final Long UPDATED_SUM_ASSURED_AMOUNT = 2L;
    private static final Long SMALLER_SUM_ASSURED_AMOUNT = 1L - 1L;

    private static final PremiumMode DEFAULT_PREMIUM_MODE = PremiumMode.YEARLY;
    private static final PremiumMode UPDATED_PREMIUM_MODE = PremiumMode.HLY;

    private static final Long DEFAULT_BASIC_PREMIUM = 1L;
    private static final Long UPDATED_BASIC_PREMIUM = 2L;
    private static final Long SMALLER_BASIC_PREMIUM = 1L - 1L;

    private static final Long DEFAULT_EXTRA_PREMIUM = 1L;
    private static final Long UPDATED_EXTRA_PREMIUM = 2L;
    private static final Long SMALLER_EXTRA_PREMIUM = 1L - 1L;

    private static final String DEFAULT_GST = "AAAAAAAAAA";
    private static final String UPDATED_GST = "BBBBBBBBBB";

    private static final PolicyStatus DEFAULT_STATUS = PolicyStatus.OPEN;
    private static final PolicyStatus UPDATED_STATUS = PolicyStatus.INFORCE;

    private static final String DEFAULT_TOTAL_PREMIUN = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_PREMIUN = "BBBBBBBBBB";

    private static final String DEFAULT_GST_FIRST_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_GST_FIRST_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_NET_PREMIUM = "AAAAAAAAAA";
    private static final String UPDATED_NET_PREMIUM = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_BENEFICIARY = "AAAAAAAAAA";
    private static final String UPDATED_TAX_BENEFICIARY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POLICY_RECEIVED = false;
    private static final Boolean UPDATED_POLICY_RECEIVED = true;

    private static final Long DEFAULT_PREVIOUS_POLICY = 1L;
    private static final Long UPDATED_PREVIOUS_POLICY = 2L;
    private static final Long SMALLER_PREVIOUS_POLICY = 1L - 1L;

    private static final String DEFAULT_POLICY_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_POLICY_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_POLICY_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_PERIOD = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CLAIM_DONE = false;
    private static final Boolean UPDATED_CLAIM_DONE = true;

    private static final Boolean DEFAULT_FREE_HEATH_CHECKUP = false;
    private static final Boolean UPDATED_FREE_HEATH_CHECKUP = true;

    private static final Zone DEFAULT_ZONE = Zone.A;
    private static final Zone UPDATED_ZONE = Zone.B;

    private static final Long DEFAULT_NO_OF_YEAR = 1L;
    private static final Long UPDATED_NO_OF_YEAR = 2L;
    private static final Long SMALLER_NO_OF_YEAR = 1L - 1L;

    private static final String DEFAULT_FLOATER_SUM = "AAAAAAAAAA";
    private static final String UPDATED_FLOATER_SUM = "BBBBBBBBBB";

    private static final String DEFAULT_TPA = "AAAAAAAAAA";
    private static final String UPDATED_TPA = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DATE = "BBBBBBBBBB";

    private static final PolicyType DEFAULT_POLICY_TYPE = PolicyType.LIFE;
    private static final PolicyType UPDATED_POLICY_TYPE = PolicyType.HEATH;

    private static final String DEFAULT_PA_TO_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_PA_TO_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_PA_TO_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_PA_TO_OTHER = "BBBBBBBBBB";

    private static final Long DEFAULT_LOADING = 1L;
    private static final Long UPDATED_LOADING = 2L;
    private static final Long SMALLER_LOADING = 1L - 1L;

    private static final String DEFAULT_RISK_COVERED_FROM = "AAAAAAAAAA";
    private static final String UPDATED_RISK_COVERED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_RISK_COVERED_TO = "AAAAAAAAAA";
    private static final String UPDATED_RISK_COVERED_TO = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_MATURITY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_MATURITY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_UIN_NO = "AAAAAAAAAA";
    private static final String UPDATED_UIN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/policies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private PolicyMapper policyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPolicyMockMvc;

    private Policy policy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createEntity(EntityManager em) {
        Policy policy = new Policy()
            .policyAmount(DEFAULT_POLICY_AMOUNT)
            .policyNumber(DEFAULT_POLICY_NUMBER)
            .term(DEFAULT_TERM)
            .ppt(DEFAULT_PPT)
            .commDate(DEFAULT_COMM_DATE)
            .proposerName(DEFAULT_PROPOSER_NAME)
            .sumAssuredAmount(DEFAULT_SUM_ASSURED_AMOUNT)
            .premiumMode(DEFAULT_PREMIUM_MODE)
            .basicPremium(DEFAULT_BASIC_PREMIUM)
            .extraPremium(DEFAULT_EXTRA_PREMIUM)
            .gst(DEFAULT_GST)
            .status(DEFAULT_STATUS)
            .totalPremiun(DEFAULT_TOTAL_PREMIUN)
            .gstFirstYear(DEFAULT_GST_FIRST_YEAR)
            .netPremium(DEFAULT_NET_PREMIUM)
            .taxBeneficiary(DEFAULT_TAX_BENEFICIARY)
            .policyReceived(DEFAULT_POLICY_RECEIVED)
            .previousPolicy(DEFAULT_PREVIOUS_POLICY)
            .policyStartDate(DEFAULT_POLICY_START_DATE)
            .policyEndDate(DEFAULT_POLICY_END_DATE)
            .period(DEFAULT_PERIOD)
            .claimDone(DEFAULT_CLAIM_DONE)
            .freeHeathCheckup(DEFAULT_FREE_HEATH_CHECKUP)
            .zone(DEFAULT_ZONE)
            .noOfYear(DEFAULT_NO_OF_YEAR)
            .floaterSum(DEFAULT_FLOATER_SUM)
            .tpa(DEFAULT_TPA)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .policyType(DEFAULT_POLICY_TYPE)
            .paToOwner(DEFAULT_PA_TO_OWNER)
            .paToOther(DEFAULT_PA_TO_OTHER)
            .loading(DEFAULT_LOADING)
            .riskCoveredFrom(DEFAULT_RISK_COVERED_FROM)
            .riskCoveredTo(DEFAULT_RISK_COVERED_TO)
            .notes(DEFAULT_NOTES)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .maturityDate(DEFAULT_MATURITY_DATE)
            .uinNo(DEFAULT_UIN_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return policy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Policy createUpdatedEntity(EntityManager em) {
        Policy policy = new Policy()
            .policyAmount(UPDATED_POLICY_AMOUNT)
            .policyNumber(UPDATED_POLICY_NUMBER)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .commDate(UPDATED_COMM_DATE)
            .proposerName(UPDATED_PROPOSER_NAME)
            .sumAssuredAmount(UPDATED_SUM_ASSURED_AMOUNT)
            .premiumMode(UPDATED_PREMIUM_MODE)
            .basicPremium(UPDATED_BASIC_PREMIUM)
            .extraPremium(UPDATED_EXTRA_PREMIUM)
            .gst(UPDATED_GST)
            .status(UPDATED_STATUS)
            .totalPremiun(UPDATED_TOTAL_PREMIUN)
            .gstFirstYear(UPDATED_GST_FIRST_YEAR)
            .netPremium(UPDATED_NET_PREMIUM)
            .taxBeneficiary(UPDATED_TAX_BENEFICIARY)
            .policyReceived(UPDATED_POLICY_RECEIVED)
            .previousPolicy(UPDATED_PREVIOUS_POLICY)
            .policyStartDate(UPDATED_POLICY_START_DATE)
            .policyEndDate(UPDATED_POLICY_END_DATE)
            .period(UPDATED_PERIOD)
            .claimDone(UPDATED_CLAIM_DONE)
            .freeHeathCheckup(UPDATED_FREE_HEATH_CHECKUP)
            .zone(UPDATED_ZONE)
            .noOfYear(UPDATED_NO_OF_YEAR)
            .floaterSum(UPDATED_FLOATER_SUM)
            .tpa(UPDATED_TPA)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .policyType(UPDATED_POLICY_TYPE)
            .paToOwner(UPDATED_PA_TO_OWNER)
            .paToOther(UPDATED_PA_TO_OTHER)
            .loading(UPDATED_LOADING)
            .riskCoveredFrom(UPDATED_RISK_COVERED_FROM)
            .riskCoveredTo(UPDATED_RISK_COVERED_TO)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .maturityDate(UPDATED_MATURITY_DATE)
            .uinNo(UPDATED_UIN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return policy;
    }

    @BeforeEach
    public void initTest() {
        policy = createEntity(em);
    }

    @Test
    @Transactional
    void createPolicy() throws Exception {
        int databaseSizeBeforeCreate = policyRepository.findAll().size();
        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isCreated());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate + 1);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyAmount()).isEqualTo(DEFAULT_POLICY_AMOUNT);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testPolicy.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testPolicy.getPpt()).isEqualTo(DEFAULT_PPT);
        assertThat(testPolicy.getCommDate()).isEqualTo(DEFAULT_COMM_DATE);
        assertThat(testPolicy.getProposerName()).isEqualTo(DEFAULT_PROPOSER_NAME);
        assertThat(testPolicy.getSumAssuredAmount()).isEqualTo(DEFAULT_SUM_ASSURED_AMOUNT);
        assertThat(testPolicy.getPremiumMode()).isEqualTo(DEFAULT_PREMIUM_MODE);
        assertThat(testPolicy.getBasicPremium()).isEqualTo(DEFAULT_BASIC_PREMIUM);
        assertThat(testPolicy.getExtraPremium()).isEqualTo(DEFAULT_EXTRA_PREMIUM);
        assertThat(testPolicy.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testPolicy.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPolicy.getTotalPremiun()).isEqualTo(DEFAULT_TOTAL_PREMIUN);
        assertThat(testPolicy.getGstFirstYear()).isEqualTo(DEFAULT_GST_FIRST_YEAR);
        assertThat(testPolicy.getNetPremium()).isEqualTo(DEFAULT_NET_PREMIUM);
        assertThat(testPolicy.getTaxBeneficiary()).isEqualTo(DEFAULT_TAX_BENEFICIARY);
        assertThat(testPolicy.getPolicyReceived()).isEqualTo(DEFAULT_POLICY_RECEIVED);
        assertThat(testPolicy.getPreviousPolicy()).isEqualTo(DEFAULT_PREVIOUS_POLICY);
        assertThat(testPolicy.getPolicyStartDate()).isEqualTo(DEFAULT_POLICY_START_DATE);
        assertThat(testPolicy.getPolicyEndDate()).isEqualTo(DEFAULT_POLICY_END_DATE);
        assertThat(testPolicy.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testPolicy.getClaimDone()).isEqualTo(DEFAULT_CLAIM_DONE);
        assertThat(testPolicy.getFreeHeathCheckup()).isEqualTo(DEFAULT_FREE_HEATH_CHECKUP);
        assertThat(testPolicy.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testPolicy.getNoOfYear()).isEqualTo(DEFAULT_NO_OF_YEAR);
        assertThat(testPolicy.getFloaterSum()).isEqualTo(DEFAULT_FLOATER_SUM);
        assertThat(testPolicy.getTpa()).isEqualTo(DEFAULT_TPA);
        assertThat(testPolicy.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPolicy.getPolicyType()).isEqualTo(DEFAULT_POLICY_TYPE);
        assertThat(testPolicy.getPaToOwner()).isEqualTo(DEFAULT_PA_TO_OWNER);
        assertThat(testPolicy.getPaToOther()).isEqualTo(DEFAULT_PA_TO_OTHER);
        assertThat(testPolicy.getLoading()).isEqualTo(DEFAULT_LOADING);
        assertThat(testPolicy.getRiskCoveredFrom()).isEqualTo(DEFAULT_RISK_COVERED_FROM);
        assertThat(testPolicy.getRiskCoveredTo()).isEqualTo(DEFAULT_RISK_COVERED_TO);
        assertThat(testPolicy.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPolicy.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPolicy.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPolicy.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPolicy.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testPolicy.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testPolicy.getMaturityDate()).isEqualTo(DEFAULT_MATURITY_DATE);
        assertThat(testPolicy.getUinNo()).isEqualTo(DEFAULT_UIN_NO);
        assertThat(testPolicy.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicy.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPolicyWithExistingId() throws Exception {
        // Create the Policy with an existing ID
        policy.setId(1L);
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        int databaseSizeBeforeCreate = policyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setCommDate(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaturityDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setMaturityDate(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setLastModified(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = policyRepository.findAll().size();
        // set the field null
        policy.setLastModifiedBy(null);

        // Create the Policy, which fails.
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        restPolicyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isBadRequest());

        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPolicies() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyAmount").value(hasItem(DEFAULT_POLICY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.intValue())))
            .andExpect(jsonPath("$.[*].ppt").value(hasItem(DEFAULT_PPT.intValue())))
            .andExpect(jsonPath("$.[*].commDate").value(hasItem(DEFAULT_COMM_DATE)))
            .andExpect(jsonPath("$.[*].proposerName").value(hasItem(DEFAULT_PROPOSER_NAME)))
            .andExpect(jsonPath("$.[*].sumAssuredAmount").value(hasItem(DEFAULT_SUM_ASSURED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].premiumMode").value(hasItem(DEFAULT_PREMIUM_MODE.toString())))
            .andExpect(jsonPath("$.[*].basicPremium").value(hasItem(DEFAULT_BASIC_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].extraPremium").value(hasItem(DEFAULT_EXTRA_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalPremiun").value(hasItem(DEFAULT_TOTAL_PREMIUN)))
            .andExpect(jsonPath("$.[*].gstFirstYear").value(hasItem(DEFAULT_GST_FIRST_YEAR)))
            .andExpect(jsonPath("$.[*].netPremium").value(hasItem(DEFAULT_NET_PREMIUM)))
            .andExpect(jsonPath("$.[*].taxBeneficiary").value(hasItem(DEFAULT_TAX_BENEFICIARY)))
            .andExpect(jsonPath("$.[*].policyReceived").value(hasItem(DEFAULT_POLICY_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].previousPolicy").value(hasItem(DEFAULT_PREVIOUS_POLICY.intValue())))
            .andExpect(jsonPath("$.[*].policyStartDate").value(hasItem(DEFAULT_POLICY_START_DATE)))
            .andExpect(jsonPath("$.[*].policyEndDate").value(hasItem(DEFAULT_POLICY_END_DATE)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].claimDone").value(hasItem(DEFAULT_CLAIM_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].freeHeathCheckup").value(hasItem(DEFAULT_FREE_HEATH_CHECKUP.booleanValue())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
            .andExpect(jsonPath("$.[*].noOfYear").value(hasItem(DEFAULT_NO_OF_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].floaterSum").value(hasItem(DEFAULT_FLOATER_SUM)))
            .andExpect(jsonPath("$.[*].tpa").value(hasItem(DEFAULT_TPA)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.[*].policyType").value(hasItem(DEFAULT_POLICY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paToOwner").value(hasItem(DEFAULT_PA_TO_OWNER)))
            .andExpect(jsonPath("$.[*].paToOther").value(hasItem(DEFAULT_PA_TO_OTHER)))
            .andExpect(jsonPath("$.[*].loading").value(hasItem(DEFAULT_LOADING.intValue())))
            .andExpect(jsonPath("$.[*].riskCoveredFrom").value(hasItem(DEFAULT_RISK_COVERED_FROM)))
            .andExpect(jsonPath("$.[*].riskCoveredTo").value(hasItem(DEFAULT_RISK_COVERED_TO)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE)))
            .andExpect(jsonPath("$.[*].uinNo").value(hasItem(DEFAULT_UIN_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get the policy
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL_ID, policy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(policy.getId().intValue()))
            .andExpect(jsonPath("$.policyAmount").value(DEFAULT_POLICY_AMOUNT.intValue()))
            .andExpect(jsonPath("$.policyNumber").value(DEFAULT_POLICY_NUMBER))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM.intValue()))
            .andExpect(jsonPath("$.ppt").value(DEFAULT_PPT.intValue()))
            .andExpect(jsonPath("$.commDate").value(DEFAULT_COMM_DATE))
            .andExpect(jsonPath("$.proposerName").value(DEFAULT_PROPOSER_NAME))
            .andExpect(jsonPath("$.sumAssuredAmount").value(DEFAULT_SUM_ASSURED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.premiumMode").value(DEFAULT_PREMIUM_MODE.toString()))
            .andExpect(jsonPath("$.basicPremium").value(DEFAULT_BASIC_PREMIUM.intValue()))
            .andExpect(jsonPath("$.extraPremium").value(DEFAULT_EXTRA_PREMIUM.intValue()))
            .andExpect(jsonPath("$.gst").value(DEFAULT_GST))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.totalPremiun").value(DEFAULT_TOTAL_PREMIUN))
            .andExpect(jsonPath("$.gstFirstYear").value(DEFAULT_GST_FIRST_YEAR))
            .andExpect(jsonPath("$.netPremium").value(DEFAULT_NET_PREMIUM))
            .andExpect(jsonPath("$.taxBeneficiary").value(DEFAULT_TAX_BENEFICIARY))
            .andExpect(jsonPath("$.policyReceived").value(DEFAULT_POLICY_RECEIVED.booleanValue()))
            .andExpect(jsonPath("$.previousPolicy").value(DEFAULT_PREVIOUS_POLICY.intValue()))
            .andExpect(jsonPath("$.policyStartDate").value(DEFAULT_POLICY_START_DATE))
            .andExpect(jsonPath("$.policyEndDate").value(DEFAULT_POLICY_END_DATE))
            .andExpect(jsonPath("$.period").value(DEFAULT_PERIOD))
            .andExpect(jsonPath("$.claimDone").value(DEFAULT_CLAIM_DONE.booleanValue()))
            .andExpect(jsonPath("$.freeHeathCheckup").value(DEFAULT_FREE_HEATH_CHECKUP.booleanValue()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE.toString()))
            .andExpect(jsonPath("$.noOfYear").value(DEFAULT_NO_OF_YEAR.intValue()))
            .andExpect(jsonPath("$.floaterSum").value(DEFAULT_FLOATER_SUM))
            .andExpect(jsonPath("$.tpa").value(DEFAULT_TPA))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE))
            .andExpect(jsonPath("$.policyType").value(DEFAULT_POLICY_TYPE.toString()))
            .andExpect(jsonPath("$.paToOwner").value(DEFAULT_PA_TO_OWNER))
            .andExpect(jsonPath("$.paToOther").value(DEFAULT_PA_TO_OTHER))
            .andExpect(jsonPath("$.loading").value(DEFAULT_LOADING.intValue()))
            .andExpect(jsonPath("$.riskCoveredFrom").value(DEFAULT_RISK_COVERED_FROM))
            .andExpect(jsonPath("$.riskCoveredTo").value(DEFAULT_RISK_COVERED_TO))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.maturityDate").value(DEFAULT_MATURITY_DATE))
            .andExpect(jsonPath("$.uinNo").value(DEFAULT_UIN_NO))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPoliciesByIdFiltering() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        Long id = policy.getId();

        defaultPolicyShouldBeFound("id.equals=" + id);
        defaultPolicyShouldNotBeFound("id.notEquals=" + id);

        defaultPolicyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPolicyShouldNotBeFound("id.greaterThan=" + id);

        defaultPolicyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPolicyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount equals to DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.equals=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount equals to UPDATED_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.equals=" + UPDATED_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount not equals to DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.notEquals=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount not equals to UPDATED_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.notEquals=" + UPDATED_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount in DEFAULT_POLICY_AMOUNT or UPDATED_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.in=" + DEFAULT_POLICY_AMOUNT + "," + UPDATED_POLICY_AMOUNT);

        // Get all the policyList where policyAmount equals to UPDATED_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.in=" + UPDATED_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount is not null
        defaultPolicyShouldBeFound("policyAmount.specified=true");

        // Get all the policyList where policyAmount is null
        defaultPolicyShouldNotBeFound("policyAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount is greater than or equal to DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.greaterThanOrEqual=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount is greater than or equal to UPDATED_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.greaterThanOrEqual=" + UPDATED_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount is less than or equal to DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.lessThanOrEqual=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount is less than or equal to SMALLER_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.lessThanOrEqual=" + SMALLER_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount is less than DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.lessThan=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount is less than UPDATED_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.lessThan=" + UPDATED_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyAmount is greater than DEFAULT_POLICY_AMOUNT
        defaultPolicyShouldNotBeFound("policyAmount.greaterThan=" + DEFAULT_POLICY_AMOUNT);

        // Get all the policyList where policyAmount is greater than SMALLER_POLICY_AMOUNT
        defaultPolicyShouldBeFound("policyAmount.greaterThan=" + SMALLER_POLICY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber equals to DEFAULT_POLICY_NUMBER
        defaultPolicyShouldBeFound("policyNumber.equals=" + DEFAULT_POLICY_NUMBER);

        // Get all the policyList where policyNumber equals to UPDATED_POLICY_NUMBER
        defaultPolicyShouldNotBeFound("policyNumber.equals=" + UPDATED_POLICY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber not equals to DEFAULT_POLICY_NUMBER
        defaultPolicyShouldNotBeFound("policyNumber.notEquals=" + DEFAULT_POLICY_NUMBER);

        // Get all the policyList where policyNumber not equals to UPDATED_POLICY_NUMBER
        defaultPolicyShouldBeFound("policyNumber.notEquals=" + UPDATED_POLICY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber in DEFAULT_POLICY_NUMBER or UPDATED_POLICY_NUMBER
        defaultPolicyShouldBeFound("policyNumber.in=" + DEFAULT_POLICY_NUMBER + "," + UPDATED_POLICY_NUMBER);

        // Get all the policyList where policyNumber equals to UPDATED_POLICY_NUMBER
        defaultPolicyShouldNotBeFound("policyNumber.in=" + UPDATED_POLICY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber is not null
        defaultPolicyShouldBeFound("policyNumber.specified=true");

        // Get all the policyList where policyNumber is null
        defaultPolicyShouldNotBeFound("policyNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber contains DEFAULT_POLICY_NUMBER
        defaultPolicyShouldBeFound("policyNumber.contains=" + DEFAULT_POLICY_NUMBER);

        // Get all the policyList where policyNumber contains UPDATED_POLICY_NUMBER
        defaultPolicyShouldNotBeFound("policyNumber.contains=" + UPDATED_POLICY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyNumberNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyNumber does not contain DEFAULT_POLICY_NUMBER
        defaultPolicyShouldNotBeFound("policyNumber.doesNotContain=" + DEFAULT_POLICY_NUMBER);

        // Get all the policyList where policyNumber does not contain UPDATED_POLICY_NUMBER
        defaultPolicyShouldBeFound("policyNumber.doesNotContain=" + UPDATED_POLICY_NUMBER);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term equals to DEFAULT_TERM
        defaultPolicyShouldBeFound("term.equals=" + DEFAULT_TERM);

        // Get all the policyList where term equals to UPDATED_TERM
        defaultPolicyShouldNotBeFound("term.equals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term not equals to DEFAULT_TERM
        defaultPolicyShouldNotBeFound("term.notEquals=" + DEFAULT_TERM);

        // Get all the policyList where term not equals to UPDATED_TERM
        defaultPolicyShouldBeFound("term.notEquals=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term in DEFAULT_TERM or UPDATED_TERM
        defaultPolicyShouldBeFound("term.in=" + DEFAULT_TERM + "," + UPDATED_TERM);

        // Get all the policyList where term equals to UPDATED_TERM
        defaultPolicyShouldNotBeFound("term.in=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term is not null
        defaultPolicyShouldBeFound("term.specified=true");

        // Get all the policyList where term is null
        defaultPolicyShouldNotBeFound("term.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term is greater than or equal to DEFAULT_TERM
        defaultPolicyShouldBeFound("term.greaterThanOrEqual=" + DEFAULT_TERM);

        // Get all the policyList where term is greater than or equal to UPDATED_TERM
        defaultPolicyShouldNotBeFound("term.greaterThanOrEqual=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term is less than or equal to DEFAULT_TERM
        defaultPolicyShouldBeFound("term.lessThanOrEqual=" + DEFAULT_TERM);

        // Get all the policyList where term is less than or equal to SMALLER_TERM
        defaultPolicyShouldNotBeFound("term.lessThanOrEqual=" + SMALLER_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term is less than DEFAULT_TERM
        defaultPolicyShouldNotBeFound("term.lessThan=" + DEFAULT_TERM);

        // Get all the policyList where term is less than UPDATED_TERM
        defaultPolicyShouldBeFound("term.lessThan=" + UPDATED_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTermIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where term is greater than DEFAULT_TERM
        defaultPolicyShouldNotBeFound("term.greaterThan=" + DEFAULT_TERM);

        // Get all the policyList where term is greater than SMALLER_TERM
        defaultPolicyShouldBeFound("term.greaterThan=" + SMALLER_TERM);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt equals to DEFAULT_PPT
        defaultPolicyShouldBeFound("ppt.equals=" + DEFAULT_PPT);

        // Get all the policyList where ppt equals to UPDATED_PPT
        defaultPolicyShouldNotBeFound("ppt.equals=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt not equals to DEFAULT_PPT
        defaultPolicyShouldNotBeFound("ppt.notEquals=" + DEFAULT_PPT);

        // Get all the policyList where ppt not equals to UPDATED_PPT
        defaultPolicyShouldBeFound("ppt.notEquals=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt in DEFAULT_PPT or UPDATED_PPT
        defaultPolicyShouldBeFound("ppt.in=" + DEFAULT_PPT + "," + UPDATED_PPT);

        // Get all the policyList where ppt equals to UPDATED_PPT
        defaultPolicyShouldNotBeFound("ppt.in=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt is not null
        defaultPolicyShouldBeFound("ppt.specified=true");

        // Get all the policyList where ppt is null
        defaultPolicyShouldNotBeFound("ppt.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt is greater than or equal to DEFAULT_PPT
        defaultPolicyShouldBeFound("ppt.greaterThanOrEqual=" + DEFAULT_PPT);

        // Get all the policyList where ppt is greater than or equal to UPDATED_PPT
        defaultPolicyShouldNotBeFound("ppt.greaterThanOrEqual=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt is less than or equal to DEFAULT_PPT
        defaultPolicyShouldBeFound("ppt.lessThanOrEqual=" + DEFAULT_PPT);

        // Get all the policyList where ppt is less than or equal to SMALLER_PPT
        defaultPolicyShouldNotBeFound("ppt.lessThanOrEqual=" + SMALLER_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt is less than DEFAULT_PPT
        defaultPolicyShouldNotBeFound("ppt.lessThan=" + DEFAULT_PPT);

        // Get all the policyList where ppt is less than UPDATED_PPT
        defaultPolicyShouldBeFound("ppt.lessThan=" + UPDATED_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPptIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where ppt is greater than DEFAULT_PPT
        defaultPolicyShouldNotBeFound("ppt.greaterThan=" + DEFAULT_PPT);

        // Get all the policyList where ppt is greater than SMALLER_PPT
        defaultPolicyShouldBeFound("ppt.greaterThan=" + SMALLER_PPT);
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate equals to DEFAULT_COMM_DATE
        defaultPolicyShouldBeFound("commDate.equals=" + DEFAULT_COMM_DATE);

        // Get all the policyList where commDate equals to UPDATED_COMM_DATE
        defaultPolicyShouldNotBeFound("commDate.equals=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate not equals to DEFAULT_COMM_DATE
        defaultPolicyShouldNotBeFound("commDate.notEquals=" + DEFAULT_COMM_DATE);

        // Get all the policyList where commDate not equals to UPDATED_COMM_DATE
        defaultPolicyShouldBeFound("commDate.notEquals=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate in DEFAULT_COMM_DATE or UPDATED_COMM_DATE
        defaultPolicyShouldBeFound("commDate.in=" + DEFAULT_COMM_DATE + "," + UPDATED_COMM_DATE);

        // Get all the policyList where commDate equals to UPDATED_COMM_DATE
        defaultPolicyShouldNotBeFound("commDate.in=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate is not null
        defaultPolicyShouldBeFound("commDate.specified=true");

        // Get all the policyList where commDate is null
        defaultPolicyShouldNotBeFound("commDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate contains DEFAULT_COMM_DATE
        defaultPolicyShouldBeFound("commDate.contains=" + DEFAULT_COMM_DATE);

        // Get all the policyList where commDate contains UPDATED_COMM_DATE
        defaultPolicyShouldNotBeFound("commDate.contains=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByCommDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where commDate does not contain DEFAULT_COMM_DATE
        defaultPolicyShouldNotBeFound("commDate.doesNotContain=" + DEFAULT_COMM_DATE);

        // Get all the policyList where commDate does not contain UPDATED_COMM_DATE
        defaultPolicyShouldBeFound("commDate.doesNotContain=" + UPDATED_COMM_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName equals to DEFAULT_PROPOSER_NAME
        defaultPolicyShouldBeFound("proposerName.equals=" + DEFAULT_PROPOSER_NAME);

        // Get all the policyList where proposerName equals to UPDATED_PROPOSER_NAME
        defaultPolicyShouldNotBeFound("proposerName.equals=" + UPDATED_PROPOSER_NAME);
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName not equals to DEFAULT_PROPOSER_NAME
        defaultPolicyShouldNotBeFound("proposerName.notEquals=" + DEFAULT_PROPOSER_NAME);

        // Get all the policyList where proposerName not equals to UPDATED_PROPOSER_NAME
        defaultPolicyShouldBeFound("proposerName.notEquals=" + UPDATED_PROPOSER_NAME);
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName in DEFAULT_PROPOSER_NAME or UPDATED_PROPOSER_NAME
        defaultPolicyShouldBeFound("proposerName.in=" + DEFAULT_PROPOSER_NAME + "," + UPDATED_PROPOSER_NAME);

        // Get all the policyList where proposerName equals to UPDATED_PROPOSER_NAME
        defaultPolicyShouldNotBeFound("proposerName.in=" + UPDATED_PROPOSER_NAME);
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName is not null
        defaultPolicyShouldBeFound("proposerName.specified=true");

        // Get all the policyList where proposerName is null
        defaultPolicyShouldNotBeFound("proposerName.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName contains DEFAULT_PROPOSER_NAME
        defaultPolicyShouldBeFound("proposerName.contains=" + DEFAULT_PROPOSER_NAME);

        // Get all the policyList where proposerName contains UPDATED_PROPOSER_NAME
        defaultPolicyShouldNotBeFound("proposerName.contains=" + UPDATED_PROPOSER_NAME);
    }

    @Test
    @Transactional
    void getAllPoliciesByProposerNameNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where proposerName does not contain DEFAULT_PROPOSER_NAME
        defaultPolicyShouldNotBeFound("proposerName.doesNotContain=" + DEFAULT_PROPOSER_NAME);

        // Get all the policyList where proposerName does not contain UPDATED_PROPOSER_NAME
        defaultPolicyShouldBeFound("proposerName.doesNotContain=" + UPDATED_PROPOSER_NAME);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount equals to DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.equals=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount equals to UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.equals=" + UPDATED_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount not equals to DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.notEquals=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount not equals to UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.notEquals=" + UPDATED_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount in DEFAULT_SUM_ASSURED_AMOUNT or UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.in=" + DEFAULT_SUM_ASSURED_AMOUNT + "," + UPDATED_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount equals to UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.in=" + UPDATED_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount is not null
        defaultPolicyShouldBeFound("sumAssuredAmount.specified=true");

        // Get all the policyList where sumAssuredAmount is null
        defaultPolicyShouldNotBeFound("sumAssuredAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount is greater than or equal to DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.greaterThanOrEqual=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount is greater than or equal to UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.greaterThanOrEqual=" + UPDATED_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount is less than or equal to DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.lessThanOrEqual=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount is less than or equal to SMALLER_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.lessThanOrEqual=" + SMALLER_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount is less than DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.lessThan=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount is less than UPDATED_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.lessThan=" + UPDATED_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesBySumAssuredAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where sumAssuredAmount is greater than DEFAULT_SUM_ASSURED_AMOUNT
        defaultPolicyShouldNotBeFound("sumAssuredAmount.greaterThan=" + DEFAULT_SUM_ASSURED_AMOUNT);

        // Get all the policyList where sumAssuredAmount is greater than SMALLER_SUM_ASSURED_AMOUNT
        defaultPolicyShouldBeFound("sumAssuredAmount.greaterThan=" + SMALLER_SUM_ASSURED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPoliciesByPremiumModeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where premiumMode equals to DEFAULT_PREMIUM_MODE
        defaultPolicyShouldBeFound("premiumMode.equals=" + DEFAULT_PREMIUM_MODE);

        // Get all the policyList where premiumMode equals to UPDATED_PREMIUM_MODE
        defaultPolicyShouldNotBeFound("premiumMode.equals=" + UPDATED_PREMIUM_MODE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPremiumModeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where premiumMode not equals to DEFAULT_PREMIUM_MODE
        defaultPolicyShouldNotBeFound("premiumMode.notEquals=" + DEFAULT_PREMIUM_MODE);

        // Get all the policyList where premiumMode not equals to UPDATED_PREMIUM_MODE
        defaultPolicyShouldBeFound("premiumMode.notEquals=" + UPDATED_PREMIUM_MODE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPremiumModeIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where premiumMode in DEFAULT_PREMIUM_MODE or UPDATED_PREMIUM_MODE
        defaultPolicyShouldBeFound("premiumMode.in=" + DEFAULT_PREMIUM_MODE + "," + UPDATED_PREMIUM_MODE);

        // Get all the policyList where premiumMode equals to UPDATED_PREMIUM_MODE
        defaultPolicyShouldNotBeFound("premiumMode.in=" + UPDATED_PREMIUM_MODE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPremiumModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where premiumMode is not null
        defaultPolicyShouldBeFound("premiumMode.specified=true");

        // Get all the policyList where premiumMode is null
        defaultPolicyShouldNotBeFound("premiumMode.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium equals to DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.equals=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium equals to UPDATED_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.equals=" + UPDATED_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium not equals to DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.notEquals=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium not equals to UPDATED_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.notEquals=" + UPDATED_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium in DEFAULT_BASIC_PREMIUM or UPDATED_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.in=" + DEFAULT_BASIC_PREMIUM + "," + UPDATED_BASIC_PREMIUM);

        // Get all the policyList where basicPremium equals to UPDATED_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.in=" + UPDATED_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium is not null
        defaultPolicyShouldBeFound("basicPremium.specified=true");

        // Get all the policyList where basicPremium is null
        defaultPolicyShouldNotBeFound("basicPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium is greater than or equal to DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.greaterThanOrEqual=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium is greater than or equal to UPDATED_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.greaterThanOrEqual=" + UPDATED_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium is less than or equal to DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.lessThanOrEqual=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium is less than or equal to SMALLER_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.lessThanOrEqual=" + SMALLER_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium is less than DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.lessThan=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium is less than UPDATED_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.lessThan=" + UPDATED_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByBasicPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where basicPremium is greater than DEFAULT_BASIC_PREMIUM
        defaultPolicyShouldNotBeFound("basicPremium.greaterThan=" + DEFAULT_BASIC_PREMIUM);

        // Get all the policyList where basicPremium is greater than SMALLER_BASIC_PREMIUM
        defaultPolicyShouldBeFound("basicPremium.greaterThan=" + SMALLER_BASIC_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium equals to DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.equals=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium equals to UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.equals=" + UPDATED_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium not equals to DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.notEquals=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium not equals to UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.notEquals=" + UPDATED_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium in DEFAULT_EXTRA_PREMIUM or UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.in=" + DEFAULT_EXTRA_PREMIUM + "," + UPDATED_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium equals to UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.in=" + UPDATED_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium is not null
        defaultPolicyShouldBeFound("extraPremium.specified=true");

        // Get all the policyList where extraPremium is null
        defaultPolicyShouldNotBeFound("extraPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium is greater than or equal to DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.greaterThanOrEqual=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium is greater than or equal to UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.greaterThanOrEqual=" + UPDATED_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium is less than or equal to DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.lessThanOrEqual=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium is less than or equal to SMALLER_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.lessThanOrEqual=" + SMALLER_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium is less than DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.lessThan=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium is less than UPDATED_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.lessThan=" + UPDATED_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByExtraPremiumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where extraPremium is greater than DEFAULT_EXTRA_PREMIUM
        defaultPolicyShouldNotBeFound("extraPremium.greaterThan=" + DEFAULT_EXTRA_PREMIUM);

        // Get all the policyList where extraPremium is greater than SMALLER_EXTRA_PREMIUM
        defaultPolicyShouldBeFound("extraPremium.greaterThan=" + SMALLER_EXTRA_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst equals to DEFAULT_GST
        defaultPolicyShouldBeFound("gst.equals=" + DEFAULT_GST);

        // Get all the policyList where gst equals to UPDATED_GST
        defaultPolicyShouldNotBeFound("gst.equals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst not equals to DEFAULT_GST
        defaultPolicyShouldNotBeFound("gst.notEquals=" + DEFAULT_GST);

        // Get all the policyList where gst not equals to UPDATED_GST
        defaultPolicyShouldBeFound("gst.notEquals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst in DEFAULT_GST or UPDATED_GST
        defaultPolicyShouldBeFound("gst.in=" + DEFAULT_GST + "," + UPDATED_GST);

        // Get all the policyList where gst equals to UPDATED_GST
        defaultPolicyShouldNotBeFound("gst.in=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst is not null
        defaultPolicyShouldBeFound("gst.specified=true");

        // Get all the policyList where gst is null
        defaultPolicyShouldNotBeFound("gst.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByGstContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst contains DEFAULT_GST
        defaultPolicyShouldBeFound("gst.contains=" + DEFAULT_GST);

        // Get all the policyList where gst contains UPDATED_GST
        defaultPolicyShouldNotBeFound("gst.contains=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gst does not contain DEFAULT_GST
        defaultPolicyShouldNotBeFound("gst.doesNotContain=" + DEFAULT_GST);

        // Get all the policyList where gst does not contain UPDATED_GST
        defaultPolicyShouldBeFound("gst.doesNotContain=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllPoliciesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where status equals to DEFAULT_STATUS
        defaultPolicyShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the policyList where status equals to UPDATED_STATUS
        defaultPolicyShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPoliciesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where status not equals to DEFAULT_STATUS
        defaultPolicyShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the policyList where status not equals to UPDATED_STATUS
        defaultPolicyShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPoliciesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPolicyShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the policyList where status equals to UPDATED_STATUS
        defaultPolicyShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPoliciesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where status is not null
        defaultPolicyShouldBeFound("status.specified=true");

        // Get all the policyList where status is null
        defaultPolicyShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun equals to DEFAULT_TOTAL_PREMIUN
        defaultPolicyShouldBeFound("totalPremiun.equals=" + DEFAULT_TOTAL_PREMIUN);

        // Get all the policyList where totalPremiun equals to UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldNotBeFound("totalPremiun.equals=" + UPDATED_TOTAL_PREMIUN);
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun not equals to DEFAULT_TOTAL_PREMIUN
        defaultPolicyShouldNotBeFound("totalPremiun.notEquals=" + DEFAULT_TOTAL_PREMIUN);

        // Get all the policyList where totalPremiun not equals to UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldBeFound("totalPremiun.notEquals=" + UPDATED_TOTAL_PREMIUN);
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun in DEFAULT_TOTAL_PREMIUN or UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldBeFound("totalPremiun.in=" + DEFAULT_TOTAL_PREMIUN + "," + UPDATED_TOTAL_PREMIUN);

        // Get all the policyList where totalPremiun equals to UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldNotBeFound("totalPremiun.in=" + UPDATED_TOTAL_PREMIUN);
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun is not null
        defaultPolicyShouldBeFound("totalPremiun.specified=true");

        // Get all the policyList where totalPremiun is null
        defaultPolicyShouldNotBeFound("totalPremiun.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun contains DEFAULT_TOTAL_PREMIUN
        defaultPolicyShouldBeFound("totalPremiun.contains=" + DEFAULT_TOTAL_PREMIUN);

        // Get all the policyList where totalPremiun contains UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldNotBeFound("totalPremiun.contains=" + UPDATED_TOTAL_PREMIUN);
    }

    @Test
    @Transactional
    void getAllPoliciesByTotalPremiunNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where totalPremiun does not contain DEFAULT_TOTAL_PREMIUN
        defaultPolicyShouldNotBeFound("totalPremiun.doesNotContain=" + DEFAULT_TOTAL_PREMIUN);

        // Get all the policyList where totalPremiun does not contain UPDATED_TOTAL_PREMIUN
        defaultPolicyShouldBeFound("totalPremiun.doesNotContain=" + UPDATED_TOTAL_PREMIUN);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear equals to DEFAULT_GST_FIRST_YEAR
        defaultPolicyShouldBeFound("gstFirstYear.equals=" + DEFAULT_GST_FIRST_YEAR);

        // Get all the policyList where gstFirstYear equals to UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldNotBeFound("gstFirstYear.equals=" + UPDATED_GST_FIRST_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear not equals to DEFAULT_GST_FIRST_YEAR
        defaultPolicyShouldNotBeFound("gstFirstYear.notEquals=" + DEFAULT_GST_FIRST_YEAR);

        // Get all the policyList where gstFirstYear not equals to UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldBeFound("gstFirstYear.notEquals=" + UPDATED_GST_FIRST_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear in DEFAULT_GST_FIRST_YEAR or UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldBeFound("gstFirstYear.in=" + DEFAULT_GST_FIRST_YEAR + "," + UPDATED_GST_FIRST_YEAR);

        // Get all the policyList where gstFirstYear equals to UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldNotBeFound("gstFirstYear.in=" + UPDATED_GST_FIRST_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear is not null
        defaultPolicyShouldBeFound("gstFirstYear.specified=true");

        // Get all the policyList where gstFirstYear is null
        defaultPolicyShouldNotBeFound("gstFirstYear.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear contains DEFAULT_GST_FIRST_YEAR
        defaultPolicyShouldBeFound("gstFirstYear.contains=" + DEFAULT_GST_FIRST_YEAR);

        // Get all the policyList where gstFirstYear contains UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldNotBeFound("gstFirstYear.contains=" + UPDATED_GST_FIRST_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByGstFirstYearNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where gstFirstYear does not contain DEFAULT_GST_FIRST_YEAR
        defaultPolicyShouldNotBeFound("gstFirstYear.doesNotContain=" + DEFAULT_GST_FIRST_YEAR);

        // Get all the policyList where gstFirstYear does not contain UPDATED_GST_FIRST_YEAR
        defaultPolicyShouldBeFound("gstFirstYear.doesNotContain=" + UPDATED_GST_FIRST_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium equals to DEFAULT_NET_PREMIUM
        defaultPolicyShouldBeFound("netPremium.equals=" + DEFAULT_NET_PREMIUM);

        // Get all the policyList where netPremium equals to UPDATED_NET_PREMIUM
        defaultPolicyShouldNotBeFound("netPremium.equals=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium not equals to DEFAULT_NET_PREMIUM
        defaultPolicyShouldNotBeFound("netPremium.notEquals=" + DEFAULT_NET_PREMIUM);

        // Get all the policyList where netPremium not equals to UPDATED_NET_PREMIUM
        defaultPolicyShouldBeFound("netPremium.notEquals=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium in DEFAULT_NET_PREMIUM or UPDATED_NET_PREMIUM
        defaultPolicyShouldBeFound("netPremium.in=" + DEFAULT_NET_PREMIUM + "," + UPDATED_NET_PREMIUM);

        // Get all the policyList where netPremium equals to UPDATED_NET_PREMIUM
        defaultPolicyShouldNotBeFound("netPremium.in=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium is not null
        defaultPolicyShouldBeFound("netPremium.specified=true");

        // Get all the policyList where netPremium is null
        defaultPolicyShouldNotBeFound("netPremium.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium contains DEFAULT_NET_PREMIUM
        defaultPolicyShouldBeFound("netPremium.contains=" + DEFAULT_NET_PREMIUM);

        // Get all the policyList where netPremium contains UPDATED_NET_PREMIUM
        defaultPolicyShouldNotBeFound("netPremium.contains=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByNetPremiumNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where netPremium does not contain DEFAULT_NET_PREMIUM
        defaultPolicyShouldNotBeFound("netPremium.doesNotContain=" + DEFAULT_NET_PREMIUM);

        // Get all the policyList where netPremium does not contain UPDATED_NET_PREMIUM
        defaultPolicyShouldBeFound("netPremium.doesNotContain=" + UPDATED_NET_PREMIUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary equals to DEFAULT_TAX_BENEFICIARY
        defaultPolicyShouldBeFound("taxBeneficiary.equals=" + DEFAULT_TAX_BENEFICIARY);

        // Get all the policyList where taxBeneficiary equals to UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldNotBeFound("taxBeneficiary.equals=" + UPDATED_TAX_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary not equals to DEFAULT_TAX_BENEFICIARY
        defaultPolicyShouldNotBeFound("taxBeneficiary.notEquals=" + DEFAULT_TAX_BENEFICIARY);

        // Get all the policyList where taxBeneficiary not equals to UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldBeFound("taxBeneficiary.notEquals=" + UPDATED_TAX_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary in DEFAULT_TAX_BENEFICIARY or UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldBeFound("taxBeneficiary.in=" + DEFAULT_TAX_BENEFICIARY + "," + UPDATED_TAX_BENEFICIARY);

        // Get all the policyList where taxBeneficiary equals to UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldNotBeFound("taxBeneficiary.in=" + UPDATED_TAX_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary is not null
        defaultPolicyShouldBeFound("taxBeneficiary.specified=true");

        // Get all the policyList where taxBeneficiary is null
        defaultPolicyShouldNotBeFound("taxBeneficiary.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary contains DEFAULT_TAX_BENEFICIARY
        defaultPolicyShouldBeFound("taxBeneficiary.contains=" + DEFAULT_TAX_BENEFICIARY);

        // Get all the policyList where taxBeneficiary contains UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldNotBeFound("taxBeneficiary.contains=" + UPDATED_TAX_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllPoliciesByTaxBeneficiaryNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where taxBeneficiary does not contain DEFAULT_TAX_BENEFICIARY
        defaultPolicyShouldNotBeFound("taxBeneficiary.doesNotContain=" + DEFAULT_TAX_BENEFICIARY);

        // Get all the policyList where taxBeneficiary does not contain UPDATED_TAX_BENEFICIARY
        defaultPolicyShouldBeFound("taxBeneficiary.doesNotContain=" + UPDATED_TAX_BENEFICIARY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyReceived equals to DEFAULT_POLICY_RECEIVED
        defaultPolicyShouldBeFound("policyReceived.equals=" + DEFAULT_POLICY_RECEIVED);

        // Get all the policyList where policyReceived equals to UPDATED_POLICY_RECEIVED
        defaultPolicyShouldNotBeFound("policyReceived.equals=" + UPDATED_POLICY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyReceived not equals to DEFAULT_POLICY_RECEIVED
        defaultPolicyShouldNotBeFound("policyReceived.notEquals=" + DEFAULT_POLICY_RECEIVED);

        // Get all the policyList where policyReceived not equals to UPDATED_POLICY_RECEIVED
        defaultPolicyShouldBeFound("policyReceived.notEquals=" + UPDATED_POLICY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyReceived in DEFAULT_POLICY_RECEIVED or UPDATED_POLICY_RECEIVED
        defaultPolicyShouldBeFound("policyReceived.in=" + DEFAULT_POLICY_RECEIVED + "," + UPDATED_POLICY_RECEIVED);

        // Get all the policyList where policyReceived equals to UPDATED_POLICY_RECEIVED
        defaultPolicyShouldNotBeFound("policyReceived.in=" + UPDATED_POLICY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyReceived is not null
        defaultPolicyShouldBeFound("policyReceived.specified=true");

        // Get all the policyList where policyReceived is null
        defaultPolicyShouldNotBeFound("policyReceived.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy equals to DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.equals=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy equals to UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.equals=" + UPDATED_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy not equals to DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.notEquals=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy not equals to UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.notEquals=" + UPDATED_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy in DEFAULT_PREVIOUS_POLICY or UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.in=" + DEFAULT_PREVIOUS_POLICY + "," + UPDATED_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy equals to UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.in=" + UPDATED_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy is not null
        defaultPolicyShouldBeFound("previousPolicy.specified=true");

        // Get all the policyList where previousPolicy is null
        defaultPolicyShouldNotBeFound("previousPolicy.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy is greater than or equal to DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.greaterThanOrEqual=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy is greater than or equal to UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.greaterThanOrEqual=" + UPDATED_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy is less than or equal to DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.lessThanOrEqual=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy is less than or equal to SMALLER_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.lessThanOrEqual=" + SMALLER_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy is less than DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.lessThan=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy is less than UPDATED_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.lessThan=" + UPDATED_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPreviousPolicyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where previousPolicy is greater than DEFAULT_PREVIOUS_POLICY
        defaultPolicyShouldNotBeFound("previousPolicy.greaterThan=" + DEFAULT_PREVIOUS_POLICY);

        // Get all the policyList where previousPolicy is greater than SMALLER_PREVIOUS_POLICY
        defaultPolicyShouldBeFound("previousPolicy.greaterThan=" + SMALLER_PREVIOUS_POLICY);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate equals to DEFAULT_POLICY_START_DATE
        defaultPolicyShouldBeFound("policyStartDate.equals=" + DEFAULT_POLICY_START_DATE);

        // Get all the policyList where policyStartDate equals to UPDATED_POLICY_START_DATE
        defaultPolicyShouldNotBeFound("policyStartDate.equals=" + UPDATED_POLICY_START_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate not equals to DEFAULT_POLICY_START_DATE
        defaultPolicyShouldNotBeFound("policyStartDate.notEquals=" + DEFAULT_POLICY_START_DATE);

        // Get all the policyList where policyStartDate not equals to UPDATED_POLICY_START_DATE
        defaultPolicyShouldBeFound("policyStartDate.notEquals=" + UPDATED_POLICY_START_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate in DEFAULT_POLICY_START_DATE or UPDATED_POLICY_START_DATE
        defaultPolicyShouldBeFound("policyStartDate.in=" + DEFAULT_POLICY_START_DATE + "," + UPDATED_POLICY_START_DATE);

        // Get all the policyList where policyStartDate equals to UPDATED_POLICY_START_DATE
        defaultPolicyShouldNotBeFound("policyStartDate.in=" + UPDATED_POLICY_START_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate is not null
        defaultPolicyShouldBeFound("policyStartDate.specified=true");

        // Get all the policyList where policyStartDate is null
        defaultPolicyShouldNotBeFound("policyStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate contains DEFAULT_POLICY_START_DATE
        defaultPolicyShouldBeFound("policyStartDate.contains=" + DEFAULT_POLICY_START_DATE);

        // Get all the policyList where policyStartDate contains UPDATED_POLICY_START_DATE
        defaultPolicyShouldNotBeFound("policyStartDate.contains=" + UPDATED_POLICY_START_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyStartDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyStartDate does not contain DEFAULT_POLICY_START_DATE
        defaultPolicyShouldNotBeFound("policyStartDate.doesNotContain=" + DEFAULT_POLICY_START_DATE);

        // Get all the policyList where policyStartDate does not contain UPDATED_POLICY_START_DATE
        defaultPolicyShouldBeFound("policyStartDate.doesNotContain=" + UPDATED_POLICY_START_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate equals to DEFAULT_POLICY_END_DATE
        defaultPolicyShouldBeFound("policyEndDate.equals=" + DEFAULT_POLICY_END_DATE);

        // Get all the policyList where policyEndDate equals to UPDATED_POLICY_END_DATE
        defaultPolicyShouldNotBeFound("policyEndDate.equals=" + UPDATED_POLICY_END_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate not equals to DEFAULT_POLICY_END_DATE
        defaultPolicyShouldNotBeFound("policyEndDate.notEquals=" + DEFAULT_POLICY_END_DATE);

        // Get all the policyList where policyEndDate not equals to UPDATED_POLICY_END_DATE
        defaultPolicyShouldBeFound("policyEndDate.notEquals=" + UPDATED_POLICY_END_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate in DEFAULT_POLICY_END_DATE or UPDATED_POLICY_END_DATE
        defaultPolicyShouldBeFound("policyEndDate.in=" + DEFAULT_POLICY_END_DATE + "," + UPDATED_POLICY_END_DATE);

        // Get all the policyList where policyEndDate equals to UPDATED_POLICY_END_DATE
        defaultPolicyShouldNotBeFound("policyEndDate.in=" + UPDATED_POLICY_END_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate is not null
        defaultPolicyShouldBeFound("policyEndDate.specified=true");

        // Get all the policyList where policyEndDate is null
        defaultPolicyShouldNotBeFound("policyEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate contains DEFAULT_POLICY_END_DATE
        defaultPolicyShouldBeFound("policyEndDate.contains=" + DEFAULT_POLICY_END_DATE);

        // Get all the policyList where policyEndDate contains UPDATED_POLICY_END_DATE
        defaultPolicyShouldNotBeFound("policyEndDate.contains=" + UPDATED_POLICY_END_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyEndDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyEndDate does not contain DEFAULT_POLICY_END_DATE
        defaultPolicyShouldNotBeFound("policyEndDate.doesNotContain=" + DEFAULT_POLICY_END_DATE);

        // Get all the policyList where policyEndDate does not contain UPDATED_POLICY_END_DATE
        defaultPolicyShouldBeFound("policyEndDate.doesNotContain=" + UPDATED_POLICY_END_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period equals to DEFAULT_PERIOD
        defaultPolicyShouldBeFound("period.equals=" + DEFAULT_PERIOD);

        // Get all the policyList where period equals to UPDATED_PERIOD
        defaultPolicyShouldNotBeFound("period.equals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period not equals to DEFAULT_PERIOD
        defaultPolicyShouldNotBeFound("period.notEquals=" + DEFAULT_PERIOD);

        // Get all the policyList where period not equals to UPDATED_PERIOD
        defaultPolicyShouldBeFound("period.notEquals=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period in DEFAULT_PERIOD or UPDATED_PERIOD
        defaultPolicyShouldBeFound("period.in=" + DEFAULT_PERIOD + "," + UPDATED_PERIOD);

        // Get all the policyList where period equals to UPDATED_PERIOD
        defaultPolicyShouldNotBeFound("period.in=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period is not null
        defaultPolicyShouldBeFound("period.specified=true");

        // Get all the policyList where period is null
        defaultPolicyShouldNotBeFound("period.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period contains DEFAULT_PERIOD
        defaultPolicyShouldBeFound("period.contains=" + DEFAULT_PERIOD);

        // Get all the policyList where period contains UPDATED_PERIOD
        defaultPolicyShouldNotBeFound("period.contains=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllPoliciesByPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where period does not contain DEFAULT_PERIOD
        defaultPolicyShouldNotBeFound("period.doesNotContain=" + DEFAULT_PERIOD);

        // Get all the policyList where period does not contain UPDATED_PERIOD
        defaultPolicyShouldBeFound("period.doesNotContain=" + UPDATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllPoliciesByClaimDoneIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where claimDone equals to DEFAULT_CLAIM_DONE
        defaultPolicyShouldBeFound("claimDone.equals=" + DEFAULT_CLAIM_DONE);

        // Get all the policyList where claimDone equals to UPDATED_CLAIM_DONE
        defaultPolicyShouldNotBeFound("claimDone.equals=" + UPDATED_CLAIM_DONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByClaimDoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where claimDone not equals to DEFAULT_CLAIM_DONE
        defaultPolicyShouldNotBeFound("claimDone.notEquals=" + DEFAULT_CLAIM_DONE);

        // Get all the policyList where claimDone not equals to UPDATED_CLAIM_DONE
        defaultPolicyShouldBeFound("claimDone.notEquals=" + UPDATED_CLAIM_DONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByClaimDoneIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where claimDone in DEFAULT_CLAIM_DONE or UPDATED_CLAIM_DONE
        defaultPolicyShouldBeFound("claimDone.in=" + DEFAULT_CLAIM_DONE + "," + UPDATED_CLAIM_DONE);

        // Get all the policyList where claimDone equals to UPDATED_CLAIM_DONE
        defaultPolicyShouldNotBeFound("claimDone.in=" + UPDATED_CLAIM_DONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByClaimDoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where claimDone is not null
        defaultPolicyShouldBeFound("claimDone.specified=true");

        // Get all the policyList where claimDone is null
        defaultPolicyShouldNotBeFound("claimDone.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeHeathCheckupIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeHeathCheckup equals to DEFAULT_FREE_HEATH_CHECKUP
        defaultPolicyShouldBeFound("freeHeathCheckup.equals=" + DEFAULT_FREE_HEATH_CHECKUP);

        // Get all the policyList where freeHeathCheckup equals to UPDATED_FREE_HEATH_CHECKUP
        defaultPolicyShouldNotBeFound("freeHeathCheckup.equals=" + UPDATED_FREE_HEATH_CHECKUP);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeHeathCheckupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeHeathCheckup not equals to DEFAULT_FREE_HEATH_CHECKUP
        defaultPolicyShouldNotBeFound("freeHeathCheckup.notEquals=" + DEFAULT_FREE_HEATH_CHECKUP);

        // Get all the policyList where freeHeathCheckup not equals to UPDATED_FREE_HEATH_CHECKUP
        defaultPolicyShouldBeFound("freeHeathCheckup.notEquals=" + UPDATED_FREE_HEATH_CHECKUP);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeHeathCheckupIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeHeathCheckup in DEFAULT_FREE_HEATH_CHECKUP or UPDATED_FREE_HEATH_CHECKUP
        defaultPolicyShouldBeFound("freeHeathCheckup.in=" + DEFAULT_FREE_HEATH_CHECKUP + "," + UPDATED_FREE_HEATH_CHECKUP);

        // Get all the policyList where freeHeathCheckup equals to UPDATED_FREE_HEATH_CHECKUP
        defaultPolicyShouldNotBeFound("freeHeathCheckup.in=" + UPDATED_FREE_HEATH_CHECKUP);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeHeathCheckupIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeHeathCheckup is not null
        defaultPolicyShouldBeFound("freeHeathCheckup.specified=true");

        // Get all the policyList where freeHeathCheckup is null
        defaultPolicyShouldNotBeFound("freeHeathCheckup.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByZoneIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where zone equals to DEFAULT_ZONE
        defaultPolicyShouldBeFound("zone.equals=" + DEFAULT_ZONE);

        // Get all the policyList where zone equals to UPDATED_ZONE
        defaultPolicyShouldNotBeFound("zone.equals=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByZoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where zone not equals to DEFAULT_ZONE
        defaultPolicyShouldNotBeFound("zone.notEquals=" + DEFAULT_ZONE);

        // Get all the policyList where zone not equals to UPDATED_ZONE
        defaultPolicyShouldBeFound("zone.notEquals=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByZoneIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where zone in DEFAULT_ZONE or UPDATED_ZONE
        defaultPolicyShouldBeFound("zone.in=" + DEFAULT_ZONE + "," + UPDATED_ZONE);

        // Get all the policyList where zone equals to UPDATED_ZONE
        defaultPolicyShouldNotBeFound("zone.in=" + UPDATED_ZONE);
    }

    @Test
    @Transactional
    void getAllPoliciesByZoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where zone is not null
        defaultPolicyShouldBeFound("zone.specified=true");

        // Get all the policyList where zone is null
        defaultPolicyShouldNotBeFound("zone.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear equals to DEFAULT_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.equals=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear equals to UPDATED_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.equals=" + UPDATED_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear not equals to DEFAULT_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.notEquals=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear not equals to UPDATED_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.notEquals=" + UPDATED_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear in DEFAULT_NO_OF_YEAR or UPDATED_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.in=" + DEFAULT_NO_OF_YEAR + "," + UPDATED_NO_OF_YEAR);

        // Get all the policyList where noOfYear equals to UPDATED_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.in=" + UPDATED_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear is not null
        defaultPolicyShouldBeFound("noOfYear.specified=true");

        // Get all the policyList where noOfYear is null
        defaultPolicyShouldNotBeFound("noOfYear.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear is greater than or equal to DEFAULT_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.greaterThanOrEqual=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear is greater than or equal to UPDATED_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.greaterThanOrEqual=" + UPDATED_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear is less than or equal to DEFAULT_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.lessThanOrEqual=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear is less than or equal to SMALLER_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.lessThanOrEqual=" + SMALLER_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear is less than DEFAULT_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.lessThan=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear is less than UPDATED_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.lessThan=" + UPDATED_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByNoOfYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where noOfYear is greater than DEFAULT_NO_OF_YEAR
        defaultPolicyShouldNotBeFound("noOfYear.greaterThan=" + DEFAULT_NO_OF_YEAR);

        // Get all the policyList where noOfYear is greater than SMALLER_NO_OF_YEAR
        defaultPolicyShouldBeFound("noOfYear.greaterThan=" + SMALLER_NO_OF_YEAR);
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum equals to DEFAULT_FLOATER_SUM
        defaultPolicyShouldBeFound("floaterSum.equals=" + DEFAULT_FLOATER_SUM);

        // Get all the policyList where floaterSum equals to UPDATED_FLOATER_SUM
        defaultPolicyShouldNotBeFound("floaterSum.equals=" + UPDATED_FLOATER_SUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum not equals to DEFAULT_FLOATER_SUM
        defaultPolicyShouldNotBeFound("floaterSum.notEquals=" + DEFAULT_FLOATER_SUM);

        // Get all the policyList where floaterSum not equals to UPDATED_FLOATER_SUM
        defaultPolicyShouldBeFound("floaterSum.notEquals=" + UPDATED_FLOATER_SUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum in DEFAULT_FLOATER_SUM or UPDATED_FLOATER_SUM
        defaultPolicyShouldBeFound("floaterSum.in=" + DEFAULT_FLOATER_SUM + "," + UPDATED_FLOATER_SUM);

        // Get all the policyList where floaterSum equals to UPDATED_FLOATER_SUM
        defaultPolicyShouldNotBeFound("floaterSum.in=" + UPDATED_FLOATER_SUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum is not null
        defaultPolicyShouldBeFound("floaterSum.specified=true");

        // Get all the policyList where floaterSum is null
        defaultPolicyShouldNotBeFound("floaterSum.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum contains DEFAULT_FLOATER_SUM
        defaultPolicyShouldBeFound("floaterSum.contains=" + DEFAULT_FLOATER_SUM);

        // Get all the policyList where floaterSum contains UPDATED_FLOATER_SUM
        defaultPolicyShouldNotBeFound("floaterSum.contains=" + UPDATED_FLOATER_SUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByFloaterSumNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where floaterSum does not contain DEFAULT_FLOATER_SUM
        defaultPolicyShouldNotBeFound("floaterSum.doesNotContain=" + DEFAULT_FLOATER_SUM);

        // Get all the policyList where floaterSum does not contain UPDATED_FLOATER_SUM
        defaultPolicyShouldBeFound("floaterSum.doesNotContain=" + UPDATED_FLOATER_SUM);
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa equals to DEFAULT_TPA
        defaultPolicyShouldBeFound("tpa.equals=" + DEFAULT_TPA);

        // Get all the policyList where tpa equals to UPDATED_TPA
        defaultPolicyShouldNotBeFound("tpa.equals=" + UPDATED_TPA);
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa not equals to DEFAULT_TPA
        defaultPolicyShouldNotBeFound("tpa.notEquals=" + DEFAULT_TPA);

        // Get all the policyList where tpa not equals to UPDATED_TPA
        defaultPolicyShouldBeFound("tpa.notEquals=" + UPDATED_TPA);
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa in DEFAULT_TPA or UPDATED_TPA
        defaultPolicyShouldBeFound("tpa.in=" + DEFAULT_TPA + "," + UPDATED_TPA);

        // Get all the policyList where tpa equals to UPDATED_TPA
        defaultPolicyShouldNotBeFound("tpa.in=" + UPDATED_TPA);
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa is not null
        defaultPolicyShouldBeFound("tpa.specified=true");

        // Get all the policyList where tpa is null
        defaultPolicyShouldNotBeFound("tpa.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa contains DEFAULT_TPA
        defaultPolicyShouldBeFound("tpa.contains=" + DEFAULT_TPA);

        // Get all the policyList where tpa contains UPDATED_TPA
        defaultPolicyShouldNotBeFound("tpa.contains=" + UPDATED_TPA);
    }

    @Test
    @Transactional
    void getAllPoliciesByTpaNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where tpa does not contain DEFAULT_TPA
        defaultPolicyShouldNotBeFound("tpa.doesNotContain=" + DEFAULT_TPA);

        // Get all the policyList where tpa does not contain UPDATED_TPA
        defaultPolicyShouldBeFound("tpa.doesNotContain=" + UPDATED_TPA);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultPolicyShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the policyList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPolicyShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultPolicyShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the policyList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultPolicyShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultPolicyShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the policyList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPolicyShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate is not null
        defaultPolicyShouldBeFound("paymentDate.specified=true");

        // Get all the policyList where paymentDate is null
        defaultPolicyShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate contains DEFAULT_PAYMENT_DATE
        defaultPolicyShouldBeFound("paymentDate.contains=" + DEFAULT_PAYMENT_DATE);

        // Get all the policyList where paymentDate contains UPDATED_PAYMENT_DATE
        defaultPolicyShouldNotBeFound("paymentDate.contains=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaymentDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paymentDate does not contain DEFAULT_PAYMENT_DATE
        defaultPolicyShouldNotBeFound("paymentDate.doesNotContain=" + DEFAULT_PAYMENT_DATE);

        // Get all the policyList where paymentDate does not contain UPDATED_PAYMENT_DATE
        defaultPolicyShouldBeFound("paymentDate.doesNotContain=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyType equals to DEFAULT_POLICY_TYPE
        defaultPolicyShouldBeFound("policyType.equals=" + DEFAULT_POLICY_TYPE);

        // Get all the policyList where policyType equals to UPDATED_POLICY_TYPE
        defaultPolicyShouldNotBeFound("policyType.equals=" + UPDATED_POLICY_TYPE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyType not equals to DEFAULT_POLICY_TYPE
        defaultPolicyShouldNotBeFound("policyType.notEquals=" + DEFAULT_POLICY_TYPE);

        // Get all the policyList where policyType not equals to UPDATED_POLICY_TYPE
        defaultPolicyShouldBeFound("policyType.notEquals=" + UPDATED_POLICY_TYPE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyType in DEFAULT_POLICY_TYPE or UPDATED_POLICY_TYPE
        defaultPolicyShouldBeFound("policyType.in=" + DEFAULT_POLICY_TYPE + "," + UPDATED_POLICY_TYPE);

        // Get all the policyList where policyType equals to UPDATED_POLICY_TYPE
        defaultPolicyShouldNotBeFound("policyType.in=" + UPDATED_POLICY_TYPE);
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where policyType is not null
        defaultPolicyShouldBeFound("policyType.specified=true");

        // Get all the policyList where policyType is null
        defaultPolicyShouldNotBeFound("policyType.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner equals to DEFAULT_PA_TO_OWNER
        defaultPolicyShouldBeFound("paToOwner.equals=" + DEFAULT_PA_TO_OWNER);

        // Get all the policyList where paToOwner equals to UPDATED_PA_TO_OWNER
        defaultPolicyShouldNotBeFound("paToOwner.equals=" + UPDATED_PA_TO_OWNER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner not equals to DEFAULT_PA_TO_OWNER
        defaultPolicyShouldNotBeFound("paToOwner.notEquals=" + DEFAULT_PA_TO_OWNER);

        // Get all the policyList where paToOwner not equals to UPDATED_PA_TO_OWNER
        defaultPolicyShouldBeFound("paToOwner.notEquals=" + UPDATED_PA_TO_OWNER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner in DEFAULT_PA_TO_OWNER or UPDATED_PA_TO_OWNER
        defaultPolicyShouldBeFound("paToOwner.in=" + DEFAULT_PA_TO_OWNER + "," + UPDATED_PA_TO_OWNER);

        // Get all the policyList where paToOwner equals to UPDATED_PA_TO_OWNER
        defaultPolicyShouldNotBeFound("paToOwner.in=" + UPDATED_PA_TO_OWNER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner is not null
        defaultPolicyShouldBeFound("paToOwner.specified=true");

        // Get all the policyList where paToOwner is null
        defaultPolicyShouldNotBeFound("paToOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner contains DEFAULT_PA_TO_OWNER
        defaultPolicyShouldBeFound("paToOwner.contains=" + DEFAULT_PA_TO_OWNER);

        // Get all the policyList where paToOwner contains UPDATED_PA_TO_OWNER
        defaultPolicyShouldNotBeFound("paToOwner.contains=" + UPDATED_PA_TO_OWNER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOwner does not contain DEFAULT_PA_TO_OWNER
        defaultPolicyShouldNotBeFound("paToOwner.doesNotContain=" + DEFAULT_PA_TO_OWNER);

        // Get all the policyList where paToOwner does not contain UPDATED_PA_TO_OWNER
        defaultPolicyShouldBeFound("paToOwner.doesNotContain=" + UPDATED_PA_TO_OWNER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther equals to DEFAULT_PA_TO_OTHER
        defaultPolicyShouldBeFound("paToOther.equals=" + DEFAULT_PA_TO_OTHER);

        // Get all the policyList where paToOther equals to UPDATED_PA_TO_OTHER
        defaultPolicyShouldNotBeFound("paToOther.equals=" + UPDATED_PA_TO_OTHER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther not equals to DEFAULT_PA_TO_OTHER
        defaultPolicyShouldNotBeFound("paToOther.notEquals=" + DEFAULT_PA_TO_OTHER);

        // Get all the policyList where paToOther not equals to UPDATED_PA_TO_OTHER
        defaultPolicyShouldBeFound("paToOther.notEquals=" + UPDATED_PA_TO_OTHER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther in DEFAULT_PA_TO_OTHER or UPDATED_PA_TO_OTHER
        defaultPolicyShouldBeFound("paToOther.in=" + DEFAULT_PA_TO_OTHER + "," + UPDATED_PA_TO_OTHER);

        // Get all the policyList where paToOther equals to UPDATED_PA_TO_OTHER
        defaultPolicyShouldNotBeFound("paToOther.in=" + UPDATED_PA_TO_OTHER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther is not null
        defaultPolicyShouldBeFound("paToOther.specified=true");

        // Get all the policyList where paToOther is null
        defaultPolicyShouldNotBeFound("paToOther.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther contains DEFAULT_PA_TO_OTHER
        defaultPolicyShouldBeFound("paToOther.contains=" + DEFAULT_PA_TO_OTHER);

        // Get all the policyList where paToOther contains UPDATED_PA_TO_OTHER
        defaultPolicyShouldNotBeFound("paToOther.contains=" + UPDATED_PA_TO_OTHER);
    }

    @Test
    @Transactional
    void getAllPoliciesByPaToOtherNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where paToOther does not contain DEFAULT_PA_TO_OTHER
        defaultPolicyShouldNotBeFound("paToOther.doesNotContain=" + DEFAULT_PA_TO_OTHER);

        // Get all the policyList where paToOther does not contain UPDATED_PA_TO_OTHER
        defaultPolicyShouldBeFound("paToOther.doesNotContain=" + UPDATED_PA_TO_OTHER);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading equals to DEFAULT_LOADING
        defaultPolicyShouldBeFound("loading.equals=" + DEFAULT_LOADING);

        // Get all the policyList where loading equals to UPDATED_LOADING
        defaultPolicyShouldNotBeFound("loading.equals=" + UPDATED_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading not equals to DEFAULT_LOADING
        defaultPolicyShouldNotBeFound("loading.notEquals=" + DEFAULT_LOADING);

        // Get all the policyList where loading not equals to UPDATED_LOADING
        defaultPolicyShouldBeFound("loading.notEquals=" + UPDATED_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading in DEFAULT_LOADING or UPDATED_LOADING
        defaultPolicyShouldBeFound("loading.in=" + DEFAULT_LOADING + "," + UPDATED_LOADING);

        // Get all the policyList where loading equals to UPDATED_LOADING
        defaultPolicyShouldNotBeFound("loading.in=" + UPDATED_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading is not null
        defaultPolicyShouldBeFound("loading.specified=true");

        // Get all the policyList where loading is null
        defaultPolicyShouldNotBeFound("loading.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading is greater than or equal to DEFAULT_LOADING
        defaultPolicyShouldBeFound("loading.greaterThanOrEqual=" + DEFAULT_LOADING);

        // Get all the policyList where loading is greater than or equal to UPDATED_LOADING
        defaultPolicyShouldNotBeFound("loading.greaterThanOrEqual=" + UPDATED_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading is less than or equal to DEFAULT_LOADING
        defaultPolicyShouldBeFound("loading.lessThanOrEqual=" + DEFAULT_LOADING);

        // Get all the policyList where loading is less than or equal to SMALLER_LOADING
        defaultPolicyShouldNotBeFound("loading.lessThanOrEqual=" + SMALLER_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsLessThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading is less than DEFAULT_LOADING
        defaultPolicyShouldNotBeFound("loading.lessThan=" + DEFAULT_LOADING);

        // Get all the policyList where loading is less than UPDATED_LOADING
        defaultPolicyShouldBeFound("loading.lessThan=" + UPDATED_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByLoadingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where loading is greater than DEFAULT_LOADING
        defaultPolicyShouldNotBeFound("loading.greaterThan=" + DEFAULT_LOADING);

        // Get all the policyList where loading is greater than SMALLER_LOADING
        defaultPolicyShouldBeFound("loading.greaterThan=" + SMALLER_LOADING);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom equals to DEFAULT_RISK_COVERED_FROM
        defaultPolicyShouldBeFound("riskCoveredFrom.equals=" + DEFAULT_RISK_COVERED_FROM);

        // Get all the policyList where riskCoveredFrom equals to UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldNotBeFound("riskCoveredFrom.equals=" + UPDATED_RISK_COVERED_FROM);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom not equals to DEFAULT_RISK_COVERED_FROM
        defaultPolicyShouldNotBeFound("riskCoveredFrom.notEquals=" + DEFAULT_RISK_COVERED_FROM);

        // Get all the policyList where riskCoveredFrom not equals to UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldBeFound("riskCoveredFrom.notEquals=" + UPDATED_RISK_COVERED_FROM);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom in DEFAULT_RISK_COVERED_FROM or UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldBeFound("riskCoveredFrom.in=" + DEFAULT_RISK_COVERED_FROM + "," + UPDATED_RISK_COVERED_FROM);

        // Get all the policyList where riskCoveredFrom equals to UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldNotBeFound("riskCoveredFrom.in=" + UPDATED_RISK_COVERED_FROM);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom is not null
        defaultPolicyShouldBeFound("riskCoveredFrom.specified=true");

        // Get all the policyList where riskCoveredFrom is null
        defaultPolicyShouldNotBeFound("riskCoveredFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom contains DEFAULT_RISK_COVERED_FROM
        defaultPolicyShouldBeFound("riskCoveredFrom.contains=" + DEFAULT_RISK_COVERED_FROM);

        // Get all the policyList where riskCoveredFrom contains UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldNotBeFound("riskCoveredFrom.contains=" + UPDATED_RISK_COVERED_FROM);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredFromNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredFrom does not contain DEFAULT_RISK_COVERED_FROM
        defaultPolicyShouldNotBeFound("riskCoveredFrom.doesNotContain=" + DEFAULT_RISK_COVERED_FROM);

        // Get all the policyList where riskCoveredFrom does not contain UPDATED_RISK_COVERED_FROM
        defaultPolicyShouldBeFound("riskCoveredFrom.doesNotContain=" + UPDATED_RISK_COVERED_FROM);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo equals to DEFAULT_RISK_COVERED_TO
        defaultPolicyShouldBeFound("riskCoveredTo.equals=" + DEFAULT_RISK_COVERED_TO);

        // Get all the policyList where riskCoveredTo equals to UPDATED_RISK_COVERED_TO
        defaultPolicyShouldNotBeFound("riskCoveredTo.equals=" + UPDATED_RISK_COVERED_TO);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo not equals to DEFAULT_RISK_COVERED_TO
        defaultPolicyShouldNotBeFound("riskCoveredTo.notEquals=" + DEFAULT_RISK_COVERED_TO);

        // Get all the policyList where riskCoveredTo not equals to UPDATED_RISK_COVERED_TO
        defaultPolicyShouldBeFound("riskCoveredTo.notEquals=" + UPDATED_RISK_COVERED_TO);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo in DEFAULT_RISK_COVERED_TO or UPDATED_RISK_COVERED_TO
        defaultPolicyShouldBeFound("riskCoveredTo.in=" + DEFAULT_RISK_COVERED_TO + "," + UPDATED_RISK_COVERED_TO);

        // Get all the policyList where riskCoveredTo equals to UPDATED_RISK_COVERED_TO
        defaultPolicyShouldNotBeFound("riskCoveredTo.in=" + UPDATED_RISK_COVERED_TO);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo is not null
        defaultPolicyShouldBeFound("riskCoveredTo.specified=true");

        // Get all the policyList where riskCoveredTo is null
        defaultPolicyShouldNotBeFound("riskCoveredTo.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo contains DEFAULT_RISK_COVERED_TO
        defaultPolicyShouldBeFound("riskCoveredTo.contains=" + DEFAULT_RISK_COVERED_TO);

        // Get all the policyList where riskCoveredTo contains UPDATED_RISK_COVERED_TO
        defaultPolicyShouldNotBeFound("riskCoveredTo.contains=" + UPDATED_RISK_COVERED_TO);
    }

    @Test
    @Transactional
    void getAllPoliciesByRiskCoveredToNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where riskCoveredTo does not contain DEFAULT_RISK_COVERED_TO
        defaultPolicyShouldNotBeFound("riskCoveredTo.doesNotContain=" + DEFAULT_RISK_COVERED_TO);

        // Get all the policyList where riskCoveredTo does not contain UPDATED_RISK_COVERED_TO
        defaultPolicyShouldBeFound("riskCoveredTo.doesNotContain=" + UPDATED_RISK_COVERED_TO);
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes equals to DEFAULT_NOTES
        defaultPolicyShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the policyList where notes equals to UPDATED_NOTES
        defaultPolicyShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes not equals to DEFAULT_NOTES
        defaultPolicyShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the policyList where notes not equals to UPDATED_NOTES
        defaultPolicyShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultPolicyShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the policyList where notes equals to UPDATED_NOTES
        defaultPolicyShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes is not null
        defaultPolicyShouldBeFound("notes.specified=true");

        // Get all the policyList where notes is null
        defaultPolicyShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes contains DEFAULT_NOTES
        defaultPolicyShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the policyList where notes contains UPDATED_NOTES
        defaultPolicyShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllPoliciesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where notes does not contain DEFAULT_NOTES
        defaultPolicyShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the policyList where notes does not contain UPDATED_NOTES
        defaultPolicyShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultPolicyShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the policyList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPolicyShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultPolicyShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the policyList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultPolicyShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultPolicyShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the policyList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPolicyShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 is not null
        defaultPolicyShouldBeFound("freeField1.specified=true");

        // Get all the policyList where freeField1 is null
        defaultPolicyShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultPolicyShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the policyList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultPolicyShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultPolicyShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the policyList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultPolicyShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultPolicyShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the policyList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPolicyShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultPolicyShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the policyList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultPolicyShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultPolicyShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the policyList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPolicyShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 is not null
        defaultPolicyShouldBeFound("freeField2.specified=true");

        // Get all the policyList where freeField2 is null
        defaultPolicyShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultPolicyShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the policyList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultPolicyShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultPolicyShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the policyList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultPolicyShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultPolicyShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the policyList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultPolicyShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultPolicyShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the policyList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultPolicyShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultPolicyShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the policyList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultPolicyShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 is not null
        defaultPolicyShouldBeFound("freeField3.specified=true");

        // Get all the policyList where freeField3 is null
        defaultPolicyShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultPolicyShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the policyList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultPolicyShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultPolicyShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the policyList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultPolicyShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultPolicyShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the policyList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultPolicyShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultPolicyShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the policyList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultPolicyShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultPolicyShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the policyList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultPolicyShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 is not null
        defaultPolicyShouldBeFound("freeField4.specified=true");

        // Get all the policyList where freeField4 is null
        defaultPolicyShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultPolicyShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the policyList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultPolicyShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultPolicyShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the policyList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultPolicyShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultPolicyShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the policyList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultPolicyShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 not equals to DEFAULT_FREE_FIELD_5
        defaultPolicyShouldNotBeFound("freeField5.notEquals=" + DEFAULT_FREE_FIELD_5);

        // Get all the policyList where freeField5 not equals to UPDATED_FREE_FIELD_5
        defaultPolicyShouldBeFound("freeField5.notEquals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultPolicyShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the policyList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultPolicyShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 is not null
        defaultPolicyShouldBeFound("freeField5.specified=true");

        // Get all the policyList where freeField5 is null
        defaultPolicyShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultPolicyShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the policyList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultPolicyShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllPoliciesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultPolicyShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the policyList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultPolicyShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate equals to DEFAULT_MATURITY_DATE
        defaultPolicyShouldBeFound("maturityDate.equals=" + DEFAULT_MATURITY_DATE);

        // Get all the policyList where maturityDate equals to UPDATED_MATURITY_DATE
        defaultPolicyShouldNotBeFound("maturityDate.equals=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate not equals to DEFAULT_MATURITY_DATE
        defaultPolicyShouldNotBeFound("maturityDate.notEquals=" + DEFAULT_MATURITY_DATE);

        // Get all the policyList where maturityDate not equals to UPDATED_MATURITY_DATE
        defaultPolicyShouldBeFound("maturityDate.notEquals=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate in DEFAULT_MATURITY_DATE or UPDATED_MATURITY_DATE
        defaultPolicyShouldBeFound("maturityDate.in=" + DEFAULT_MATURITY_DATE + "," + UPDATED_MATURITY_DATE);

        // Get all the policyList where maturityDate equals to UPDATED_MATURITY_DATE
        defaultPolicyShouldNotBeFound("maturityDate.in=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate is not null
        defaultPolicyShouldBeFound("maturityDate.specified=true");

        // Get all the policyList where maturityDate is null
        defaultPolicyShouldNotBeFound("maturityDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate contains DEFAULT_MATURITY_DATE
        defaultPolicyShouldBeFound("maturityDate.contains=" + DEFAULT_MATURITY_DATE);

        // Get all the policyList where maturityDate contains UPDATED_MATURITY_DATE
        defaultPolicyShouldNotBeFound("maturityDate.contains=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByMaturityDateNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where maturityDate does not contain DEFAULT_MATURITY_DATE
        defaultPolicyShouldNotBeFound("maturityDate.doesNotContain=" + DEFAULT_MATURITY_DATE);

        // Get all the policyList where maturityDate does not contain UPDATED_MATURITY_DATE
        defaultPolicyShouldBeFound("maturityDate.doesNotContain=" + UPDATED_MATURITY_DATE);
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo equals to DEFAULT_UIN_NO
        defaultPolicyShouldBeFound("uinNo.equals=" + DEFAULT_UIN_NO);

        // Get all the policyList where uinNo equals to UPDATED_UIN_NO
        defaultPolicyShouldNotBeFound("uinNo.equals=" + UPDATED_UIN_NO);
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo not equals to DEFAULT_UIN_NO
        defaultPolicyShouldNotBeFound("uinNo.notEquals=" + DEFAULT_UIN_NO);

        // Get all the policyList where uinNo not equals to UPDATED_UIN_NO
        defaultPolicyShouldBeFound("uinNo.notEquals=" + UPDATED_UIN_NO);
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo in DEFAULT_UIN_NO or UPDATED_UIN_NO
        defaultPolicyShouldBeFound("uinNo.in=" + DEFAULT_UIN_NO + "," + UPDATED_UIN_NO);

        // Get all the policyList where uinNo equals to UPDATED_UIN_NO
        defaultPolicyShouldNotBeFound("uinNo.in=" + UPDATED_UIN_NO);
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo is not null
        defaultPolicyShouldBeFound("uinNo.specified=true");

        // Get all the policyList where uinNo is null
        defaultPolicyShouldNotBeFound("uinNo.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo contains DEFAULT_UIN_NO
        defaultPolicyShouldBeFound("uinNo.contains=" + DEFAULT_UIN_NO);

        // Get all the policyList where uinNo contains UPDATED_UIN_NO
        defaultPolicyShouldNotBeFound("uinNo.contains=" + UPDATED_UIN_NO);
    }

    @Test
    @Transactional
    void getAllPoliciesByUinNoNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where uinNo does not contain DEFAULT_UIN_NO
        defaultPolicyShouldNotBeFound("uinNo.doesNotContain=" + DEFAULT_UIN_NO);

        // Get all the policyList where uinNo does not contain UPDATED_UIN_NO
        defaultPolicyShouldBeFound("uinNo.doesNotContain=" + UPDATED_UIN_NO);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPolicyShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPolicyShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPolicyShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPolicyShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the policyList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPolicyShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified is not null
        defaultPolicyShouldBeFound("lastModified.specified=true");

        // Get all the policyList where lastModified is null
        defaultPolicyShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified contains DEFAULT_LAST_MODIFIED
        defaultPolicyShouldBeFound("lastModified.contains=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyList where lastModified contains UPDATED_LAST_MODIFIED
        defaultPolicyShouldNotBeFound("lastModified.contains=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModified does not contain DEFAULT_LAST_MODIFIED
        defaultPolicyShouldNotBeFound("lastModified.doesNotContain=" + DEFAULT_LAST_MODIFIED);

        // Get all the policyList where lastModified does not contain UPDATED_LAST_MODIFIED
        defaultPolicyShouldBeFound("lastModified.doesNotContain=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPolicyShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the policyList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy is not null
        defaultPolicyShouldBeFound("lastModifiedBy.specified=true");

        // Get all the policyList where lastModifiedBy is null
        defaultPolicyShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPolicyShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPoliciesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        // Get all the policyList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPolicyShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the policyList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPolicyShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPoliciesByAgencyIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        Agency agency;
        if (TestUtil.findAll(em, Agency.class).isEmpty()) {
            agency = AgencyResourceIT.createEntity(em);
            em.persist(agency);
            em.flush();
        } else {
            agency = TestUtil.findAll(em, Agency.class).get(0);
        }
        em.persist(agency);
        em.flush();
        policy.setAgency(agency);
        policyRepository.saveAndFlush(policy);
        Long agencyId = agency.getId();

        // Get all the policyList where agency equals to agencyId
        defaultPolicyShouldBeFound("agencyId.equals=" + agencyId);

        // Get all the policyList where agency equals to (agencyId + 1)
        defaultPolicyShouldNotBeFound("agencyId.equals=" + (agencyId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
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
        policy.setCompany(company);
        policyRepository.saveAndFlush(policy);
        Long companyId = company.getId();

        // Get all the policyList where company equals to companyId
        defaultPolicyShouldBeFound("companyId.equals=" + companyId);

        // Get all the policyList where company equals to (companyId + 1)
        defaultPolicyShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
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
        policy.setProduct(product);
        policyRepository.saveAndFlush(policy);
        Long productId = product.getId();

        // Get all the policyList where product equals to productId
        defaultPolicyShouldBeFound("productId.equals=" + productId);

        // Get all the policyList where product equals to (productId + 1)
        defaultPolicyShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByPremiunDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        PremiunDetails premiunDetails;
        if (TestUtil.findAll(em, PremiunDetails.class).isEmpty()) {
            premiunDetails = PremiunDetailsResourceIT.createEntity(em);
            em.persist(premiunDetails);
            em.flush();
        } else {
            premiunDetails = TestUtil.findAll(em, PremiunDetails.class).get(0);
        }
        em.persist(premiunDetails);
        em.flush();
        policy.setPremiunDetails(premiunDetails);
        policyRepository.saveAndFlush(policy);
        Long premiunDetailsId = premiunDetails.getId();

        // Get all the policyList where premiunDetails equals to premiunDetailsId
        defaultPolicyShouldBeFound("premiunDetailsId.equals=" + premiunDetailsId);

        // Get all the policyList where premiunDetails equals to (premiunDetailsId + 1)
        defaultPolicyShouldNotBeFound("premiunDetailsId.equals=" + (premiunDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByVehicleClassIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        VehicleClass vehicleClass;
        if (TestUtil.findAll(em, VehicleClass.class).isEmpty()) {
            vehicleClass = VehicleClassResourceIT.createEntity(em);
            em.persist(vehicleClass);
            em.flush();
        } else {
            vehicleClass = TestUtil.findAll(em, VehicleClass.class).get(0);
        }
        em.persist(vehicleClass);
        em.flush();
        policy.setVehicleClass(vehicleClass);
        policyRepository.saveAndFlush(policy);
        Long vehicleClassId = vehicleClass.getId();

        // Get all the policyList where vehicleClass equals to vehicleClassId
        defaultPolicyShouldBeFound("vehicleClassId.equals=" + vehicleClassId);

        // Get all the policyList where vehicleClass equals to (vehicleClassId + 1)
        defaultPolicyShouldNotBeFound("vehicleClassId.equals=" + (vehicleClassId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByBankDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        BankDetails bankDetails;
        if (TestUtil.findAll(em, BankDetails.class).isEmpty()) {
            bankDetails = BankDetailsResourceIT.createEntity(em);
            em.persist(bankDetails);
            em.flush();
        } else {
            bankDetails = TestUtil.findAll(em, BankDetails.class).get(0);
        }
        em.persist(bankDetails);
        em.flush();
        policy.setBankDetails(bankDetails);
        policyRepository.saveAndFlush(policy);
        Long bankDetailsId = bankDetails.getId();

        // Get all the policyList where bankDetails equals to bankDetailsId
        defaultPolicyShouldBeFound("bankDetailsId.equals=" + bankDetailsId);

        // Get all the policyList where bankDetails equals to (bankDetailsId + 1)
        defaultPolicyShouldNotBeFound("bankDetailsId.equals=" + (bankDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByNomineeIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        Nominee nominee;
        if (TestUtil.findAll(em, Nominee.class).isEmpty()) {
            nominee = NomineeResourceIT.createEntity(em);
            em.persist(nominee);
            em.flush();
        } else {
            nominee = TestUtil.findAll(em, Nominee.class).get(0);
        }
        em.persist(nominee);
        em.flush();
        policy.addNominee(nominee);
        policyRepository.saveAndFlush(policy);
        Long nomineeId = nominee.getId();

        // Get all the policyList where nominee equals to nomineeId
        defaultPolicyShouldBeFound("nomineeId.equals=" + nomineeId);

        // Get all the policyList where nominee equals to (nomineeId + 1)
        defaultPolicyShouldNotBeFound("nomineeId.equals=" + (nomineeId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            member = MemberResourceIT.createEntity(em);
            em.persist(member);
            em.flush();
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        policy.addMember(member);
        policyRepository.saveAndFlush(policy);
        Long memberId = member.getId();

        // Get all the policyList where member equals to memberId
        defaultPolicyShouldBeFound("memberId.equals=" + memberId);

        // Get all the policyList where member equals to (memberId + 1)
        defaultPolicyShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllPoliciesByPolicyUsersIsEqualToSomething() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);
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
        policy.setPolicyUsers(policyUsers);
        policyRepository.saveAndFlush(policy);
        Long policyUsersId = policyUsers.getId();

        // Get all the policyList where policyUsers equals to policyUsersId
        defaultPolicyShouldBeFound("policyUsersId.equals=" + policyUsersId);

        // Get all the policyList where policyUsers equals to (policyUsersId + 1)
        defaultPolicyShouldNotBeFound("policyUsersId.equals=" + (policyUsersId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPolicyShouldBeFound(String filter) throws Exception {
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(policy.getId().intValue())))
            .andExpect(jsonPath("$.[*].policyAmount").value(hasItem(DEFAULT_POLICY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].policyNumber").value(hasItem(DEFAULT_POLICY_NUMBER)))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM.intValue())))
            .andExpect(jsonPath("$.[*].ppt").value(hasItem(DEFAULT_PPT.intValue())))
            .andExpect(jsonPath("$.[*].commDate").value(hasItem(DEFAULT_COMM_DATE)))
            .andExpect(jsonPath("$.[*].proposerName").value(hasItem(DEFAULT_PROPOSER_NAME)))
            .andExpect(jsonPath("$.[*].sumAssuredAmount").value(hasItem(DEFAULT_SUM_ASSURED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].premiumMode").value(hasItem(DEFAULT_PREMIUM_MODE.toString())))
            .andExpect(jsonPath("$.[*].basicPremium").value(hasItem(DEFAULT_BASIC_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].extraPremium").value(hasItem(DEFAULT_EXTRA_PREMIUM.intValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].totalPremiun").value(hasItem(DEFAULT_TOTAL_PREMIUN)))
            .andExpect(jsonPath("$.[*].gstFirstYear").value(hasItem(DEFAULT_GST_FIRST_YEAR)))
            .andExpect(jsonPath("$.[*].netPremium").value(hasItem(DEFAULT_NET_PREMIUM)))
            .andExpect(jsonPath("$.[*].taxBeneficiary").value(hasItem(DEFAULT_TAX_BENEFICIARY)))
            .andExpect(jsonPath("$.[*].policyReceived").value(hasItem(DEFAULT_POLICY_RECEIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].previousPolicy").value(hasItem(DEFAULT_PREVIOUS_POLICY.intValue())))
            .andExpect(jsonPath("$.[*].policyStartDate").value(hasItem(DEFAULT_POLICY_START_DATE)))
            .andExpect(jsonPath("$.[*].policyEndDate").value(hasItem(DEFAULT_POLICY_END_DATE)))
            .andExpect(jsonPath("$.[*].period").value(hasItem(DEFAULT_PERIOD)))
            .andExpect(jsonPath("$.[*].claimDone").value(hasItem(DEFAULT_CLAIM_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].freeHeathCheckup").value(hasItem(DEFAULT_FREE_HEATH_CHECKUP.booleanValue())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE.toString())))
            .andExpect(jsonPath("$.[*].noOfYear").value(hasItem(DEFAULT_NO_OF_YEAR.intValue())))
            .andExpect(jsonPath("$.[*].floaterSum").value(hasItem(DEFAULT_FLOATER_SUM)))
            .andExpect(jsonPath("$.[*].tpa").value(hasItem(DEFAULT_TPA)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE)))
            .andExpect(jsonPath("$.[*].policyType").value(hasItem(DEFAULT_POLICY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].paToOwner").value(hasItem(DEFAULT_PA_TO_OWNER)))
            .andExpect(jsonPath("$.[*].paToOther").value(hasItem(DEFAULT_PA_TO_OTHER)))
            .andExpect(jsonPath("$.[*].loading").value(hasItem(DEFAULT_LOADING.intValue())))
            .andExpect(jsonPath("$.[*].riskCoveredFrom").value(hasItem(DEFAULT_RISK_COVERED_FROM)))
            .andExpect(jsonPath("$.[*].riskCoveredTo").value(hasItem(DEFAULT_RISK_COVERED_TO)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].maturityDate").value(hasItem(DEFAULT_MATURITY_DATE)))
            .andExpect(jsonPath("$.[*].uinNo").value(hasItem(DEFAULT_UIN_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPolicyShouldNotBeFound(String filter) throws Exception {
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPolicyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPolicy() throws Exception {
        // Get the policy
        restPolicyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy
        Policy updatedPolicy = policyRepository.findById(policy.getId()).get();
        // Disconnect from session so that the updates on updatedPolicy are not directly saved in db
        em.detach(updatedPolicy);
        updatedPolicy
            .policyAmount(UPDATED_POLICY_AMOUNT)
            .policyNumber(UPDATED_POLICY_NUMBER)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .commDate(UPDATED_COMM_DATE)
            .proposerName(UPDATED_PROPOSER_NAME)
            .sumAssuredAmount(UPDATED_SUM_ASSURED_AMOUNT)
            .premiumMode(UPDATED_PREMIUM_MODE)
            .basicPremium(UPDATED_BASIC_PREMIUM)
            .extraPremium(UPDATED_EXTRA_PREMIUM)
            .gst(UPDATED_GST)
            .status(UPDATED_STATUS)
            .totalPremiun(UPDATED_TOTAL_PREMIUN)
            .gstFirstYear(UPDATED_GST_FIRST_YEAR)
            .netPremium(UPDATED_NET_PREMIUM)
            .taxBeneficiary(UPDATED_TAX_BENEFICIARY)
            .policyReceived(UPDATED_POLICY_RECEIVED)
            .previousPolicy(UPDATED_PREVIOUS_POLICY)
            .policyStartDate(UPDATED_POLICY_START_DATE)
            .policyEndDate(UPDATED_POLICY_END_DATE)
            .period(UPDATED_PERIOD)
            .claimDone(UPDATED_CLAIM_DONE)
            .freeHeathCheckup(UPDATED_FREE_HEATH_CHECKUP)
            .zone(UPDATED_ZONE)
            .noOfYear(UPDATED_NO_OF_YEAR)
            .floaterSum(UPDATED_FLOATER_SUM)
            .tpa(UPDATED_TPA)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .policyType(UPDATED_POLICY_TYPE)
            .paToOwner(UPDATED_PA_TO_OWNER)
            .paToOther(UPDATED_PA_TO_OTHER)
            .loading(UPDATED_LOADING)
            .riskCoveredFrom(UPDATED_RISK_COVERED_FROM)
            .riskCoveredTo(UPDATED_RISK_COVERED_TO)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .maturityDate(UPDATED_MATURITY_DATE)
            .uinNo(UPDATED_UIN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PolicyDTO policyDTO = policyMapper.toDto(updatedPolicy);

        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyAmount()).isEqualTo(UPDATED_POLICY_AMOUNT);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testPolicy.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testPolicy.getPpt()).isEqualTo(UPDATED_PPT);
        assertThat(testPolicy.getCommDate()).isEqualTo(UPDATED_COMM_DATE);
        assertThat(testPolicy.getProposerName()).isEqualTo(UPDATED_PROPOSER_NAME);
        assertThat(testPolicy.getSumAssuredAmount()).isEqualTo(UPDATED_SUM_ASSURED_AMOUNT);
        assertThat(testPolicy.getPremiumMode()).isEqualTo(UPDATED_PREMIUM_MODE);
        assertThat(testPolicy.getBasicPremium()).isEqualTo(UPDATED_BASIC_PREMIUM);
        assertThat(testPolicy.getExtraPremium()).isEqualTo(UPDATED_EXTRA_PREMIUM);
        assertThat(testPolicy.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicy.getTotalPremiun()).isEqualTo(UPDATED_TOTAL_PREMIUN);
        assertThat(testPolicy.getGstFirstYear()).isEqualTo(UPDATED_GST_FIRST_YEAR);
        assertThat(testPolicy.getNetPremium()).isEqualTo(UPDATED_NET_PREMIUM);
        assertThat(testPolicy.getTaxBeneficiary()).isEqualTo(UPDATED_TAX_BENEFICIARY);
        assertThat(testPolicy.getPolicyReceived()).isEqualTo(UPDATED_POLICY_RECEIVED);
        assertThat(testPolicy.getPreviousPolicy()).isEqualTo(UPDATED_PREVIOUS_POLICY);
        assertThat(testPolicy.getPolicyStartDate()).isEqualTo(UPDATED_POLICY_START_DATE);
        assertThat(testPolicy.getPolicyEndDate()).isEqualTo(UPDATED_POLICY_END_DATE);
        assertThat(testPolicy.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPolicy.getClaimDone()).isEqualTo(UPDATED_CLAIM_DONE);
        assertThat(testPolicy.getFreeHeathCheckup()).isEqualTo(UPDATED_FREE_HEATH_CHECKUP);
        assertThat(testPolicy.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testPolicy.getNoOfYear()).isEqualTo(UPDATED_NO_OF_YEAR);
        assertThat(testPolicy.getFloaterSum()).isEqualTo(UPDATED_FLOATER_SUM);
        assertThat(testPolicy.getTpa()).isEqualTo(UPDATED_TPA);
        assertThat(testPolicy.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPolicy.getPolicyType()).isEqualTo(UPDATED_POLICY_TYPE);
        assertThat(testPolicy.getPaToOwner()).isEqualTo(UPDATED_PA_TO_OWNER);
        assertThat(testPolicy.getPaToOther()).isEqualTo(UPDATED_PA_TO_OTHER);
        assertThat(testPolicy.getLoading()).isEqualTo(UPDATED_LOADING);
        assertThat(testPolicy.getRiskCoveredFrom()).isEqualTo(UPDATED_RISK_COVERED_FROM);
        assertThat(testPolicy.getRiskCoveredTo()).isEqualTo(UPDATED_RISK_COVERED_TO);
        assertThat(testPolicy.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPolicy.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPolicy.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPolicy.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPolicy.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testPolicy.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testPolicy.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testPolicy.getUinNo()).isEqualTo(UPDATED_UIN_NO);
        assertThat(testPolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(policyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy
            .term(UPDATED_TERM)
            .premiumMode(UPDATED_PREMIUM_MODE)
            .extraPremium(UPDATED_EXTRA_PREMIUM)
            .status(UPDATED_STATUS)
            .gstFirstYear(UPDATED_GST_FIRST_YEAR)
            .netPremium(UPDATED_NET_PREMIUM)
            .taxBeneficiary(UPDATED_TAX_BENEFICIARY)
            .policyReceived(UPDATED_POLICY_RECEIVED)
            .policyStartDate(UPDATED_POLICY_START_DATE)
            .freeHeathCheckup(UPDATED_FREE_HEATH_CHECKUP)
            .noOfYear(UPDATED_NO_OF_YEAR)
            .floaterSum(UPDATED_FLOATER_SUM)
            .tpa(UPDATED_TPA)
            .paToOwner(UPDATED_PA_TO_OWNER)
            .loading(UPDATED_LOADING)
            .riskCoveredTo(UPDATED_RISK_COVERED_TO)
            .freeField1(UPDATED_FREE_FIELD_1)
            .maturityDate(UPDATED_MATURITY_DATE)
            .uinNo(UPDATED_UIN_NO);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyAmount()).isEqualTo(DEFAULT_POLICY_AMOUNT);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(DEFAULT_POLICY_NUMBER);
        assertThat(testPolicy.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testPolicy.getPpt()).isEqualTo(DEFAULT_PPT);
        assertThat(testPolicy.getCommDate()).isEqualTo(DEFAULT_COMM_DATE);
        assertThat(testPolicy.getProposerName()).isEqualTo(DEFAULT_PROPOSER_NAME);
        assertThat(testPolicy.getSumAssuredAmount()).isEqualTo(DEFAULT_SUM_ASSURED_AMOUNT);
        assertThat(testPolicy.getPremiumMode()).isEqualTo(UPDATED_PREMIUM_MODE);
        assertThat(testPolicy.getBasicPremium()).isEqualTo(DEFAULT_BASIC_PREMIUM);
        assertThat(testPolicy.getExtraPremium()).isEqualTo(UPDATED_EXTRA_PREMIUM);
        assertThat(testPolicy.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicy.getTotalPremiun()).isEqualTo(DEFAULT_TOTAL_PREMIUN);
        assertThat(testPolicy.getGstFirstYear()).isEqualTo(UPDATED_GST_FIRST_YEAR);
        assertThat(testPolicy.getNetPremium()).isEqualTo(UPDATED_NET_PREMIUM);
        assertThat(testPolicy.getTaxBeneficiary()).isEqualTo(UPDATED_TAX_BENEFICIARY);
        assertThat(testPolicy.getPolicyReceived()).isEqualTo(UPDATED_POLICY_RECEIVED);
        assertThat(testPolicy.getPreviousPolicy()).isEqualTo(DEFAULT_PREVIOUS_POLICY);
        assertThat(testPolicy.getPolicyStartDate()).isEqualTo(UPDATED_POLICY_START_DATE);
        assertThat(testPolicy.getPolicyEndDate()).isEqualTo(DEFAULT_POLICY_END_DATE);
        assertThat(testPolicy.getPeriod()).isEqualTo(DEFAULT_PERIOD);
        assertThat(testPolicy.getClaimDone()).isEqualTo(DEFAULT_CLAIM_DONE);
        assertThat(testPolicy.getFreeHeathCheckup()).isEqualTo(UPDATED_FREE_HEATH_CHECKUP);
        assertThat(testPolicy.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testPolicy.getNoOfYear()).isEqualTo(UPDATED_NO_OF_YEAR);
        assertThat(testPolicy.getFloaterSum()).isEqualTo(UPDATED_FLOATER_SUM);
        assertThat(testPolicy.getTpa()).isEqualTo(UPDATED_TPA);
        assertThat(testPolicy.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPolicy.getPolicyType()).isEqualTo(DEFAULT_POLICY_TYPE);
        assertThat(testPolicy.getPaToOwner()).isEqualTo(UPDATED_PA_TO_OWNER);
        assertThat(testPolicy.getPaToOther()).isEqualTo(DEFAULT_PA_TO_OTHER);
        assertThat(testPolicy.getLoading()).isEqualTo(UPDATED_LOADING);
        assertThat(testPolicy.getRiskCoveredFrom()).isEqualTo(DEFAULT_RISK_COVERED_FROM);
        assertThat(testPolicy.getRiskCoveredTo()).isEqualTo(UPDATED_RISK_COVERED_TO);
        assertThat(testPolicy.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testPolicy.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPolicy.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPolicy.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPolicy.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testPolicy.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testPolicy.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testPolicy.getUinNo()).isEqualTo(UPDATED_UIN_NO);
        assertThat(testPolicy.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPolicy.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePolicyWithPatch() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeUpdate = policyRepository.findAll().size();

        // Update the policy using partial update
        Policy partialUpdatedPolicy = new Policy();
        partialUpdatedPolicy.setId(policy.getId());

        partialUpdatedPolicy
            .policyAmount(UPDATED_POLICY_AMOUNT)
            .policyNumber(UPDATED_POLICY_NUMBER)
            .term(UPDATED_TERM)
            .ppt(UPDATED_PPT)
            .commDate(UPDATED_COMM_DATE)
            .proposerName(UPDATED_PROPOSER_NAME)
            .sumAssuredAmount(UPDATED_SUM_ASSURED_AMOUNT)
            .premiumMode(UPDATED_PREMIUM_MODE)
            .basicPremium(UPDATED_BASIC_PREMIUM)
            .extraPremium(UPDATED_EXTRA_PREMIUM)
            .gst(UPDATED_GST)
            .status(UPDATED_STATUS)
            .totalPremiun(UPDATED_TOTAL_PREMIUN)
            .gstFirstYear(UPDATED_GST_FIRST_YEAR)
            .netPremium(UPDATED_NET_PREMIUM)
            .taxBeneficiary(UPDATED_TAX_BENEFICIARY)
            .policyReceived(UPDATED_POLICY_RECEIVED)
            .previousPolicy(UPDATED_PREVIOUS_POLICY)
            .policyStartDate(UPDATED_POLICY_START_DATE)
            .policyEndDate(UPDATED_POLICY_END_DATE)
            .period(UPDATED_PERIOD)
            .claimDone(UPDATED_CLAIM_DONE)
            .freeHeathCheckup(UPDATED_FREE_HEATH_CHECKUP)
            .zone(UPDATED_ZONE)
            .noOfYear(UPDATED_NO_OF_YEAR)
            .floaterSum(UPDATED_FLOATER_SUM)
            .tpa(UPDATED_TPA)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .policyType(UPDATED_POLICY_TYPE)
            .paToOwner(UPDATED_PA_TO_OWNER)
            .paToOther(UPDATED_PA_TO_OTHER)
            .loading(UPDATED_LOADING)
            .riskCoveredFrom(UPDATED_RISK_COVERED_FROM)
            .riskCoveredTo(UPDATED_RISK_COVERED_TO)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .maturityDate(UPDATED_MATURITY_DATE)
            .uinNo(UPDATED_UIN_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPolicy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPolicy))
            )
            .andExpect(status().isOk());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
        Policy testPolicy = policyList.get(policyList.size() - 1);
        assertThat(testPolicy.getPolicyAmount()).isEqualTo(UPDATED_POLICY_AMOUNT);
        assertThat(testPolicy.getPolicyNumber()).isEqualTo(UPDATED_POLICY_NUMBER);
        assertThat(testPolicy.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testPolicy.getPpt()).isEqualTo(UPDATED_PPT);
        assertThat(testPolicy.getCommDate()).isEqualTo(UPDATED_COMM_DATE);
        assertThat(testPolicy.getProposerName()).isEqualTo(UPDATED_PROPOSER_NAME);
        assertThat(testPolicy.getSumAssuredAmount()).isEqualTo(UPDATED_SUM_ASSURED_AMOUNT);
        assertThat(testPolicy.getPremiumMode()).isEqualTo(UPDATED_PREMIUM_MODE);
        assertThat(testPolicy.getBasicPremium()).isEqualTo(UPDATED_BASIC_PREMIUM);
        assertThat(testPolicy.getExtraPremium()).isEqualTo(UPDATED_EXTRA_PREMIUM);
        assertThat(testPolicy.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testPolicy.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPolicy.getTotalPremiun()).isEqualTo(UPDATED_TOTAL_PREMIUN);
        assertThat(testPolicy.getGstFirstYear()).isEqualTo(UPDATED_GST_FIRST_YEAR);
        assertThat(testPolicy.getNetPremium()).isEqualTo(UPDATED_NET_PREMIUM);
        assertThat(testPolicy.getTaxBeneficiary()).isEqualTo(UPDATED_TAX_BENEFICIARY);
        assertThat(testPolicy.getPolicyReceived()).isEqualTo(UPDATED_POLICY_RECEIVED);
        assertThat(testPolicy.getPreviousPolicy()).isEqualTo(UPDATED_PREVIOUS_POLICY);
        assertThat(testPolicy.getPolicyStartDate()).isEqualTo(UPDATED_POLICY_START_DATE);
        assertThat(testPolicy.getPolicyEndDate()).isEqualTo(UPDATED_POLICY_END_DATE);
        assertThat(testPolicy.getPeriod()).isEqualTo(UPDATED_PERIOD);
        assertThat(testPolicy.getClaimDone()).isEqualTo(UPDATED_CLAIM_DONE);
        assertThat(testPolicy.getFreeHeathCheckup()).isEqualTo(UPDATED_FREE_HEATH_CHECKUP);
        assertThat(testPolicy.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testPolicy.getNoOfYear()).isEqualTo(UPDATED_NO_OF_YEAR);
        assertThat(testPolicy.getFloaterSum()).isEqualTo(UPDATED_FLOATER_SUM);
        assertThat(testPolicy.getTpa()).isEqualTo(UPDATED_TPA);
        assertThat(testPolicy.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPolicy.getPolicyType()).isEqualTo(UPDATED_POLICY_TYPE);
        assertThat(testPolicy.getPaToOwner()).isEqualTo(UPDATED_PA_TO_OWNER);
        assertThat(testPolicy.getPaToOther()).isEqualTo(UPDATED_PA_TO_OTHER);
        assertThat(testPolicy.getLoading()).isEqualTo(UPDATED_LOADING);
        assertThat(testPolicy.getRiskCoveredFrom()).isEqualTo(UPDATED_RISK_COVERED_FROM);
        assertThat(testPolicy.getRiskCoveredTo()).isEqualTo(UPDATED_RISK_COVERED_TO);
        assertThat(testPolicy.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testPolicy.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPolicy.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPolicy.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPolicy.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testPolicy.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testPolicy.getMaturityDate()).isEqualTo(UPDATED_MATURITY_DATE);
        assertThat(testPolicy.getUinNo()).isEqualTo(UPDATED_UIN_NO);
        assertThat(testPolicy.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPolicy.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, policyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPolicy() throws Exception {
        int databaseSizeBeforeUpdate = policyRepository.findAll().size();
        policy.setId(count.incrementAndGet());

        // Create the Policy
        PolicyDTO policyDTO = policyMapper.toDto(policy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPolicyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(policyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Policy in the database
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePolicy() throws Exception {
        // Initialize the database
        policyRepository.saveAndFlush(policy);

        int databaseSizeBeforeDelete = policyRepository.findAll().size();

        // Delete the policy
        restPolicyMockMvc
            .perform(delete(ENTITY_API_URL_ID, policy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Policy> policyList = policyRepository.findAll();
        assertThat(policyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
