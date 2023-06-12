package peaksoft.dto.dtoBasket;

import lombok.Builder;
import peaksoft.entity.Product;

import java.util.List;
@Builder
public record BasketRequest(List<Product> products,
                            Long userId) {
    public BasketRequest {
    }
}
