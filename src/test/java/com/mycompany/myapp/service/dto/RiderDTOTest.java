package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RiderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RiderDTO.class);
        RiderDTO riderDTO1 = new RiderDTO();
        riderDTO1.setId(1L);
        RiderDTO riderDTO2 = new RiderDTO();
        assertThat(riderDTO1).isNotEqualTo(riderDTO2);
        riderDTO2.setId(riderDTO1.getId());
        assertThat(riderDTO1).isEqualTo(riderDTO2);
        riderDTO2.setId(2L);
        assertThat(riderDTO1).isNotEqualTo(riderDTO2);
        riderDTO1.setId(null);
        assertThat(riderDTO1).isNotEqualTo(riderDTO2);
    }
}
