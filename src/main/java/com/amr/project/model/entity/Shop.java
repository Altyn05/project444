package com.amr.project.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country location;


    @OneToMany(mappedBy = "shop",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    @ToString.Exclude
    private List<Review> reviews;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logo_id")
    @ToString.Exclude
    private Image logo;

    private int count;
    private double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "shop")
    @ToString.Exclude
    private List<Discount> discounts;

    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendedToBeDeleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(name, shop.name) && Objects.equals(email, shop.email) && Objects.equals(phone, shop.phone) && Objects.equals(description, shop.description) && Objects.equals(location, shop.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phone, description, location);
    }
}
