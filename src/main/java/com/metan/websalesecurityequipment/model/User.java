package com.metan.websalesecurityequipment.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number",unique = true)
    private String phoneNumber;
    private String role;
    private String profile;
    @Column(name = "registered_at", columnDefinition = "datetime")
    private Date registeredAt;
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;
    @Column(name = "verification_code",length = 64)
    private String verificationCode;
    private boolean enable;
    @Enumerated(EnumType.STRING)
    @Column(name = "auth_provider", length = 15)
    private AuthenticationProvider authProvider;
    @Embedded
    private Address address;

    @Transient
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}
