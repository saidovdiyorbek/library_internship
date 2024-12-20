package intership.libraryintership.controller;

import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger log  = Logger.getLogger(ProfileController.class.getName());

    private final ProfileService service;

    @PostMapping("/create")
    public ResponseEntity<ProfileCreateDTO> create(@RequestBody @Valid ProfileCreateDTO dto){
        log.info("create profile librarians request");
        return ResponseEntity.ok(service.create(dto));
    }

}
