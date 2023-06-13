package peaksoft.dto.dtoAuthentication;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.ZonedDateTime;

@Builder
public record AuthenticationRequest(String firstName, String lastName, String email, String password) {
    public AuthenticationRequest {
    }
}
