package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
@EqualsAndHashCode(of = "id")
class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> products;
}
