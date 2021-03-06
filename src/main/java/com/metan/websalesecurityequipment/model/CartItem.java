package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cart_items")
@IdClass(CartItemPK.class)
public @Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"product", "cart"})
class CartItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private Cart cart;
    private int quantity;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
