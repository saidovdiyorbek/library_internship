package intership.libraryintership.service;

import groovy.util.logging.Slf4j;
import intership.libraryintership.dto.customResponse.StandardResponse;
import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.entity.Profile;
import intership.libraryintership.exceptions.DuplicateProfileException;
import intership.libraryintership.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private static final Logger log  = Logger.getLogger(ProfileService.class.getName());

    private final ProfileRepository repository;
    private final PasswordEncoder passwordEncoder;


    public ProfileCreateDTO create(ProfileCreateDTO dto) {
        log.info("Creating profile in service");
        StandardResponse standardResponse = checkEmailOrUsername(dto.username(), dto.email());
        if (!standardResponse.status()){
            throw new DuplicateProfileException(standardResponse.message());
        }

        Profile profile = new Profile();
        profile.setUsername(dto.username());
        profile.setEmail(dto.email());
        profile.setName(dto.name());
        profile.setPassword(passwordEncoder.encode(dto.password()));
        profile.setUsername(dto.username());
        profile.setRole(dto.role());
        profile.setStatus(dto.status());
        profile.setStartTime(dto.startTime());
        profile.setEndTime(dto.endTime());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setVisible(true);
        repository.save(profile);
        log.info("Profile created successfully in database");
        return dto;
    }

    public StandardResponse checkEmailOrUsername(String username, String email){
        boolean existsByEmail = repository.existsByEmail(email);
        boolean existsByUsername = repository.existsByUsername(username);

        if (existsByEmail && existsByUsername) {
            return new StandardResponse("Email and Username already exists", false);
        }
        if (existsByEmail) {
            return new StandardResponse("Email already exists", false);
        }
        if (existsByUsername) {
            return new StandardResponse("Username already exists", false);
        }
        return new StandardResponse("Username and Email not found", true);
    }
}
