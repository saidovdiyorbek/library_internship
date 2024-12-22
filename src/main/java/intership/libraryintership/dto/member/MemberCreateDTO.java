package intership.libraryintership.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record MemberCreateDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String phone
) {
    public record MemberStandardResponse(
            String id,
            String name,
            String email,
            String phone,
            Integer createdById,
            LocalDateTime createAt,
            LocalDateTime memberShipDate){}
}
