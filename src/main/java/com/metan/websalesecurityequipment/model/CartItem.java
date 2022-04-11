package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cart_items")
@IdClass(CartItemPK.class)
public @Data
@EqualsAndHashCode(of = {"product", "cart"})
class CartItem {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    private int quantity;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
