package intership.libraryintership.repository;

import intership.libraryintership.entity.Book;
import intership.libraryintership.entity.Loan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LoanRepository extends CrudRepository<Loan, String> {
    int countByBookIdAndReturnDateIsNull(String bookId);

    boolean existsByBookIdAndMemberIdAndReturnDateIsNull(String bookId, String memberId);

    @Query("select l.dueDate from Loan l where l.bookId = ?1 order by l.dueDate desc limit 1")
    LocalDate getFirstReturnDate(String bookId);

    /*@Query("select case when count(l) > 0 then true else false end " +
            "from Loan l " +
            "join l.member m " +
            "join l.book b " +
            "where m.email = :email and b.title = :title")
    boolean returnBook(@Param("email") String email, @Param("title") String title);*/

    @Query("from Loan l " +
            "join l.member m " +
            "join l.book b " +
            "where m.email = :email and b.title = :title and l.returnDate is null ")
    Loan returnBook(@Param("email") String email, @Param("title") String title);
}
