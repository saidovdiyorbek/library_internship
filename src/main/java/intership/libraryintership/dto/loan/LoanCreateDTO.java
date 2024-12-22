package intership.libraryintership.dto.loan;

import intership.libraryintership.exceptions.AppBadRequestException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanCreateDTO(
        @NotBlank
        String memberId,
        @NotBlank
        String bookId,
        @NotNull
        LocalDate dueDate
) {
    public LoanCreateDTO{
        if (dueDate != null && !dueDate.isAfter(LocalDate.now())) {
            throw new AppBadRequestException("Due date must be after returned date");
        }
    }
    public record LoanStandardResponse(
            String id,
            String memberId,
            String bookId,
            LocalDate issueDate,
            LocalDate dueDate,
            LocalDate returnDate
    ){}
    public record LoanBookReturnMember(
            @NotBlank
            @Email
            String email,
            @NotBlank
            String title
    ){}
}
