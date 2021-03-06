package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "categories")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"categoryId"})
@ToString(exclude = {"products", "categories"})
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long categoryId;
    @Column(name = "description", columnDefinition = "text")
    private String desc;
    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @Column(name = "thumbnail")
    private String thumbnail;
    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    @JsonBackReference
    private Category category;
    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Category> categories;
}
