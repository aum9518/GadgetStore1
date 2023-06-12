package peaksoft.dto.dtoBrand;

import lombok.Builder;

@Builder
public record BrandResponse(Long id,
                            String brandName,
                            String image) {
    public BrandResponse {
    }
}
