package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_discounts")
public @Data
@EqualsAndHashCode(of = "discountId")
class ProductDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private long discountId;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "discount_percent")
    private Float discountPercent;
    private boolean active;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
