package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PolicyMapperTest {

    private PolicyMapper policyMapper;

    @BeforeEach
    public void setUp() {
        policyMapper = new PolicyMapperImpl();
    }
}
