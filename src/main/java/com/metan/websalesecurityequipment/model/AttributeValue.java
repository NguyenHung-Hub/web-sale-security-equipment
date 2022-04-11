package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "attribute_values")
public @Data
@EqualsAndHashCode(of = "valueId")
class AttributeValue {
    @Id
    @Column(name = "value_id", columnDefinition = "nvarchar(20)")
    private String valueId;
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;
}
