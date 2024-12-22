package intership.libraryintership.mapper.loan;

import intership.libraryintership.dto.loan.LoanCreateDTO;
import intership.libraryintership.entity.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan createDTOToLoan(LoanCreateDTO dto);
    LoanCreateDTO.LoanStandardResponse loanToLoanStandardResponse(Loan loan);

}
