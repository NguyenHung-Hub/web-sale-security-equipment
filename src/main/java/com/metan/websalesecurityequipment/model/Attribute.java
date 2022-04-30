package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonBackReference
    private List<ProductAttribute> productAttributes;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category category;

}
