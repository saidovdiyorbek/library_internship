package intership.libraryintership.service;

import intership.libraryintership.dto.book.BookCreateDTO;
import intership.libraryintership.entity.Book;
import intership.libraryintership.exceptions.AppBadRequestException;
import intership.libraryintership.exceptions.DataNotFoundException;
import intership.libraryintership.mapper.BookMapper;
import intership.libraryintership.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;
    private final BookMapper mapper;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookCreateDTO.BookStandardResponse create(BookCreateDTO dto) {
        boolean existsAuthor = authorService.exists(dto.authorId());
        boolean existsGenre = genreService.exists(dto.genreId());

        if (existsAuthor) {
            throw new DataNotFoundException("Author not found " + dto.authorId());
        }
        if (existsGenre) {
            throw new DataNotFoundException("Genre not found " + dto.genreId());
        }

        logger.info("create book");
        Book book = repository.existsByTitleAndAuthorIdAndGenreId(dto.title(), dto.authorId(), dto.genreId());
        if (book != null) {
            throw new AppBadRequestException("This book is available, please increase the quantity" + book.getId());
        }
        Book book1 = mapper.toBook(dto);
        book1.setCreatedAt(LocalDateTime.now());
        repository.save(book1);
        logger.info("create book successful");
        return mapper.toBookStandardResponse(book1);
    }

    public List<BookCreateDTO.BookStandardResponse> getAll() {
        List<BookCreateDTO.BookStandardResponse> books = new ArrayList<>();

        for (Book book : repository.findAll()) {
            books.add(mapper.toBookStandardResponse(book));
        }
        return books;
    }

    public BookCreateDTO.BookStandardResponse update(String bookId, BookCreateDTO dto) {
        Optional<Book> optionalBook = repository.findById(bookId);
        if (!optionalBook.isPresent()) {
            throw new DataNotFoundException("Book not found " + bookId);
        }
        Book book = mapper.toBook(dto);
        repository.save(book);
        return mapper.toBookStandardResponse(book);
    }
}
