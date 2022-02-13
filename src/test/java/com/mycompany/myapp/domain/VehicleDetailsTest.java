package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDetails.class);
        VehicleDetails vehicleDetails1 = new VehicleDetails();
        vehicleDetails1.setId(1L);
        VehicleDetails vehicleDetails2 = new VehicleDetails();
        vehicleDetails2.setId(vehicleDetails1.getId());
        assertThat(vehicleDetails1).isEqualTo(vehicleDetails2);
        vehicleDetails2.setId(2L);
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
        vehicleDetails1.setId(null);
        assertThat(vehicleDetails1).isNotEqualTo(vehicleDetails2);
    }
}
