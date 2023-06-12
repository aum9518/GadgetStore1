package peaksoft.dto.dtoAuthentication;

import lombok.Builder;

@Builder
public record SignIn(String email,String password) {
    public SignIn {
    }
}
