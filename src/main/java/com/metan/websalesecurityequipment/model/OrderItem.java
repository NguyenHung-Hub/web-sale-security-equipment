package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_items")
@IdClass(OrderItemPK.class)
public @Data
@EqualsAndHashCode(of = {"product", "order"})
@ToString
class OrderItem {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonManagedReference
    private Order order;
    private int quantity;
    @Column(columnDefinition = "decimal(13,2)")
    private Double lineTotal;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
