package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_attributes")
@IdClass(ProductAttributePK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"product", "attribute"})
class ProductAttribute {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attribute_id")
    @JsonManagedReference
    private Attribute attribute;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    private String value;
}
