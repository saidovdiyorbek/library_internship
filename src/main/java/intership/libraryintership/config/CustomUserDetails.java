package intership.libraryintership.config;

import intership.libraryintership.entity.Profile;
import intership.libraryintership.enums.Role;
import intership.libraryintership.enums.Status;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private Integer id;
    private String name;
    private String username;
    private String password;
    private String email;
    private Status status;
    private Role role;

    public CustomUserDetails(Profile profile) {
        this.id = profile.getId();
        this.name = profile.getName();
        this.username = profile.getUsername();
        this.password = profile.getPassword();
        this.email = profile.getEmail();
        this.status = profile.getStatus();
        this.role = profile.getRole();
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return  status == Status.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
