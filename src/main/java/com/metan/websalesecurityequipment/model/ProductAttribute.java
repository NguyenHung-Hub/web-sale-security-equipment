package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_attributes")
@IdClass(ProductAttributePK.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
@EqualsAndHashCode(of = {"product", "attributeValue"})
class ProductAttribute {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "value_id", columnDefinition = "nvarchar(20)")
    @JsonManagedReference
    private AttributeValue attributeValue;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
}
