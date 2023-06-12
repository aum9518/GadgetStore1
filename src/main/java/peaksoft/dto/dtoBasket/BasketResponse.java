package peaksoft.dto.dtoBasket;

import lombok.Builder;
import peaksoft.entity.Product;

import java.util.List;
@Builder
public record BasketResponse(Long Id,
                             List<String> productName,
                             String userName) {
}
