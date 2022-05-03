package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;
    private Float rating;
    @Column(columnDefinition = "nvarchar(255)")
    private String title;
    @Column(columnDefinition = "nvarchar(255)")
    private String content;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}
