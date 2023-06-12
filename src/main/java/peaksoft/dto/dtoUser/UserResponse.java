package peaksoft.dto.dtoUser;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.ZonedDateTime;
@Builder
public record UserResponse(Long id,
                           String firstName,
                           String lastName,
                           String email,
                           String password,
                           ZonedDateTime createAt,
                           ZonedDateTime updatedAt,
                           Role role,
                           String token) {
    public UserResponse {
    }

    public UserResponse(Long id, String firstName, String lastName, String email, String password, ZonedDateTime createAt, ZonedDateTime updatedAt, Role role) {
        this(id, firstName, lastName, email, password, createAt, updatedAt, role, null);
    }
}
