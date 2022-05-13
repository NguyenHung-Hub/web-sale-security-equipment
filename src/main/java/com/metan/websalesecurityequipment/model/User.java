package com.metan.websalesecurityequipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
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
@ToString(exclude = {"cart", "orders"})
class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @NotEmpty(message = "Nhập đầy đủ họ tên!")
    private String firstName;
    @NotEmpty(message = "Nhập đầy đủ họ tên!")
    private String lastName;
    @Column(unique = true)
    @NotBlank(message = "Nhập email của bạn!")
    @Email(message = "Email của bạn nhập không đúng")
    private String email;
    @NotBlank(message = "Nhập số điện thoại của bạn!")
    @Pattern(regexp = "^$|(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại nhập không đúng")
    @Column(name = "phone_number")
    private String phoneNumber;
    private String role;
    private String profile;
    @Column(name = "registered_at", columnDefinition = "datetime")
    private Date registeredAt;
    @Column(nullable = false)
//    @NotNull(message = "Nhập password!")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Tối thiểu 8 ký tự, ít nhất một chữ cái và một số")
    private String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
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
