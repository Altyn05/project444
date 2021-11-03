package com.amr.project.util;

import com.amr.project.model.entity.*;
import com.amr.project.repository.CityRepository;
import com.amr.project.repository.CountryRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserProfileUtil {
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ReadWriteService<Image, Long> rwImage;
    private final ReadWriteService<Address, Long> rwAddress;
    private final ReadWriteService<City, Long> rwCity;
    private final ReadWriteService<Country, Long> rwCountry;


    /////// add userId and persist new Image and address if any
    public User prepareUser(User user) {
        User userFromDB = userRepository.findUserByUsername(user.getUsername()).orElse(null);
        user.setId(userFromDB.getId());

        for(Image image: user.getImages()) {
            if(image.getId() == null) {
                rwImage.persist(image);
            }
        }

        for(Address address: user.getAddress()) {
            if(address.getId() == null) {
                if(countryRepository.findCountryByName(address.getCountry().getName()).orElse(null) == null) {
                    rwCountry.persist(address.getCountry());
                } else address.setCountry(countryRepository.findCountryByName(address.getCountry().getName()).orElse(null));

                if(cityRepository.findCityByName(address.getCity().getName()).orElse(null) == null) {
                    rwCity.persist(address.getCity());
                }else address.setCity(cityRepository.findCityByName(address.getCity().getName()).orElse(null));

                rwAddress.persist(address);
            }
        }
        return user;
    }
}
