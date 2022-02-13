package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PolicyUsersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PolicyUsers.class);
        PolicyUsers policyUsers1 = new PolicyUsers();
        policyUsers1.setId(1L);
        PolicyUsers policyUsers2 = new PolicyUsers();
        policyUsers2.setId(policyUsers1.getId());
        assertThat(policyUsers1).isEqualTo(policyUsers2);
        policyUsers2.setId(2L);
        assertThat(policyUsers1).isNotEqualTo(policyUsers2);
        policyUsers1.setId(null);
        assertThat(policyUsers1).isNotEqualTo(policyUsers2);
    }
}
