package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BankDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetailsDTO.class);
        BankDetailsDTO bankDetailsDTO1 = new BankDetailsDTO();
        bankDetailsDTO1.setId(1L);
        BankDetailsDTO bankDetailsDTO2 = new BankDetailsDTO();
        assertThat(bankDetailsDTO1).isNotEqualTo(bankDetailsDTO2);
        bankDetailsDTO2.setId(bankDetailsDTO1.getId());
        assertThat(bankDetailsDTO1).isEqualTo(bankDetailsDTO2);
        bankDetailsDTO2.setId(2L);
        assertThat(bankDetailsDTO1).isNotEqualTo(bankDetailsDTO2);
        bankDetailsDTO1.setId(null);
        assertThat(bankDetailsDTO1).isNotEqualTo(bankDetailsDTO2);
    }
}
