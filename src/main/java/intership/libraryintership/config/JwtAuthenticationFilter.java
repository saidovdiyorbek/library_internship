package intership.libraryintership.config;

import intership.libraryintership.dto.jwt.JwtDTO;
import intership.libraryintership.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = header.substring(7).trim();
            JwtDTO decode = JwtUtil.decode(token);

            String login = decode.login();
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication1.getPrincipal();
            System.out.println(((CustomUserDetails) principal).getId());
            System.out.println(((CustomUserDetails) principal).getRole());

            filterChain.doFilter(request, response);
        }catch (JwtException | UsernameNotFoundException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
            return;
        }
    }
}
