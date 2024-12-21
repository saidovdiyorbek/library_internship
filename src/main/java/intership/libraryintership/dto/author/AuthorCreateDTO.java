package intership.libraryintership.dto.author;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record AuthorCreateDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName
) {
    public record AuthorStandardResponse(
            String id,
            String firstName,
            String lastName,
            LocalDateTime createdAt
    ){}
}
