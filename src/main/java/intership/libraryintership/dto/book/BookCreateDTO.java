package intership.libraryintership.dto.book;

import intership.libraryintership.dto.author.AuthorCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookCreateDTO(
        String title,
        String authorId,
        String genreId,
        Integer count
) {

    public record BookStandardResponse(
            String id,
            String title,
            String authorId,
            String genreId,
            Integer count
    ){}
    public record BookResponseMember(
            String title,
            String authorFirstName,
            String authorLastName,
            String genreTitle

    ){}
    public record BookFilterDTO(
            @NotNull
            Boolean available,
            String genre,
            Author author
    ){}
    public record Author(

            String firstName,

            String lastName
    ){}
}
