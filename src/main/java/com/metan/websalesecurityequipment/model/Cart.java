package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
public @Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cartId")
@ToString(exclude = {"cartItems"})
class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;
    @Column(columnDefinition = "decimal(13,2)")
    private Double total;
    @Column(name = "created_at", columnDefinition = "datetime")
    private Date createdAt;
    @Column(name = "modified_at", columnDefinition = "datetime")
    private Date modifiedAt;
    @OneToMany(mappedBy = "cart" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CartItem> cartItems;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}
