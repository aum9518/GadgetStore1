package peaksoft.dto.dtoAuthentication;

import lombok.Builder;

@Builder
public record AdminTokenResponse(String email) {
    public AdminTokenResponse {
    }
}
