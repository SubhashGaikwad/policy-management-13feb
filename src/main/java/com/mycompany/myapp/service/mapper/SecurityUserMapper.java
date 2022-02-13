package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.service.dto.SecurityUserDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link SecurityUserDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityPermissionMapper.class, SecurityRoleMapper.class })
public interface SecurityUserMapper extends EntityMapper<SecurityUserDTO, SecurityUser> {
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "nameSet")
    @Mapping(target = "securityRoles", source = "securityRoles", qualifiedByName = "nameSet")
    SecurityUserDTO toDto(SecurityUser s);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    SecurityUser toEntity(SecurityUserDTO securityUserDTO);

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    SecurityUserDTO toDtoLogin(SecurityUser securityUser);
}
