package peaksoft.service.serviceImpl;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoProduct.AllProductRequest;
import peaksoft.dto.dtoProduct.ProductRequest;
import peaksoft.dto.dtoProduct.ProductResponse;
import peaksoft.entity.Brand;
import peaksoft.entity.Favorite;
import peaksoft.entity.Product;
import peaksoft.enums.Category;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.BrandRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.service.ProductService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final BrandRepository brandRepository;

    @Override
    public List<AllProductRequest> getAllProducts(Category category, int price) {
        return repository.getAllProduct(category, price);
    }

    @Override
    public SimpleResponse saveProduct(ProductRequest productRequest, Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new UsernameNotFoundException("Brand is not found"));
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setImages(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setFavorite(false);
        product.setMadeIn(productRequest.madeIn());
        product.setCategory(productRequest.category());
        product.setBrand(brand);
        repository.save(product);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Product with name: "+product.getName()+" successfully saved")
                .build();
    }

    @Override
    public SimpleResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Product with id:" + id + " is not found!"));
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.getImages().addAll(productRequest.images());
        product.setCharacteristic(productRequest.characteristic());
        product.setMadeIn(productRequest.madeIn());
        product.setCategory(productRequest.category());
        repository.save(product);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Product with name: "+product.getName()+" successfully updated!")
                .build();
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("Product with id:" + id + " is not found!"));
        List<String> allProductComments = repository.getAllProductComments(id);
        List<Favorite> favorites = repository.getAllProductFavorites(id);
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .images(product.getImages())
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .comments(allProductComments)
                .likes(favorites.size())
                .build();
    }

    @Override
    public SimpleResponse deleteProductById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }else throw new NotFoundException("User with ID: "+id+" is not found");

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }
}
