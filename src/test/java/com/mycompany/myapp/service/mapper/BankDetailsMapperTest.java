package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankDetailsMapperTest {

    private BankDetailsMapper bankDetailsMapper;

    @BeforeEach
    public void setUp() {
        bankDetailsMapper = new BankDetailsMapperImpl();
    }
}
