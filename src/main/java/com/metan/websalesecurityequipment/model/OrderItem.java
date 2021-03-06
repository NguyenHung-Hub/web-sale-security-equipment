package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_items")
@IdClass(OrderItemPK.class)
public @Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"product", "order"})
class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
    private int quantity;
    @Column(columnDefinition = "decimal(13,2)")
    private Double lineTotal;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
