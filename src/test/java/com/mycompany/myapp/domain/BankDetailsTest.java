package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetails.class);
        BankDetails bankDetails1 = new BankDetails();
        bankDetails1.setId(1L);
        BankDetails bankDetails2 = new BankDetails();
        bankDetails2.setId(bankDetails1.getId());
        assertThat(bankDetails1).isEqualTo(bankDetails2);
        bankDetails2.setId(2L);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
        bankDetails1.setId(null);
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
    }
}
