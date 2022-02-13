package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RiderMapperTest {

    private RiderMapper riderMapper;

    @BeforeEach
    public void setUp() {
        riderMapper = new RiderMapperImpl();
    }
}
