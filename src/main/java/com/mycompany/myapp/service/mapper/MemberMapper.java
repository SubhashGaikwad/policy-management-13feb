package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.service.dto.MemberDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring", uses = { PolicyMapper.class })
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {
    @Mapping(target = "policy", source = "policy", qualifiedByName = "id")
    MemberDTO toDto(Member s);
}
