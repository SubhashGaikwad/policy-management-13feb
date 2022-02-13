package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VehicleClassMapperTest {

    private VehicleClassMapper vehicleClassMapper;

    @BeforeEach
    public void setUp() {
        vehicleClassMapper = new VehicleClassMapperImpl();
    }
}
