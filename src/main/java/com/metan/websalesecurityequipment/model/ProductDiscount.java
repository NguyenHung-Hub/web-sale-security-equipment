package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_discounts")
@IdClass(ProductDiscountPK.class)
public
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"product", "discount"})
class ProductDiscount {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_discount")
    @JsonManagedReference
    private Discount discount;
    private float discountPercent;

}
