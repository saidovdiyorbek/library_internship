package intership.libraryintership.service;

import intership.libraryintership.dto.genre.GenreCreateDTO;
import intership.libraryintership.entity.Genre;
import intership.libraryintership.exceptions.DataNotFoundException;
import intership.libraryintership.mapper.genre.GenreMapper;
import intership.libraryintership.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {
    private static final Logger log = LoggerFactory.getLogger(GenreService.class);
    @Qualifier("genreMapper")
    private final GenreMapper mapper;

    private final GenreRepository repository;

    public boolean exists(String genreId) {
        return repository.existsById(genreId);
    }

    public GenreCreateDTO.GenreStandardResponse create(GenreCreateDTO dto) {
        log.info("Create Genre in service: {}", dto);
        Optional<Genre> optionalGenre = repository.findByTitle(dto.title().trim());


        if (!optionalGenre.isPresent()) {
            Genre genre = mapper.genreCreateDTOtoGenre(dto);
            genre.setCreatedAt(LocalDateTime.now());
            repository.save(genre);
            log.info("Create Genre in service not duplicate: {}", dto);
            return new GenreCreateDTO.GenreStandardResponse(genre.getId(), genre.getTitle(), null);
        }
        Genre genre = optionalGenre.get();
        log.info("Create Genre in service already exists: {}", dto);
        return new GenreCreateDTO.GenreStandardResponse(genre.getId(), genre.getTitle(), genre.getId());
    }

    public List<GenreCreateDTO.GenreStandardResponse> getAll() {
        log.info("Get all Genre in service");
        List<GenreCreateDTO.GenreStandardResponse> genres = new ArrayList<>();
        for (Genre genre : repository.findAll()) {
            genres.add(mapper.GenreToGenreStandardResponse(genre));
        }
        log.info("Get all Genre in service not duplicate: {}", genres);
        return genres;
    }

    public GenreCreateDTO.GenreStandardResponse update(String genreId, GenreCreateDTO dto) {
        log.info("Update Genre in service: {}", dto);
        Optional<Genre> byId = repository.findById(genreId);
        if (byId.isEmpty()) {
            throw new DataNotFoundException("Genre not found");
        }
        Genre genre = byId.get();
        genre.setTitle(dto.title().trim());
        repository.save(genre);
        return new GenreCreateDTO.GenreStandardResponse(genre.getId(), genre.getTitle(), null);
    }

    public String delete(String genreId) {
        log.info("delete book in service");
        repository.findById(genreId).ifPresent(repository::delete);
        return "Deleted " + genreId;
    }
}
