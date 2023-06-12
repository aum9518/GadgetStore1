package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoFavorite.FavoriteRequest;
import peaksoft.entity.Favorite;
import peaksoft.repository.FavoriteRepository;
import peaksoft.service.FavoriteService;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    @Override
    public SimpleResponse saveFavorite(FavoriteRequest favoriteRequest) {

        return null;
    }

    @Override
    public SimpleResponse deleteFavorite(Long id) {
        return null;
    }
}
