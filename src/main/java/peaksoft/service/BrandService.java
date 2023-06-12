package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoBrand.BrandRequest;
import peaksoft.dto.dtoBrand.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAllBrands();
    SimpleResponse saveBrand(BrandRequest brandRequest);
    SimpleResponse updateBrandById(Long id, BrandRequest brandRequest);
    BrandResponse getBrandById(Long id);
    SimpleResponse deleteBrandById(Long id);
    SimpleResponse assignProductToBrand(Long productId,Long brandId);
}
