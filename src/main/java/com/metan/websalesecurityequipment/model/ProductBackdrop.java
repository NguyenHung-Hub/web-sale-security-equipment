package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_backdrops")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public @Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "backdropId")
class ProductBackdrop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backdrop_id")
    private long backdropId;
    @Column(name = "file_path")
    private String filePath;
}
