package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyTypeDTO.class);
        CompanyTypeDTO companyTypeDTO1 = new CompanyTypeDTO();
        companyTypeDTO1.setId(1L);
        CompanyTypeDTO companyTypeDTO2 = new CompanyTypeDTO();
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
        companyTypeDTO2.setId(companyTypeDTO1.getId());
        assertThat(companyTypeDTO1).isEqualTo(companyTypeDTO2);
        companyTypeDTO2.setId(2L);
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
        companyTypeDTO1.setId(null);
        assertThat(companyTypeDTO1).isNotEqualTo(companyTypeDTO2);
    }
}
