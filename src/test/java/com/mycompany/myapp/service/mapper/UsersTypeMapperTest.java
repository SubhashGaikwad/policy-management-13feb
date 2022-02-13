package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsersTypeMapperTest {

    private UsersTypeMapper usersTypeMapper;

    @BeforeEach
    public void setUp() {
        usersTypeMapper = new UsersTypeMapperImpl();
    }
}
