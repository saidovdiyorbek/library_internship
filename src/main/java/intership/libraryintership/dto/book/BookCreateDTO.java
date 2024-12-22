package intership.libraryintership.dto.book;

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

}
