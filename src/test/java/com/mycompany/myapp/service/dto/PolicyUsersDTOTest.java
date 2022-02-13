package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolicyUsersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyUsersDTO.class);
        PolicyUsersDTO policyUsersDTO1 = new PolicyUsersDTO();
        policyUsersDTO1.setId(1L);
        PolicyUsersDTO policyUsersDTO2 = new PolicyUsersDTO();
        assertThat(policyUsersDTO1).isNotEqualTo(policyUsersDTO2);
        policyUsersDTO2.setId(policyUsersDTO1.getId());
        assertThat(policyUsersDTO1).isEqualTo(policyUsersDTO2);
        policyUsersDTO2.setId(2L);
        assertThat(policyUsersDTO1).isNotEqualTo(policyUsersDTO2);
        policyUsersDTO1.setId(null);
        assertThat(policyUsersDTO1).isNotEqualTo(policyUsersDTO2);
    }
}
