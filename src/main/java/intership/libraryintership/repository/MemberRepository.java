package intership.libraryintership.repository;

import intership.libraryintership.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {
    boolean existsByEmailAndPhone(String email, String phone);
}
