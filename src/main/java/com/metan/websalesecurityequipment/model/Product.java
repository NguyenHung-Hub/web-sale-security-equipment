package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public @Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "productId")
class Product {
    @Id
    @Column(name = "product_id", columnDefinition = "varchar(20)")
    private String productId;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private int quantity;
    @Column(columnDefinition = "decimal(13,2)")
    private Double price;
    @Column(name = "short_desc", columnDefinition = "text")
    private String shortDesc;
    private String thumbnail;
    @Column(columnDefinition = "nvarchar(255)")
    private String title;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIncludeProperties("discountPercent")
    private List<ProductDiscount> productDiscounts;
    private float discountPercentBase;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @JsonManagedReference
    private Brand brand;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderItem> orderItems;
    @Column(nullable = false, unique = true)
    private String slug;
    @Column(columnDefinition = "text")
    private String longDesc;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<ProductAttribute> productAttributes;
    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<ProductReview> productReviews;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ProductBackdrop> productBackdrops;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;
}
