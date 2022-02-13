package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.service.dto.AddressDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring", uses = { PolicyUsersMapper.class, CompanyMapper.class })
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "policyUsers", source = "policyUsers", qualifiedByName = "id")
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    AddressDTO toDto(Address s);
}
