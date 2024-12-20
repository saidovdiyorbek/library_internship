package intership.libraryintership.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    private String title;
    private String authorId;
    private String genreId;
    private Integer count;
    private LocalDateTime createdAt;
}
