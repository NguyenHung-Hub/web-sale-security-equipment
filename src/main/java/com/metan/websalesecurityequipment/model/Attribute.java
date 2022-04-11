package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attributes")
public @Data
@EqualsAndHashCode(of = "attributeId")
class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attributeId;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @OneToMany(mappedBy = "attribute")
    private List<AttributeValue> attributeValues;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
