package intership.libraryintership.service;

import groovy.util.logging.Slf4j;
import intership.libraryintership.dto.customResponse.StandardResponse;
import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.entity.Profile;
import intership.libraryintership.exceptions.DuplicateProfileException;
import intership.libraryintership.exceptions.UserNotFoundException;
import intership.libraryintership.mapper.profile.ProfileMapper;
import intership.libraryintership.repository.ProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static intership.libraryintership.util.SpringSecurityUtil.getCurrentUserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {
    private static final Logger log  = Logger.getLogger(ProfileService.class.getName());

    private final ProfileRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileMapper profileMapper;


    public ProfileCreateDTO.ProfileResponse create(ProfileCreateDTO dto) {
        log.info("Creating profile in service");
        StandardResponse standardResponse = checkEmailOrUsername(dto.username(), dto.email());
        if (!standardResponse.status()){
            throw new DuplicateProfileException(standardResponse.message());
        }

       /* Profile profile = new Profile();
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
        repository.save(profile);*/

        Profile profile = profileMapper.profileCreateDTOtoProfile(dto);
        profile.setCreatedAt(LocalDateTime.now());
        profile.setVisible(true);
        profile.setPassword(passwordEncoder.encode(dto.password()));
        repository.save(profile);


        log.info("Profile created successfully in database");
        return profileMapper.profileToProfileResponse(profile);
    }

    public StandardResponse checkEmailOrUsername(String username, String email){
        boolean existsByEmail = repository.existsByEmail(email);
        boolean existsByUsername = repository.existsByUsername(username);

        if (existsByEmail && existsByUsername) {
            return new StandardResponse("Email and Username already exists", false, null);
        }
        if (existsByEmail) {
            return new StandardResponse("Email already exists", false, null);
        }
        if (existsByUsername) {
            return new StandardResponse("Username already exists", false, null);
        }
        return new StandardResponse("Username and Email not found", true, null);
    }

    public List<ProfileCreateDTO.ProfileResponse> getAll() {
        log.info("Getting all profiles");
        List<ProfileCreateDTO.ProfileResponse> librarians = new ArrayList<>();

        for (Profile profile : repository.findAll()) {
            librarians.add(profileMapper.profileToProfileResponse(profile));
        }
        log.info("Profiles retrieved successfully");
        return librarians;
    }


    public ProfileCreateDTO.ProfileResponse update(Integer profileId, @Valid ProfileCreateDTO dto) {
        log.info("Updating profile in service");
        Optional<Profile> optionalProfile = repository.findById(profileId);
        if (!optionalProfile.isPresent()) {
            throw new UserNotFoundException("Profile not found");
        }

        boolean checkEmail = repository.existsByEmail(dto.email());
        boolean checkUsername = repository.existsByUsername(dto.username());

        if (checkEmail) {
            throw new DuplicateProfileException("email already exists");
        }
        if (checkUsername) {
            throw new DuplicateProfileException(dto.username()+" already exists");
        }

        Profile profile = profileMapper.profileCreateDTOtoProfile(dto);
        repository.save(profile);
        log.info("Profile updated successfully");
        return profileMapper.profileToProfileResponse(profile);
    }

    public String delete(Integer profileId) {
        log.info("Updating profile in service");
        Optional<Profile> optionalProfile = repository.findById(profileId);
        if (!optionalProfile.isPresent()) {
            throw new UserNotFoundException("Profile not found");
        }

        repository.delete(optionalProfile.get());
        log.info("Profile deleted successfully");
        return "Profile deleted successfully";
    }

    public ProfileCreateDTO.ProfileResponse updateOwner(ProfileCreateDTO.ProfileUpdateOwnerDTO dto) {
        log.info("Updating profile in service owner");
        Integer currentUserId = getCurrentUserId();
        Optional<Profile> currentProfile = repository.findById(currentUserId);
        Profile profile = null;
        if (currentProfile.isPresent()) {
            profile = currentProfile.get();
            profile.setUsername(dto.username());
            profile.setPassword(passwordEncoder.encode(dto.password()));
            profile.setName(dto.name());
            repository.save(profile);
            log.info("Profile updated successfully");
        }
        return profileMapper.profileToProfileResponse(profile);
    }
}
