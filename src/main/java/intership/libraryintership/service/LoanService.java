package intership.libraryintership.service;

import intership.libraryintership.dto.customResponse.StandardResponse;
import intership.libraryintership.dto.loan.LoanCreateDTO;
import intership.libraryintership.entity.Book;
import intership.libraryintership.entity.Loan;
import intership.libraryintership.exceptions.*;
import intership.libraryintership.mapper.loan.LoanMapper;
import intership.libraryintership.repository.LoanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import static intership.libraryintership.util.SpringSecurityUtil.getCurrentUserId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private static final Logger log = LoggerFactory.getLogger(LoanService.class);
    private final LoanRepository repository;
    private final BookService bookService;
    private final MemberService memberService;
    @Qualifier("loanMapper")
    private final LoanMapper mapper;

    public LoanCreateDTO.LoanStandardResponse create(LoanCreateDTO  dto) {
        //Book va Member mavjudligini tekshirish
        StandardResponse standardResponse = checkBookAndMember(dto.bookId(), dto.memberId());

        if (!standardResponse.status()){
            throw new AppBadRequestException(standardResponse.message());
        }
        //Shu kitobni ijaralarini tekshirish
        if (!checkLoanCount(dto.bookId())) {
            throw new LoanCountException("All books are on loan "+getFirstReturnDate(dto.bookId()));
        }

        //Member bu kitobni oldin olganmi?
        StandardResponse responseCheckMember = checkMember(dto.bookId(), dto.memberId());
        if (!responseCheckMember.status()){
            throw new StatusOKButException(responseCheckMember.message());
        }
        log.info("create loan in service");

        Loan loan = mapper.createDTOToLoan(dto);
        loan.setCreatedAt(LocalDateTime.now());
        loan.setIssueDate(LocalDate.now());
        repository.save(loan);

        log.info("create loan success in service");
        return new LoanCreateDTO.LoanStandardResponse(loan.getId(), loan.getMemberId(), loan.getBookId(),loan.getIssueDate(),loan.getDueDate(),loan.getReturnDate());
    }

    public List<LoanCreateDTO.LoanStandardResponse> getAll() {
        log.info("get all loans in service");
        List<LoanCreateDTO.LoanStandardResponse> loans = new ArrayList<>();
        for (Loan loan : repository.findAll()) {
            loans.add(mapper.loanToLoanStandardResponse(loan));
        }
        return loans;
    }

    public LoanCreateDTO.LoanStandardResponse update(String loanId, LoanCreateDTO dto) {
        log.info("update loan in service");
        Optional<Loan> byId = repository.findById(loanId);
        if (byId.isEmpty()) {
            throw new DataNotFoundException("Loan not found");
        }

        Loan loan = byId.get();
        loan.setMemberId(dto.memberId());
        loan.setBookId(dto.bookId());
        loan.setDueDate(dto.dueDate());
        repository.save(loan);
        log.info("update loan success in service");
        return mapper.loanToLoanStandardResponse(loan);
    }

    public String delete(String loanId) {
        log.info("delete loan in service");
        repository.findById(loanId).ifPresent(repository::delete);
        return "Deleted "+loanId;
    }

    public StandardResponse checkBookAndMember(String bookId, String memberId) {
        StandardResponse responseBook = bookService.findById(bookId);
        StandardResponse responseMember = memberService.findById(memberId);

        if (!responseBook.status()){
            return new StandardResponse(responseBook.message(), false, null);
        }
        if (!responseMember.status()){
            return new StandardResponse(responseMember.message(), false, null);
        }
        return new StandardResponse(responseBook.message()+" "+responseMember.message(), true, null);
    }

    public Boolean checkLoanCount(String bookId){
        Book book = (Book) bookService.findById(bookId).object();
        int loanCount = repository.countByBookIdAndReturnDateIsNull(bookId);
        if (loanCount < book.getCount()){
            return true;
        }
        return false;
    }

    //Shu id-li ijaraga olinga kitoblarni birinchi qaytariladigan soni
    public String getFirstReturnDate(String bookId){
        LocalDate firstReturnDate = repository.getFirstReturnDate(bookId);
        return "First return date "+firstReturnDate.toString();
    }

    //Member oldin shu kitobni olganmi?
    public StandardResponse checkMember(String bookId, String memberId){
        boolean exists = repository.existsByBookIdAndMemberIdAndReturnDateIsNull(bookId, memberId);
        if (!exists){
            return new StandardResponse("Not yet received by Member", true, null);
        }
        return new StandardResponse("This book has already been taken by this member", false, null);
    }

    public String loanBookReturn(LoanCreateDTO.LoanBookReturnMember dto) {
        log.info("loanBookReturn in service");
        Loan loanReturning = repository.returnBook(dto.email(), dto.title());
        if (loanReturning == null || loanReturning.getReturnDate() != null){
            throw new AppBadRequestException("Return Book not found");
        }
        loanReturning.setReturnDate(LocalDate.now());
        repository.save(loanReturning);
        log.info("loanBookReturn success in service");
        return "Returned "+dto.email()+" "+loanReturning.getReturnDate().toString();
    }

    public int loanCount(String bookId){
        return repository.countByBookIdAndReturnDateIsNull(bookId);
    }
}
