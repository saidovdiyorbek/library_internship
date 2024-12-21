package intership.libraryintership.repository;

import intership.libraryintership.entity.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, String> {

    Book existsByTitleAndAuthorIdAndGenreId(String title, String authorId, String genre);
}

