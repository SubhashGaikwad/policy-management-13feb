package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PremiunDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PremiunDetailsDTO.class);
        PremiunDetailsDTO premiunDetailsDTO1 = new PremiunDetailsDTO();
        premiunDetailsDTO1.setId(1L);
        PremiunDetailsDTO premiunDetailsDTO2 = new PremiunDetailsDTO();
        assertThat(premiunDetailsDTO1).isNotEqualTo(premiunDetailsDTO2);
        premiunDetailsDTO2.setId(premiunDetailsDTO1.getId());
        assertThat(premiunDetailsDTO1).isEqualTo(premiunDetailsDTO2);
        premiunDetailsDTO2.setId(2L);
        assertThat(premiunDetailsDTO1).isNotEqualTo(premiunDetailsDTO2);
        premiunDetailsDTO1.setId(null);
        assertThat(premiunDetailsDTO1).isNotEqualTo(premiunDetailsDTO2);
    }
}
