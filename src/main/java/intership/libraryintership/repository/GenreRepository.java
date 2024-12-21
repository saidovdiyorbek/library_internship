package intership.libraryintership.repository;

import intership.libraryintership.entity.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, String> {

    Optional<Genre> findByTitle(String genreName);
}
