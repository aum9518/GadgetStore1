package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoProduct.AllProductRequest;
import peaksoft.dto.dtoProduct.ProductRequest;
import peaksoft.dto.dtoProduct.ProductResponse;
import peaksoft.enums.Category;

import java.util.List;

public interface ProductService {
  List<AllProductRequest> getAllProducts(Category category, int price);
  SimpleResponse saveProduct(ProductRequest productRequest,Long brandId);
  SimpleResponse updateProduct(Long id, ProductRequest productRequest);
  ProductResponse getProductById(Long id);
  SimpleResponse deleteProductById(Long id);

}
