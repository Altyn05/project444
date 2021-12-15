package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Image;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class NewShopController {
    private final ShopService shopService;
    private final UserService userService;
    private final CountryService countryService;
    private final CityService cityService;
    private final ShopMapper shopMapper;
    private final ImageService imageService;

    public NewShopController(ShopService shopService, UserService userService,
                             CountryService countryService, CityService cityService,
                             ShopMapper shopMapper, ImageService imageService) {
        this.shopService = shopService;
        this.userService = userService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.shopMapper = shopMapper;
        this.imageService = imageService;
    }

    @PostMapping("/user/newShop")
    public ModelAndView createNewShop(Principal principal, @RequestBody ShopDto shopDto) {

        Shop shop = shopMapper.dtoToModel(shopDto);
        cityService.addNewCity(new City(shopDto.getCityDto().getName()));
        countryService.addNewCountry(new Country(shopDto.getLocation().getName()));
        imageService.addNewImage(new Image(shopDto.getLogo().getUrl(), shopDto.getLogo().getPicture(), shopDto.getLogo().getIsMain()));
        shop.setLocation(countryService.findByName(shopDto.getLocation().getName()));
        shop.setCity(cityService.findByName(shopDto.getCityDto().getName()));
        shop.setUser(userService.findUserByUsername(principal.getName()));
        shopService.addNewShop(shop);
        return new ModelAndView("redirect:/user");
    }


    @RequestMapping(value = "/updateShop", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView updateShop(Principal principal, @RequestBody ShopDto shopDto) {
        Shop shop = shopService.findById(shopMapper.dtoToModel(shopDto).getId());
        shop.setEmail(shopDto.getEmail());
        shop.setPhone(shopDto.getPhone());
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());
        shop.getLogo().setUrl(shopDto.getLogo().getUrl());
        shop.getLogo().setPicture(shopDto.getLogo().getPicture());
        shop.setLocation(countryService.checkByName(shopDto.getLocation().getName()));
        shop.setCity(cityService.checkByName(shopDto.getCityDto().getName()));
        userService.findUserByUsername(principal.getName()).addShop(shop);
        shopService.update(shop);
        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/getOneNew/{id}")
    public String getOneNew(@PathVariable("id") Long id) {
        Shop shop = shopService.findById(id);
        byte[] encodeBase64 = Base64.encode(shop.getLogo().getPicture());
        String base64Encoded = new String(encodeBase64);
        String pict = "data:image/jpeg;base64," + base64Encoded;
        String var = String.format(
                "{ \"id\":\"%s\", \"name\":\"%s\", \"email\":\"%s\", \"phone\":\"%s\",\"location\":\"%s\",\"city\":\"%s\",\"description\":\"%s\", \"logo\":\"%s\"}",
                shop.getId(), shop.getName(), shop.getEmail(), shop.getPhone(),
                shop.getLocation().getName(), shop.getCity().getName(), shop.getDescription(), pict);
        return var;
    }

    @GetMapping("/deleteUserShop/{id}")
    public ModelAndView deleteShop(@PathVariable("id") Long id) {
        Shop shopDb = shopService.findById(id);
        shopService.deleteUserShop(shopDb);
        System.out.println(
                "work method delete"
        );
        return new ModelAndView("redirect:/user");
    }

}
