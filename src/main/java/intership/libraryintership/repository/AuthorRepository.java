package intership.libraryintership.repository;

import intership.libraryintership.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, String> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsById(String id);

}
