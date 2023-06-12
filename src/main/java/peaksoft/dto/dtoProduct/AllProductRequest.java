package peaksoft.dto.dtoProduct;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import peaksoft.enums.Category;

import java.util.List;
@Builder
@Getter
@Setter
public class AllProductRequest {
    private Long id;
  private  String name;
   private int price;
  private  List<String> images;
  private  String characteristic;
  private boolean isFavorite;
  private  String madeIn;
  private  Category category;

    public AllProductRequest(Long id, String name, int price, List<String> images, String characteristic, boolean isFavorite, String madeIn, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
    }
}
