package intership.libraryintership.controller;

import intership.libraryintership.dto.auth.LoginDTO;
import intership.libraryintership.dto.jwt.JwtDTO;
import intership.libraryintership.dto.jwt.JwtResponseDTO;
import intership.libraryintership.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(service.login(loginDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtDTO.TokenDTO> refresh(@RequestBody @Valid JwtDTO.RefreshTokenRequest refresh) {
        return ResponseEntity.ok(service.refresh(refresh));
    }

}
