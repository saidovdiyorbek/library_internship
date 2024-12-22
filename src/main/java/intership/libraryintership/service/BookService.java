package intership.libraryintership.service;

import intership.libraryintership.dto.book.BookCreateDTO;
import intership.libraryintership.dto.customResponse.StandardResponse;
import intership.libraryintership.entity.Book;
import intership.libraryintership.exceptions.BookAlreadyExistsException;
import intership.libraryintership.exceptions.DataNotFoundException;
import intership.libraryintership.mapper.book.BookMapper;
import intership.libraryintership.repository.BookRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
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
    private final AuthorService authorService;
    private final GenreService genreService;
    @Autowired
    @Lazy
    LoanService loanService;

    @Qualifier("bookMapper")
    private final BookMapper mapper;

    public BookCreateDTO.BookStandardResponse create(BookCreateDTO dto) {
        boolean existsAuthor = authorService.exists(dto.authorId());
        boolean existsGenre = genreService.exists(dto.genreId());

        if (!existsAuthor) {
            throw new DataNotFoundException("Author not found " + dto.authorId());
        }
        if (!existsGenre) {
            throw new DataNotFoundException("Genre not found " + dto.genreId());
        }

        logger.info("create book");
        Book book = repository.findByTitleAndAuthorIdAndGenreId(dto.title(), dto.authorId(), dto.genreId());
        if (book != null) {
            book.setCount(book.getCount() + dto.count());
            repository.save(book);
            throw new BookAlreadyExistsException("This book already exists, automatically increased book count" + book.getId() +" "+ dto.count());
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
        logger.info("update book in service");
        Optional<Book> optionalBook = repository.findById(bookId);
        if (!optionalBook.isPresent()) {
            throw new DataNotFoundException("Book not found " + bookId);
        }
        Book book = optionalBook.get();
        book.setTitle(dto.title());
        book.setAuthorId(dto.authorId());
        book.setGenreId(dto.genreId());
        repository.save(book);
        logger.info("update book successful in service");
        return mapper.toBookStandardResponse(book);
    }

    public String delete(String bookId) {
        logger.info("delete book in service");
        repository.findById(bookId).ifPresent(repository::delete);
        return bookId;
    }

    public StandardResponse findById(String bookId) {
        Optional<Book> byId = repository.findById(bookId);
        if (!byId.isPresent()) {
            return new StandardResponse("Book not found", false, null);
        }
        return new StandardResponse("Book found", true, byId.get());
    }

    public List<BookCreateDTO.BookResponseMember> getAllGenre(String genre) {
        List<BookCreateDTO.BookResponseMember> books = new ArrayList<>();

        for (Book book : repository.findByGenre(genre)) {
            books.add(mapper.bookToBookResponseMember(book));
        }
        return books;
    }

    public List<BookCreateDTO.BookResponseMember> getAllFilter(BookCreateDTO.BookFilterDTO dto) {
        List<BookCreateDTO.BookResponseMember> books = new ArrayList<>();

        for (String bookId : repository.findByIdIs()) {
            Optional<Book> optionalBook = repository.findById(bookId);

            if (!optionalBook.isPresent()) {
                throw new DataNotFoundException("Book not found " + bookId);
            }
            Book book = optionalBook.get();
            int loanCount = loanService.loanCount(bookId);
            if (book.getCount() > loanCount) {

                String dtoFirstName = dto.author().firstName();
                String dtoLastName = dto.author().lastName();

                if (book.getAuthor().getFirstName().equals(dtoFirstName) &&
                        book.getAuthor().getLastName().equals(dtoLastName)) {
                    if (book.getGenre().getTitle().equals(dto.genre())) {
                        books.add(mapper.bookToBookResponseMember(optionalBook.get()));
                    }
                }
            }

        }
        return books;
    }

    public List<BookCreateDTO.BookResponseMember> searchTitle(String title) {
        logger.info("search title in service");
        List<BookCreateDTO.BookResponseMember> books = new ArrayList<>();

        for (Book book : repository.findByTitle(title)) {
            books.add(mapper.bookToBookResponseMember(book));
        }
        return books;
    }

    public List<BookCreateDTO.BookResponseMember> searchAuthor(String author) {
        logger.info("search author in service");
        List<BookCreateDTO.BookResponseMember> books = new ArrayList<>();

        for (Book book : repository.findByAuthor(author)) {
            books.add(mapper.bookToBookResponseMember(book));
        }
        return books;
    }
}
