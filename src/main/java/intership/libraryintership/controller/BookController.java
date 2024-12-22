package intership.libraryintership.controller;

import intership.libraryintership.dto.book.BookCreateDTO;
import intership.libraryintership.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService service;

    @PostMapping("/create")
    public ResponseEntity<BookCreateDTO.BookStandardResponse> create(@RequestBody @Valid BookCreateDTO dto){
        logger.info("create book");
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookCreateDTO.BookStandardResponse>> getAll(){
        logger.info("getAll book");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookCreateDTO.BookStandardResponse> update(@PathVariable("bookId") String bookId, @RequestBody @Valid BookCreateDTO dto){
        logger.info("update book");
        return ResponseEntity.ok(service.update(bookId, dto));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> delete(@PathVariable("bookId") String bookId){
        logger.info("delete book");
        return ResponseEntity.ok(service.delete(bookId));
    }

    @GetMapping("/getAll-filter")
    public ResponseEntity<List<BookCreateDTO.BookResponseMember>> getAllFilter(@RequestBody @Valid BookCreateDTO.BookFilterDTO dto){
        logger.info("getAll filter book");
        return ResponseEntity.ok(service.getAllFilter(dto));
    }

    @GetMapping("/search-title/{title}")
    public ResponseEntity<List<BookCreateDTO.BookResponseMember>> searchTitle(@PathVariable("title") String title){
        logger.info("search title");
        return ResponseEntity.ok(service.searchTitle(title));
    }

    @GetMapping("/search-author/{author}")
    public ResponseEntity<List<BookCreateDTO.BookResponseMember>> searchAuthor(@PathVariable("author") String author){
        logger.info("search author");
        return ResponseEntity.ok(service.searchAuthor(author));
    }

}
