package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public @Data
@EqualsAndHashCode(of = "orderId")
class Order {
    @Id
    @Column(name = "order_id", length = 20)
    private String orderId;
    @Column(name = "due_date", columnDefinition = "datetime")
    private Date dueDate;
    @Column(columnDefinition = "text")
    private String content;
    @Column(columnDefinition = "decimal(13,2)")
    private Double total;
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @OneToMany(mappedBy = "order")
    @JsonBackReference
    private List<OrderItem> orderItems;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
