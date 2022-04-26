package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;


import javax.persistence.*;

@Entity
@Table(name = "attribute_values")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public
//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "valueId")
class AttributeValue {
    @Id
    @Column(name = "value_id", columnDefinition = "nvarchar(20)")
    private String valueId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attribute_id")
    @JsonManagedReference
    private Attribute attribute;
}
