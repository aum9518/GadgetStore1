package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "brand_gen")
    @SequenceGenerator(name = "brand_gen",sequenceName = "brand_seq",allocationSize = 1)
    private Long id;
    private String brandName;
    private String image;
    @OneToMany(mappedBy = "brand",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Product> products;
}
