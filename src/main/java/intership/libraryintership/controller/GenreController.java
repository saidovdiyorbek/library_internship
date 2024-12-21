package intership.libraryintership.controller;

import intership.libraryintership.dto.genre.GenreCreateDTO;
import intership.libraryintership.service.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genre")
@RequiredArgsConstructor
public class GenreController {
    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);
    private final GenreService service;

    @PostMapping("/create")
    public ResponseEntity<GenreCreateDTO.GenreStandardResponse> create(@RequestBody @Valid GenreCreateDTO dto){
        logger.info("Create genre: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GenreCreateDTO.GenreStandardResponse>> getAll(){
        logger.info("Get all genre");
        return ResponseEntity.ok(service.getAll());
    }
}
