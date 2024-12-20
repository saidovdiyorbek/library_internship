package intership.libraryintership.config;

import intership.libraryintership.entity.Profile;
import intership.libraryintership.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> optionalProfile = profileRepository.findByUsernameAndVisibleTrue(username);

        if (optionalProfile.isEmpty()){
            throw new UsernameNotFoundException(username);
        }

        Profile profile = optionalProfile.get();
        return new CustomUserDetails(profile);
    }
}
