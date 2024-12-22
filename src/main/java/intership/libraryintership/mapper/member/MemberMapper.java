package intership.libraryintership.mapper.member;

import intership.libraryintership.dto.member.MemberCreateDTO;
import intership.libraryintership.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member createDTOToMember(MemberCreateDTO memberCreateDTO);
    MemberCreateDTO.MemberStandardResponse memberToStandardResponse(Member member);
}
