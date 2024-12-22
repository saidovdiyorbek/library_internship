package intership.libraryintership.mapper.book;

import intership.libraryintership.dto.book.BookCreateDTO;
import intership.libraryintership.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookCreateDTO.BookStandardResponse toBookStandardResponse(Book book);
    Book toBook(BookCreateDTO bookCreateDTO);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "genreTitle", source = "genre.title")
    BookCreateDTO.BookResponseMember bookToBookResponseMember(Book book);
}
