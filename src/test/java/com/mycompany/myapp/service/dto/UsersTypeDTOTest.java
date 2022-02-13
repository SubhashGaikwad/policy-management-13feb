package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsersTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersTypeDTO.class);
        UsersTypeDTO usersTypeDTO1 = new UsersTypeDTO();
        usersTypeDTO1.setId(1L);
        UsersTypeDTO usersTypeDTO2 = new UsersTypeDTO();
        assertThat(usersTypeDTO1).isNotEqualTo(usersTypeDTO2);
        usersTypeDTO2.setId(usersTypeDTO1.getId());
        assertThat(usersTypeDTO1).isEqualTo(usersTypeDTO2);
        usersTypeDTO2.setId(2L);
        assertThat(usersTypeDTO1).isNotEqualTo(usersTypeDTO2);
        usersTypeDTO1.setId(null);
        assertThat(usersTypeDTO1).isNotEqualTo(usersTypeDTO2);
    }
}
