package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.BankDetails;
import com.mycompany.myapp.service.dto.BankDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankDetails} and its DTO {@link BankDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BankDetailsMapper extends EntityMapper<BankDetailsDTO, BankDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankDetailsDTO toDtoId(BankDetails bankDetails);
}
