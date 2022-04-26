package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String title;
    @Column(columnDefinition = "nvarchar(255)")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}
