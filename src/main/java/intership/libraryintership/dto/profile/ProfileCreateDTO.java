package intership.libraryintership.dto.profile;

import intership.libraryintership.enums.Role;
import intership.libraryintership.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalTime;

public record ProfileCreateDTO(
        @NotNull(message = "Name cannot be null")
        String name,

        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotNull(message = "Password cannot be null")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotNull(message = "Role cannot be null")
        Role role,

        @NotNull(message = "Status cannot be null")
        Status status,

        LocalTime startTime,
        LocalTime endTime
) {
}
