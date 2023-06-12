package peaksoft.dto.dtoProduct;

import lombok.Builder;
import peaksoft.enums.Category;

import java.util.List;
@Builder
public record ProductRequest(String name,
                             int price,
                             List<String> images,
                             String characteristic,
                             String madeIn,
                             Category category) {
    public ProductRequest {
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int price() {
        return price;
    }

    @Override
    public List<String> images() {
        return images;
    }

    @Override
    public String characteristic() {
        return characteristic;
    }

    @Override
    public String madeIn() {
        return madeIn;
    }

    @Override
    public Category category() {
        return category;
    }
}
