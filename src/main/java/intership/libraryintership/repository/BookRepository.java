package intership.libraryintership.repository;

import intership.libraryintership.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    Book findByTitleAndAuthorIdAndGenreId(String title, String authorId, String genre);

    @Query("select b.id from Book b")
    List<String> findByIdIs();


    @Query("from Book b " +
            "join b.genre g " +
            "where g.title = :genre")
    List<Book> findByGenre(@Param("genre") String genre);

}

