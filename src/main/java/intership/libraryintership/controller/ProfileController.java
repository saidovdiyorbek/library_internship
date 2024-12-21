package intership.libraryintership.controller;

import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.service.ProfileService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/librarians")
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger log  = Logger.getLogger(ProfileController.class.getName());

    private final ProfileService service;

    @PostMapping("/create")
    public ResponseEntity<ProfileCreateDTO.ProfileResponse> create(@RequestBody @Valid ProfileCreateDTO dto){
        log.info("create profile librarians request");
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileCreateDTO.ProfileResponse>> getAll(){
        log.info("get profile librarians request");
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/update/{profileId}")
    public ResponseEntity<ProfileCreateDTO.ProfileResponse> update(@PathVariable Integer profileId, @RequestBody @Valid ProfileCreateDTO dto){
        log.info("update profile librarians request");
        return ResponseEntity.ok(service.update(profileId, dto));
    }

    @DeleteMapping("/delete/{profileId}")
    public ResponseEntity<String> delete(@PathVariable Integer profileId){
        log.info("delete profile librarians request");
        return ResponseEntity.ok(service.delete(profileId));
    }

    @PutMapping("/update-own")
    public ResponseEntity<ProfileCreateDTO.ProfileResponse> updateOwn(@RequestBody @Valid ProfileCreateDTO.ProfileUpdateOwnerDTO dto){
        log.info("update profile librarians request, profile owner");
        return ResponseEntity.ok(service.updateOwner(dto));
    }
}
