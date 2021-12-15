package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.ShopService;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.impl.ShopServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MarketController {

    private ShopService shopServices;
    private final ReadWriteService<Shop, Long> rwShopService;
    private final ShopService shopService;


    public MarketController(ReadWriteService<Shop, Long> rwShopService, ShopService shopService) {
        this.rwShopService = rwShopService;

        this.shopService = shopService;
    }

    @GetMapping("/market/{id}")
    public String marketHome(Model model, @PathVariable(value = "id", required = true) Long id) {
        if (shopService.existsById(Shop.class, id)) {
            model.addAttribute("id", id);
            model.addAttribute("shop", shopService.getShop(id));
            System.out.println("controllerMarket " + shopService.findById(id));
            return "market";
        }
        return "404";
    }

}

