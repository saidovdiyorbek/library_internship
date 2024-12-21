package intership.libraryintership.mapper;

import intership.libraryintership.dto.member.MemberCreateDTO;
import intership.libraryintership.entity.Member;
import org.mapstruct.Mapper;

@Mapper
public interface MemberMapper {
    Member createDTOToMember(MemberCreateDTO memberCreateDTO);
    MemberCreateDTO.MemberStandardResponse memberToStandardResponse(Member member);
}
