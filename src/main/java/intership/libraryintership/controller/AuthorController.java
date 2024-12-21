package intership.libraryintership.controller;

import intership.libraryintership.dto.author.AuthorCreateDTO;
import intership.libraryintership.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);
    private final AuthorService service;

    @PostMapping("/create")
    public ResponseEntity<AuthorCreateDTO.AuthorStandardResponse> create(@RequestBody @Valid AuthorCreateDTO dto) {
        logger.info("Create author: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll-admin")
    public ResponseEntity<List<AuthorCreateDTO.AuthorStandardResponse>> getAll() {
        logger.info("Get all authors");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{authorId}")
    public ResponseEntity<AuthorCreateDTO.AuthorStandardResponse> update(@PathVariable String authorId, @RequestBody @Valid AuthorCreateDTO dto) {
        logger.info("Update author: {}", dto);
        return ResponseEntity.ok(service.update(authorId, dto));
    }

    @DeleteMapping("/delete/{authorId}")
    public ResponseEntity<String> delete(@PathVariable String authorId) {
        logger.info("Delete author: {}", authorId);
        return ResponseEntity.ok(service.delete(authorId));
    }

}
