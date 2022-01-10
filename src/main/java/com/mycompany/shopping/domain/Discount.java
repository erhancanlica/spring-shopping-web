package com.mycompany.shopping.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "discount")
public class Discount extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_percent")
    private Float discountPercent;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id) && Objects.equals(name, discount.name) && Objects.equals(description, discount.description) && Objects.equals(discountPercent, discount.discountPercent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, discountPercent);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "description = " + description + ", " +
                "discountPercent = " + discountPercent + ")";
    }
}
