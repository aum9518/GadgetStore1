package peaksoft.dto.dtoUser;

import lombok.Builder;
import peaksoft.enums.Role;
@Builder
public record UserRequest(String firsName,
                          String lastName,
                          String email,
                          String password) {
    public UserRequest {
    }
}
