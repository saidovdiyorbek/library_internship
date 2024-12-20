package intership.libraryintership.repository;

import intership.libraryintership.entity.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Optional<Profile> findByUsernameAndVisibleTrue(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
