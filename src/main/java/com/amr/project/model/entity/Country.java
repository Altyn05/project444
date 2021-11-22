package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(
            mappedBy = "country",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    @ToString.Exclude
    private List<City> cities;

    public Country(String name) {
        this.name = name;
    }


    public void addCity(City city) {
        if (this.cities == null) {
            this.cities = new ArrayList<>();
        }
        this.cities.add(city);
    }

     public List<City> getCities() {
        return cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(name, country.name) && Objects.equals(cities, country.cities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cities);
    }
}
