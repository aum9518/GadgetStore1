package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket, Long> {
}