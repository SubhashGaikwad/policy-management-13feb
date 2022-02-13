package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AgencyMapperTest {

    private AgencyMapper agencyMapper;

    @BeforeEach
    public void setUp() {
        agencyMapper = new AgencyMapperImpl();
    }
}
