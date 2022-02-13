package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Agency;
import com.mycompany.myapp.service.dto.AgencyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agency} and its DTO {@link AgencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgencyMapper extends EntityMapper<AgencyDTO, Agency> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AgencyDTO toDtoId(Agency agency);
}
