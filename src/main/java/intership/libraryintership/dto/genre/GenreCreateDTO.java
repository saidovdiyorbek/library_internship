package intership.libraryintership.dto.genre;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;

public record GenreCreateDTO(
        @NotBlank
        String title
) {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record GenreStandardResponse(
            String id,
            String title,
            String duplicateGenreId
    ){}
}
