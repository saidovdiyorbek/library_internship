package intership.libraryintership.mapper;

import intership.libraryintership.dto.book.BookCreateDTO;
import intership.libraryintership.entity.Book;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {
    BookCreateDTO.BookStandardResponse toBookStandardResponse(Book book);
    Book toBook(BookCreateDTO bookCreateDTO);
}
