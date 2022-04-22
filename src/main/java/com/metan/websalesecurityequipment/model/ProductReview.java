package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long reviewId;
    private Float rating;
    @Column(columnDefinition = "nvarchar(255)")
    private String tile;
    @Column(columnDefinition = "nvarchar(255)")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties("productReviews")
    private Product product;
}
