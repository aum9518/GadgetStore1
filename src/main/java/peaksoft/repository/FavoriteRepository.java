package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}