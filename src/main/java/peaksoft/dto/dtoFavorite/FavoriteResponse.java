package peaksoft.dto.dtoFavorite;

import lombok.Builder;
import peaksoft.entity.User;
@Builder
public record FavoriteResponse(Long id,
                               String userName,
                               String productName) {
    public FavoriteResponse {
    }
}
