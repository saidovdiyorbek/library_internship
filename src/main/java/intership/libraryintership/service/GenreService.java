package intership.libraryintership.service;

import intership.libraryintership.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public boolean exists(String genreId) {
        return genreRepository.existsById(genreId);
    }
}
