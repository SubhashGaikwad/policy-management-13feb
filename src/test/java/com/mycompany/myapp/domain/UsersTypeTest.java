package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsersTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsersType.class);
        UsersType usersType1 = new UsersType();
        usersType1.setId(1L);
        UsersType usersType2 = new UsersType();
        usersType2.setId(usersType1.getId());
        assertThat(usersType1).isEqualTo(usersType2);
        usersType2.setId(2L);
        assertThat(usersType1).isNotEqualTo(usersType2);
        usersType1.setId(null);
        assertThat(usersType1).isNotEqualTo(usersType2);
    }
}
