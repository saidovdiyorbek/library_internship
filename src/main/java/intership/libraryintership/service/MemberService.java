package intership.libraryintership.service;

import intership.libraryintership.dto.customResponse.StandardResponse;
import intership.libraryintership.dto.member.MemberCreateDTO;
import intership.libraryintership.entity.Member;
import intership.libraryintership.enums.Role;
import intership.libraryintership.exceptions.AppBadRequestException;
import intership.libraryintership.exceptions.DataNotFoundException;
import intership.libraryintership.exceptions.DuplicateDataException;
import intership.libraryintership.mapper.member.MemberMapper;
import intership.libraryintership.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static intership.libraryintership.util.SpringSecurityUtil.getCurrentUserId;
import static intership.libraryintership.util.SpringSecurityUtil.getCurrentUserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository repository;
    @Qualifier("memberMapper")
    private final MemberMapper mapper;

    public MemberCreateDTO.MemberStandardResponse create(MemberCreateDTO  dto) {
        log.info("create member in service");
        boolean existsEmail = repository.existsByEmail(dto.email());
        boolean existsPhone = repository.existsByPhone(dto.phone());
        if (existsEmail) {
            throw new DuplicateDataException("Email already exists");
        }
        if (existsPhone) {
            throw new DuplicateDataException("Phone already exists");
        }

        Member member = mapper.createDTOToMember(dto);
        member.setCreatedById(getCurrentUserId());
        member.setCreatedAt(LocalDateTime.now());
        member.setMemberShipDate(LocalDateTime.now());
        repository.save(member);
        log.info("create member success in service");
        return new MemberCreateDTO.MemberStandardResponse(member.getId(), member.getName(), member.getEmail(), member.getPhone(), member.getCreatedById(), member.getCreatedAt(), member.getMemberShipDate());
    }

    public List<MemberCreateDTO.MemberStandardResponse> getAll() {
        log.info("get all members in service");
        List<MemberCreateDTO.MemberStandardResponse> members = new ArrayList<>();
        for (Member member : repository.findAll()) {
            members.add(mapper.memberToStandardResponse(member));
        }
        return members;
    }

    public MemberCreateDTO.MemberStandardResponse update(String memberId, MemberCreateDTO dto) {


        Optional<Member> byId = repository.findById(memberId);
        if (byId.isEmpty()) {
            throw new DataNotFoundException("Member not found");
        }
        if (!byId.get().getCreatedById().equals(getCurrentUserId()) && getCurrentUserRole().equals(Role.ROLE_ADMIN)) {
            throw new AppBadRequestException("Whoever created the member can change it.");
        }

        log.info("update member in service");
        Member member = byId.get();
        member.setName(dto.name());
        member.setEmail(dto.email());
        member.setPhone(dto.phone());
        repository.save(member);
        log.info("update member success in service");
        return mapper.memberToStandardResponse(member);
    }

    public String delete(String memberId) {
        log.info("delete member in service");
        repository.findById(memberId).ifPresent(repository::delete);
        return "Deleted "+memberId;
    }

    public StandardResponse findById(String memberId) {
        Optional<Member> byId = repository.findById(memberId);
        if (!byId.isPresent()) {
            return new StandardResponse("Member not found", false, null);
        }
        return new StandardResponse("Member found", true, byId.get());
    }

}
