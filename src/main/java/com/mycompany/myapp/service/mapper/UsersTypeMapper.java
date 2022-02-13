package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.UsersType;
import com.mycompany.myapp.service.dto.UsersTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsersType} and its DTO {@link UsersTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UsersTypeMapper extends EntityMapper<UsersTypeDTO, UsersType> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsersTypeDTO toDtoId(UsersType usersType);
}
