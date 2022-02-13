package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyTypeMapperTest {

    private CompanyTypeMapper companyTypeMapper;

    @BeforeEach
    public void setUp() {
        companyTypeMapper = new CompanyTypeMapperImpl();
    }
}
