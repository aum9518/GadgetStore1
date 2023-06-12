package peaksoft.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoProduct.AllProductRequest;
import peaksoft.dto.dtoProduct.ProductRequest;
import peaksoft.dto.dtoProduct.ProductResponse;
import peaksoft.enums.Category;
import peaksoft.service.ProductService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductAPI {
    private final ProductService service;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public List<AllProductRequest> getAllProducts(@RequestParam String category, @RequestParam int price) {
        Category disiredCategory = Category.valueOf(category.toUpperCase());
        return service.getAllProducts(disiredCategory, price);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveProduct(@RequestBody ProductRequest productRequest) {
        return service.saveProduct(productRequest);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public SimpleResponse updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest){
        return service.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("{id}")
    public ProductResponse getProductById(@PathVariable Long id){
        return service.getProductById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public SimpleResponse deleteProduct(@PathVariable Long id){
        return service.deleteProductById(id);
    }
}
