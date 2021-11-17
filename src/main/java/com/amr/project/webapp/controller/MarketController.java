package com.amr.project.webapp.controller;

import com.amr.project.service.abstracts.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MarketController {

    private ShopService shopServices;

    public MarketController(ShopService shopServices) {
        this.shopServices = shopServices;
    }


    @GetMapping("/market/{id}")
    public String marketHome(Model model, @PathVariable(value = "id", required = true) Long id) {
        model.addAttribute("shop", shopServices.getShop(id));
        return "market";
    }

}

