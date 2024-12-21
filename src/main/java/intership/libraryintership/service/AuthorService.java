package intership.libraryintership.service;

import intership.libraryintership.dto.author.AuthorCreateDTO;
import intership.libraryintership.entity.Author;
import intership.libraryintership.exceptions.DataNotFoundException;
import intership.libraryintership.exceptions.DuplicateDataException;
import intership.libraryintership.mapper.AuthorMapper;
import intership.libraryintership.repository.AuthorRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final static Logger log = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository repository;
    private final AuthorMapper mapper;


    public AuthorCreateDTO.AuthorStandardResponse create(AuthorCreateDTO dto) {
        log.info("Creating author");
        boolean exists = repository.existsByFirstNameAndLastName(dto.firstName(), dto.lastName());
        if (exists) {
            throw new DuplicateDataException("Author already exists");
        }
        Author author = mapper.authorCreateDTOToAuthor(dto);
        author.setCreatedAt(LocalDateTime.now());
        author = repository.save(author);

        log.info("Author created successfully");
       return new AuthorCreateDTO.AuthorStandardResponse(author.getId(),
                                                          author.getFirstName(),
                                                          author.getLastName(),
                                                          author.getCreatedAt());

    }

    public List<AuthorCreateDTO.AuthorStandardResponse> getAll() {
        log.info("Getting all authors");
        List<AuthorCreateDTO.AuthorStandardResponse> authors = new ArrayList<>();
        for (Author author : repository.findAll()) {
            authors.add(mapper.authorToAuthorStandardResponse(author));
        }
        log.info("Authors retrieved successfully");
        return authors;
    }

    public AuthorCreateDTO.AuthorStandardResponse update(String authorId,AuthorCreateDTO dto) {
        log.info("Updating author");
        repository.findById(authorId).orElseThrow(() -> new DataNotFoundException("Author not found"));
        boolean exists = repository.existsByFirstNameAndLastName(dto.firstName(), dto.lastName());
        if (exists) {
            throw new DuplicateDataException("Author already exists");
        }

        Author author = mapper.authorCreateDTOToAuthor(dto);
        repository.save(author);
        log.info("Author updated successfully");
        AuthorCreateDTO.AuthorStandardResponse response
                = new AuthorCreateDTO.AuthorStandardResponse(author.getId(), author.getFirstName(),
                                                                author.getLastName(), author.getCreatedAt());

        return response;
    }

    public String delete(String authorId) {
        log.info("Deleting author");
        repository.findById(authorId).orElseThrow(() -> new DataNotFoundException("Author not found"));
        Author author = repository.findById(authorId).get();
        repository.delete(author);
        log.info("Author deleted successfully");
        return "Author deleted successfully";
    }

    public boolean exists(String authorId) {
        return repository.existsById(authorId);
    }
}
