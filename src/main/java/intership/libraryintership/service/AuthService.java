package intership.libraryintership.service;

import intership.libraryintership.config.CustomUserDetails;
import intership.libraryintership.dto.auth.LoginDTO;
import intership.libraryintership.dto.jwt.JwtResponseDTO;
import intership.libraryintership.entity.Profile;
import intership.libraryintership.repository.ProfileRepository;
import intership.libraryintership.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;


    public JwtResponseDTO login(LoginDTO loginDTO) {
        Optional<Profile> optionalProfile = profileRepository.findByUsernameAndVisibleTrue(loginDTO.username());
        if(!optionalProfile.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
        );

        if (authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


            JwtResponseDTO responseDTO = new JwtResponseDTO();
            responseDTO.setEmail(userDetails.getEmail());
            responseDTO.setToken(JwtUtil.encode(loginDTO.username(), userDetails.getRole().toString()));
            responseDTO.setRefreshToken(JwtUtil.refreshToken(loginDTO.username(), userDetails.getRole().toString() ));
            responseDTO.setRoles(List.of(userDetails.getRole().toString()));

            return responseDTO;
        }
        throw new UsernameNotFoundException("Username or Password wrong");
    }
}
