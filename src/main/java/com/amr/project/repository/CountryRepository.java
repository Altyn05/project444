package com.amr.project.repository;

import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findCountryByName (String name);
}
