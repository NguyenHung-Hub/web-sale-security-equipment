package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public @Data
@EqualsAndHashCode(of = "userId")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private byte[] avatar;
    private boolean admin;
    private String profile;
    @Column(name = "registered_at", columnDefinition = "datetime")
    private Date registeredAt;
    private String username;
    private String password;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
}
