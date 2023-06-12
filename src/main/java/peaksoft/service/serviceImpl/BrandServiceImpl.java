package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBrand.BrandRequest;
import peaksoft.dto.dtoBrand.BrandResponse;
import peaksoft.entity.Brand;
import peaksoft.entity.Product;
import peaksoft.repository.BrandRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.service.BrandService;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repository;
    private final ProductRepository productRepository;

    @Override
    public List<BrandResponse> getAllBrands() {
        return repository.getAllBrands();
    }

    @Override
    public SimpleResponse saveBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());
      repository.save(brand);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Brand with name: "+brand.getBrandName()+" successfully saved")
                .build();
    }

    @Override
    public SimpleResponse updateBrandById(Long id, BrandRequest brandRequest) {
        Brand brand = repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Brand is not found!"));
        brand.setBrandName(brandRequest.brandName());
        brand.setImage(brandRequest.image());
        repository.save(brand);

        return
                SimpleResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Brand with name: "+brand.getBrandName()+" successfully updated")
                        .build();
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        Brand brand = repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Brand is not found"));
        return BrandResponse.builder()
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .image(brand.getImage())
                .build();
    }

    @Override
    public SimpleResponse deleteBrandById(Long id) {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }else throw new UsernameNotFoundException("Brand with id: "+id+" is does not exist!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Brand with id: "+id+" is deleted!")
                .build();
    }

    @Override
    public SimpleResponse assignProductToBrand(Long productId, Long brandId) {
        Brand brand = repository.findById(brandId).orElseThrow(() -> new UsernameNotFoundException("Brand is not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new UsernameNotFoundException("Product is not found"));
        List<Product> products = new ArrayList<>();
        products.add(product);
        brand.setProducts(products);
        product.setBrand(brand);
        productRepository.save(product);
        repository.save(brand);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Successfully assigned!")
                .build();
    }
}
