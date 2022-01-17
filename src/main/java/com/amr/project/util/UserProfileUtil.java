package com.amr.project.util;

import com.amr.project.converter.ImageMapper;
import com.amr.project.converter.UserPageMapper;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.UserPageDto;
import com.amr.project.model.entity.*;
import com.amr.project.repository.CityRepository;
import com.amr.project.repository.CountryRepository;
import com.amr.project.repository.UserRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserProfileUtil {
    private final ImageMapper imageMapper;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final ReadWriteService<User, Long> rwUser;
    private final ReadWriteService<Image, Long> rwImage;
    private final ReadWriteService<Address, Long> rwAddress;
    private final ReadWriteService<City, Long> rwCity;
    private final ReadWriteService<Country, Long> rwCountry;
    private final UserPageMapper userPageMapper;

    /////// convert from Dto to Model and copy to userDB
    public UserPageDto prepareUser(UserPageDto userPageDto) {
        User userDB = userRepository.findUserByUsername(userPageDto.getUsername()).orElse(null);
        User userFront = userPageMapper.toModel(userPageDto);

        /////////Copy simple fields
        userPageMapper.updateModel(userPageDto, userDB);

        ///////Persist Addresses
        for (Address address : userFront.getAddress()) {
            Country country = address.getCountry();
            if (countryRepository.findFirstCountryByName(country.getName()).orElse(null) == null) {
                rwCountry.persist(country);
            } else address.setCountry(countryRepository.findFirstCountryByName(country.getName()).orElse(null));

            City city = address.getCity();
            if (cityRepository.findFirstCityByName(city.getName()).orElse(null) == null) {
                rwCity.persist(city);
            } else address.setCity(cityRepository.findFirstCityByName(city.getName()).orElse(null));
            if (address.getId() == null) {
                rwAddress.persist(address);
            } else rwAddress.update(address);
        }

        userDB.setAddress(userFront.getAddress());

        /////////Persist Images

/*  Не заработал до конца
        for(Image image: userFront.getImages()) {
            if(image.getId() == null) {
                rwImage.persist(image);
            }
        }
        userDB.setImages(userFront.getImages());
 Не заработал до конца */


        List<Long> indexes = userPageDto.getImages().stream().map(ImageDto::getId).collect(Collectors.toList());
        userDB.getImages().removeIf(image -> !indexes.contains(image.getId()));

        int imageLast = userPageDto.getImages().size() - 1;
        Image img = imageMapper.toModel(userPageDto.getImages().get(imageLast));
        if (img.getId() == null) {
            rwImage.persist(img);
            userDB.getImages().add(img);
        }
        int i = 0;
        for (Image image : userDB.getImages()) {
            image.setIsMain(false);
            if (userPageDto.getImages().get(i++).getIsMain()) image.setIsMain(true);
        }

        rwUser.update(userDB);
        return userPageMapper.toDto(userDB);
    }
}
