package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleClassTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleClass.class);
        VehicleClass vehicleClass1 = new VehicleClass();
        vehicleClass1.setId(1L);
        VehicleClass vehicleClass2 = new VehicleClass();
        vehicleClass2.setId(vehicleClass1.getId());
        assertThat(vehicleClass1).isEqualTo(vehicleClass2);
        vehicleClass2.setId(2L);
        assertThat(vehicleClass1).isNotEqualTo(vehicleClass2);
        vehicleClass1.setId(null);
        assertThat(vehicleClass1).isNotEqualTo(vehicleClass2);
    }
}
