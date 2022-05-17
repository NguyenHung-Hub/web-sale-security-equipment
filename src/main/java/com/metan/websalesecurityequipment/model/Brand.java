package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

import lombok.EqualsAndHashCode;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "brands")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"brandId"})
@ToString(exclude = {"products"})
class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private long brandId;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @Column(columnDefinition = "nvarchar(255)")
    private String country;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @OneToMany(mappedBy = "brand")
    @JsonBackReference("brand")
    private List<Product> products;
}
