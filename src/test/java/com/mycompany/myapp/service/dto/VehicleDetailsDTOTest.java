package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VehicleDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDetailsDTO.class);
        VehicleDetailsDTO vehicleDetailsDTO1 = new VehicleDetailsDTO();
        vehicleDetailsDTO1.setId(1L);
        VehicleDetailsDTO vehicleDetailsDTO2 = new VehicleDetailsDTO();
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO2.setId(vehicleDetailsDTO1.getId());
        assertThat(vehicleDetailsDTO1).isEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO2.setId(2L);
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
        vehicleDetailsDTO1.setId(null);
        assertThat(vehicleDetailsDTO1).isNotEqualTo(vehicleDetailsDTO2);
    }
}
