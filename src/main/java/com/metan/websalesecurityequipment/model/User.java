package com.metan.websalesecurityequipment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public @Data
@EqualsAndHashCode(of = "userId")
class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private byte[] avatar;
    private String role;
    private String profile;
    @Column(name = "registered_at", columnDefinition = "datetime")
    private Date registeredAt;
    private String password;
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @Column(name = "verification_code",length = 64)
    private String verificationCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", length = 15)
    private AuthenticationProvider authProvider;

    @Transient
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
