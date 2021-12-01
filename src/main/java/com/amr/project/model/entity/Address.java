package com.amr.project.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cityIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    @ToString.Exclude
    private City city;

    private String street;
    private String house;

    @ManyToMany
    @JoinTable(
            name = "users_addresses",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private Set<User> users;

    public Address(String cityIndex, Country country, City city, String street, String house) {
        this.cityIndex = cityIndex;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
    }

    public Address(String cityIndex, String street, String house) {
        this.cityIndex = cityIndex;
        this.street = street;
        this.house = house;
    }

    public Address(Country country, City city) {
        this.country = country;
        this.city = city;
    }

    public Address() {
        this.country = new Country();
        this.city = new City(); //починить - убрать null
    }

    public Country getCountry() {
        return country;
    }

    public City getCity() {
        return city;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;

        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return 1395783287;
    }

    public void addUser(User user) {
        if (this.users == null) {
            Set<User> users = new HashSet<>();
        }
        this.users.add(user);
    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Address address = (Address) o;
//        return Objects.equals(cityIndex, address.cityIndex) && Objects.equals(country, address.country) && Objects.equals(city, address.city) && Objects.equals(street, address.street) && Objects.equals(house, address.house);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(cityIndex, country, city, street, house);
//    }
}
