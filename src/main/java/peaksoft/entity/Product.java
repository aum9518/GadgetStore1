package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.Category;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_gen")
    @SequenceGenerator(name = "product_gen",sequenceName = "product_seq",allocationSize = 1)
    private Long id;
    private String name;
    private int price;
    @Lob
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    @Enumerated(EnumType.STRING)
    private Category category;


    @ManyToMany(mappedBy = "products",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
    private List<Basket> baskets;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Brand brand;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Comment comment;

    @OneToMany(mappedBy = "product",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Favorite> favorite;
}
