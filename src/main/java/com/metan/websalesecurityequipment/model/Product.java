package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull(message = "Mã không được rỗng")
    private String productId;
    @Column(columnDefinition = "nvarchar(255)")
    @NotNull(message = "Tên không được rỗng")
    private String name;
    @NotNull(message = "số lượng không được rỗng")
    private int quantity;
    @Column(columnDefinition = "decimal(13,2)")
    @NotNull(message = "Giá không được rỗng")
    private Double price;
    @Column(name = "short_desc", columnDefinition = "text")
    private String shortDesc;
    private String thumbnail;
    @Column(columnDefinition = "nvarchar(255)")
    private String title;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    @JsonIncludeProperties("discountPercent")
    private List<ProductDiscount> productDiscounts;
    private float discountPercentBase;
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
    private List<ProductAttribute> productAttributes;
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductReview> productReviews;
    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductBackdrop> productBackdrops;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products")
    @JsonManagedReference
    private Category category;
}
