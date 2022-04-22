package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "attributes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
@EqualsAndHashCode(of = "attributeId")
class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long attributeId;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @OneToMany(mappedBy = "attribute", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("attribute")
    private List<AttributeValue> attributeValues;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("attribute")
    private Category category;

}
