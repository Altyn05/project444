package com.amr.project.webapp.controller;

import com.amr.project.converter.UserMapper;
import com.amr.project.converter.AddressMapper;
import com.amr.project.model.dto.UserDto;
import com.amr.project.model.dto.AddressDto;
import com.amr.project.model.entity.*;
import com.amr.project.service.abstracts.*;
import com.amr.project.webapp.security.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Import(SecurityConfig.class)
@Controller("")
public class UserController {

    private final UserService userService;
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    private final AddressService addressService;
    private final UserMapper mapper;
    private final AddressMapper addressMapper;


    public UserController(UserService userService,
                          CountryService countryService, RegionService regionService, CityService cityService, AddressService addressService,
                          UserMapper mapper, AddressMapper addressMapper) {

        this.userService = userService;
        this.countryService = countryService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.addressService = addressService;
        this.mapper = mapper;

        this.addressMapper = addressMapper;
    }

    @PostMapping("/signup")
    public ModelAndView createNewUser(@ModelAttribute UserDto userDto) {
        User user = mapper.toModel(userDto);
        if (userService.createNewUser(user)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("signup");
            modelAndView.addObject("date", new Date());
            modelAndView.addObject("addressDto", new AddressDto());

            return modelAndView;
        } else {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("index");//другая страница или сообщение, что такой пользователь существует
            return modelAndView;
        }
    }

    @PostMapping("/update")
    public String updateDataUser(@ModelAttribute UserDto userDto,
                                 @ModelAttribute("addressDto") AddressDto addressDto,
                                @ModelAttribute String date) {
        User user = mapper.toModel(userDto);
        Address address = addressMapper.toModel(addressDto);

        countryService.addNewCountry(new Country(addressDto.getCountry()));
        regionService.persist(new Region(addressDto.getRegion(), countryService.findByName(addressDto.getCountry())));
        cityService.addNewCity(new City(addressDto.getCity(),
                regionService.findByName(addressDto.getRegion())));

        address.setCity(cityService.findByName(addressDto.getCity()));
        address.setCountry(countryService.findByName(addressDto.getCountry()));

        addressService.addNewAddress(address);
        address.setId(addressService.getByAddress(address).getId());
        user.addAddress(addressService.findById(address.getId()));
        userService.updateUser(user);
        return "redirect:/";
    }

}

