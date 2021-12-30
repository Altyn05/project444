package com.amr.project.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "regions",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "country_id"})}
)
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @ToString.Exclude
    private Country country;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<City> cities;

    public Region(String name){
        this.name = name;
    }

    public Region(String name, Country country){
        this.name = name;
        this.country = country;
    }
//    public void addCity(City city) {
//        if (this.cities == null) {
//            this.cities = new ArrayList<>();
//        }
//        this.cities.add(city);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Region)) return false;

        Region region = (Region) o;

        if (!name.equals(region.name)) return false;
        return country.equals(region.country);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }
}
