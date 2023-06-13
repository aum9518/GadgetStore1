package peaksoft.dto.dtoBasket;

import lombok.Builder;
import peaksoft.entity.Product;

import java.util.List;

@Builder
public record GetAllUsersBasketResponse( List<Product> products,int sumOfPrice, int sizeOfProducts) {
    public GetAllUsersBasketResponse {
    }
}
