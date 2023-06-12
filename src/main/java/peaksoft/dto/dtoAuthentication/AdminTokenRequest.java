package peaksoft.dto.dtoAuthentication;

import lombok.Builder;

@Builder
public record AdminTokenRequest(String email) {
    public AdminTokenRequest {
    }
}
