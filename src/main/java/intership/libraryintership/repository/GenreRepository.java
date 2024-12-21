package intership.libraryintership.repository;

import intership.libraryintership.entity.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, String> {
}
