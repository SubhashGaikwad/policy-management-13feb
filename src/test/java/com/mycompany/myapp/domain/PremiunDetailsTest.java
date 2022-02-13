package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PremiunDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PremiunDetails.class);
        PremiunDetails premiunDetails1 = new PremiunDetails();
        premiunDetails1.setId(1L);
        PremiunDetails premiunDetails2 = new PremiunDetails();
        premiunDetails2.setId(premiunDetails1.getId());
        assertThat(premiunDetails1).isEqualTo(premiunDetails2);
        premiunDetails2.setId(2L);
        assertThat(premiunDetails1).isNotEqualTo(premiunDetails2);
        premiunDetails1.setId(null);
        assertThat(premiunDetails1).isNotEqualTo(premiunDetails2);
    }
}
