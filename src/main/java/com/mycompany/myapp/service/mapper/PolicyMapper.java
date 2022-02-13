package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Policy;
import com.mycompany.myapp.service.dto.PolicyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Policy} and its DTO {@link PolicyDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        AgencyMapper.class,
        CompanyMapper.class,
        ProductMapper.class,
        PremiunDetailsMapper.class,
        VehicleClassMapper.class,
        BankDetailsMapper.class,
        PolicyUsersMapper.class,
    }
)
public interface PolicyMapper extends EntityMapper<PolicyDTO, Policy> {
    @Mapping(target = "agency", source = "agency", qualifiedByName = "id")
    @Mapping(target = "company", source = "company", qualifiedByName = "id")
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "premiunDetails", source = "premiunDetails", qualifiedByName = "id")
    @Mapping(target = "vehicleClass", source = "vehicleClass", qualifiedByName = "id")
    @Mapping(target = "bankDetails", source = "bankDetails", qualifiedByName = "id")
    @Mapping(target = "policyUsers", source = "policyUsers", qualifiedByName = "id")
    PolicyDTO toDto(Policy s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PolicyDTO toDtoId(Policy policy);
}
