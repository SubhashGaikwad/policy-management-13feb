package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDetailsMapperTest {

    private ProductDetailsMapper productDetailsMapper;

    @BeforeEach
    public void setUp() {
        productDetailsMapper = new ProductDetailsMapperImpl();
    }
}
