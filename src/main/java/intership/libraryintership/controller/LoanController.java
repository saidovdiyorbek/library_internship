package intership.libraryintership.controller;

import intership.libraryintership.dto.loan.LoanCreateDTO;
import intership.libraryintership.dto.member.MemberCreateDTO;
import intership.libraryintership.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
@RequiredArgsConstructor
public class LoanController {
    private static final Logger log = LoggerFactory.getLogger(LoanController.class);
    private final LoanService service;

    @PostMapping("/create")
    public ResponseEntity<LoanCreateDTO.LoanStandardResponse> create(@RequestBody @Valid LoanCreateDTO dto){
        log.info("create loan");
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<LoanCreateDTO.LoanStandardResponse>> getAll(){
        log.info("getAll loan");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{loanId}")
    public ResponseEntity<LoanCreateDTO.LoanStandardResponse> update(@PathVariable("loanId") String loanId, @RequestBody LoanCreateDTO dto){
        log.info("Update loan: {}", loanId);
        return ResponseEntity.ok(service.update(loanId, dto));
    }

    @DeleteMapping("/delete/{loanId}")
    public ResponseEntity<String> delete(@PathVariable("loanId") String loanId){
        log.info("Delete loan: {}", loanId);
        return ResponseEntity.ok(service.delete(loanId));
    }

    @PutMapping("/return-book-member")
    public ResponseEntity<String> loanBookReturnMember(@RequestBody @Valid LoanCreateDTO.LoanBookReturnMember dto){
        log.info("Return book member: {}", dto.email());
        return ResponseEntity.ok(service.loanBookReturn(dto));
    }

}
