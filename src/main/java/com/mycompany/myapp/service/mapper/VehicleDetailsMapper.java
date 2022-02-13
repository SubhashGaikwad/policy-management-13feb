package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.VehicleDetails;
import com.mycompany.myapp.service.dto.VehicleDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleDetails} and its DTO {@link VehicleDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleDetailsMapper extends EntityMapper<VehicleDetailsDTO, VehicleDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VehicleDetailsDTO toDtoId(VehicleDetails vehicleDetails);
}
