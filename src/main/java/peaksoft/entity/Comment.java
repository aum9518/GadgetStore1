package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment_gen")
    @SequenceGenerator(name = "comment_gen",sequenceName = "comment_seq",allocationSize = 1)
    private Long id;
    private String comment;
    private ZonedDateTime createdAt;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private User user;
    @OneToMany(mappedBy = "comment",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private List<Product> product;
}
