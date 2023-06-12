package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoFavorite.FavoriteRequest;

public interface FavoriteService {
    SimpleResponse saveFavorite(FavoriteRequest favoriteRequest);
    SimpleResponse deleteFavorite(Long id);

}
