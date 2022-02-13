package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Rider;
import com.mycompany.myapp.service.dto.RiderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rider} and its DTO {@link RiderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RiderMapper extends EntityMapper<RiderDTO, Rider> {}
