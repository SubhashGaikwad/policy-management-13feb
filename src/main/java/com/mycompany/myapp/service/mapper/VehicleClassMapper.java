package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.VehicleClass;
import com.mycompany.myapp.service.dto.VehicleClassDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleClass} and its DTO {@link VehicleClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleClassMapper extends EntityMapper<VehicleClassDTO, VehicleClass> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    VehicleClassDTO toDtoId(VehicleClass vehicleClass);
}
