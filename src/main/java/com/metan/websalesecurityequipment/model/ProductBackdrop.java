package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_backdrops")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
@EqualsAndHashCode(of = "backdropId")
class ProductBackdrop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backdrop_id")
    private long backdropId;
    @Column(name = "file_path")
    private String filePath;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;
}
