package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleClassDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleClassDTO.class);
        VehicleClassDTO vehicleClassDTO1 = new VehicleClassDTO();
        vehicleClassDTO1.setId(1L);
        VehicleClassDTO vehicleClassDTO2 = new VehicleClassDTO();
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
        vehicleClassDTO2.setId(vehicleClassDTO1.getId());
        assertThat(vehicleClassDTO1).isEqualTo(vehicleClassDTO2);
        vehicleClassDTO2.setId(2L);
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
        vehicleClassDTO1.setId(null);
        assertThat(vehicleClassDTO1).isNotEqualTo(vehicleClassDTO2);
    }
}
