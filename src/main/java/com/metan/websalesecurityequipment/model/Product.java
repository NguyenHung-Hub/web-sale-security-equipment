package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public @Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "productId")
@ToString(exclude = {"category", "productType", "brand"})
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "discount_id")
    private ProductDiscount productDiscount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @JsonIgnoreProperties("products")
    private Brand brand;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
    private String slug;
    @Column(columnDefinition = "text")
    private String longDesc;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties("products")
    private ProductType productType;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private List<ProductAttribute> productAttributes;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private List<ProductReview> productReviews;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("product")
    private List<ProductBackdrop> productBackdrops;
}
