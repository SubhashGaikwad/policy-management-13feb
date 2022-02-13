package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PremiunDetailsMapperTest {

    private PremiunDetailsMapper premiunDetailsMapper;

    @BeforeEach
    public void setUp() {
        premiunDetailsMapper = new PremiunDetailsMapperImpl();
    }
}
