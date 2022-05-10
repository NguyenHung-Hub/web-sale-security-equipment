package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "discounts")
public
@Data
@EqualsAndHashCode(of = "name")
@ToString(exclude = "productDiscounts")
class Discount {
    @Id
    private String name;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @Column(name = "end_date", columnDefinition = "timestamp")
    private Date endDate;
    @OneToMany(mappedBy = "discount", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductDiscount> productDiscounts;


}
