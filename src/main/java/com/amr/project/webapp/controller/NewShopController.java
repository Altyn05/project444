package com.amr.project.webapp.controller;

import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.CountryService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class NewShopController {
    private final ShopService shopService;
    private final UserService userService;
    private final CountryService countryService;
    private final CityService cityService;
    private final ShopMapper shopMapper;

    public NewShopController(ShopService shopService, UserService userService,
                             CountryService countryService, CityService cityService,
                             ShopMapper shopMapper) {
        this.shopService = shopService;
        this.userService = userService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.shopMapper = shopMapper;

    }

    @PostMapping("/user/newShop")
    public ModelAndView createNewShop(Principal principal, @ModelAttribute ShopDto shopDto) {
        Shop shop = shopMapper.dtoToModel(shopDto);

        cityService.addNewCity(new City(shopDto.getCityDto().getName()));
        countryService.addNewCountry(new Country(shopDto.getLocation().getName()));

        shop.setLocation(countryService.findByName(shopDto.getLocation().getName()));
        shop.setCity(cityService.findByName(shopDto.getCityDto().getName()));

        shop.setUser(userService.findUserByUsername(principal.getName()));

        shopService.addNewShop(shop);
        return new ModelAndView("redirect:/user");
    }


    @RequestMapping(value = "/updateShop", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView updateShop(Principal principal, @ModelAttribute ShopDto shopDto) {
        System.out.println(shopDto);
        Shop shop = shopService.findById(shopMapper.dtoToModel(shopDto).getId());
        shop.setEmail(shopDto.getEmail());
        shop.setPhone(shopDto.getPhone());
        shop.setName(shopDto.getName());
        shop.setDescription(shopDto.getDescription());

        shop.setLocation(countryService.checkByName(shopDto.getLocation().getName()));
        shop.setCity(cityService.checkByName(shopDto.getCityDto().getName()));

        userService.findUserByUsername(principal.getName()).addShop(shop);

        shopService.update(shop);

        return new ModelAndView("redirect:/user");
    }

    @GetMapping("/getOneNew/{id}")
    public String getOneNew(@PathVariable("id") Long id) {
        Shop shop = shopService.findById(id);
        System.out.println("getOne " + shop.toString());

        String var = String.format(
                "{ \"id\":\"%s\", \"name\":\"%s\", \"email\":\"%s\", \"phone\":\"%s\",\"location\":\"%s\",\"city\":\"%s\",\"description\":\"%s\"}",
                shop.getId(), shop.getName(), shop.getEmail(), shop.getPhone(),
                String.valueOf(shop.getLocation().getName()), String.valueOf(shop.getCity().getName()), shop.getDescription());
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
