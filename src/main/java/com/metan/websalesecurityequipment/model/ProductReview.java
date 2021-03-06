package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"reviewId"})
class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;
    private float rating;
    @Column(columnDefinition = "nvarchar(255)")
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
}
