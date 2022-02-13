package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ParameterLookup;
import com.mycompany.myapp.service.dto.ParameterLookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterLookup} and its DTO {@link ParameterLookupDTO}.
 */
@Mapper(componentModel = "spring", uses = { VehicleDetailsMapper.class })
public interface ParameterLookupMapper extends EntityMapper<ParameterLookupDTO, ParameterLookup> {
    @Mapping(target = "vehicleDetails", source = "vehicleDetails", qualifiedByName = "id")
    ParameterLookupDTO toDto(ParameterLookup s);
}
