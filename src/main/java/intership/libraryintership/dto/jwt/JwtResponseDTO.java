package intership.libraryintership.dto.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String email;
    private List<String> roles;
}
