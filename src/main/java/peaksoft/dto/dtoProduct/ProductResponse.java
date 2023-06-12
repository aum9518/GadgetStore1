package peaksoft.dto.dtoProduct;

import lombok.Builder;
import peaksoft.enums.Category;

import java.util.List;
@Builder
public record ProductResponse(Long id,
                              String name,
                              int price,
                              List<String> images,
                              String characteristic,
                              boolean isFavorite,
                              String madeIn,
                              Category category,
                              List<String> comments,
                              int likes) {
    public ProductResponse {
    }

    public ProductResponse(Long id, String name, int price, List<String> images, String characteristic, boolean isFavorite, String madeIn, Category category) {
        this(id, name, price, images, characteristic, isFavorite, madeIn, category, null, 0);
    }
}
