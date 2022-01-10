package com.mycompany.shopping.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private OrderDetail orderDetail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ShoppingSession shoppingSession;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    @BatchSize(size = 20)
    @ToString.Exclude
    private Set<Authority> authorities = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return activated == user.activated && Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(address, user.address) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(orderDetail, user.orderDetail) && Objects.equals(shoppingSession, user.shoppingSession) && Objects.equals(langKey, user.langKey) && Objects.equals(imageUrl, user.imageUrl) && Objects.equals(activationKey, user.activationKey) && Objects.equals(resetKey, user.resetKey) && Objects.equals(resetDate, user.resetDate) && Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email, address, phoneNumber, orderDetail, shoppingSession, activated, langKey, imageUrl, activationKey, resetKey, resetDate, authorities);
    }
}
