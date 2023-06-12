package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBasket.BasketRequest;

public interface BasketService {

    SimpleResponse saveBasket(BasketRequest basketRequest);
    SimpleResponse deleteBasketById(Long id);
}
