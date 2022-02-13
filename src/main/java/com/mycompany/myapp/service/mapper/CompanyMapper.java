package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyTypeMapper.class })
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "companyType", source = "companyType", qualifiedByName = "id")
    CompanyDTO toDto(Company s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoId(Company company);
}
