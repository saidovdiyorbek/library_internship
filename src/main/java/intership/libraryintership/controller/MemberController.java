package intership.libraryintership.controller;

import intership.libraryintership.dto.genre.GenreCreateDTO;
import intership.libraryintership.dto.member.MemberCreateDTO;
import intership.libraryintership.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService service;

    @PostMapping("/create")
    public ResponseEntity<MemberCreateDTO.MemberStandardResponse> create(@RequestBody @Valid MemberCreateDTO dto){
        log.info("create member");
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MemberCreateDTO.MemberStandardResponse>> getAll(){
        log.info("getAll member");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity<MemberCreateDTO.MemberStandardResponse> update(@PathVariable("memberId") String memberId, @RequestBody MemberCreateDTO dto){
        log.info("Update member: {}", memberId);
        return ResponseEntity.ok(service.update(memberId, dto));
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity<String> delete(@PathVariable("memberId") String memberId){
        log.info("Delete member: {}", memberId);
        return ResponseEntity.ok(service.delete(memberId));
    }
}
