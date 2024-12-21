package intership.libraryintership.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String authorId;

    @Column(nullable = false)
    private String genreId;

    @Column
    private Integer count;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
