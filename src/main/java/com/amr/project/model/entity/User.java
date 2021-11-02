package com.amr.project.model.entity;

import com.amr.project.model.enums.Gender;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    private boolean activate;
    private String activationCode;
    private String phone;
    private String firstName;
    private String lastName;
    private int age;

    @ManyToMany
    @JoinTable(
            name = "users_addresses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @ToString.Exclude
    private List<Address> address;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<Role> roles;

    private Gender gender;
    private Calendar birthday;

    @OneToMany
    @ToString.Exclude
    private List<Image> images;

    @OneToMany
    @ToString.Exclude
    private List<Coupon> coupons;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<CartItem> cart;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Review> reviews;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Shop> shops;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Discount> discounts;



    public void addAddress(Address address) {
        if (this.address == null) {
            this.address = new ArrayList<>();
        }
        this.address.add(address);
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }

    public void addImages(Image image) {
        if (this.images == null) {
            this.images = new ArrayList<>();
        }
        this.images.add(image);
    }

    public User(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activate;
    }

    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return activate == user.activate && age == user.age && Objects.equals(email, user.email) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(phone, user.phone) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username, password, activate, phone, firstName, lastName, age);
    }
}
