package intership.libraryintership.repository;

import intership.libraryintership.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
