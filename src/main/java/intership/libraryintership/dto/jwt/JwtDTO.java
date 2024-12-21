package intership.libraryintership.dto.jwt;

import jakarta.validation.constraints.NotBlank;

public record JwtDTO(
        String login,
        String role
) {
    public record TokenDTO(
        String accessToken,
        String refreshToken
    ){}

    public record RefreshTokenRequest(
            @NotBlank(message = "Refresh token not blank")
            String refreshToken
    ){

    }
}
