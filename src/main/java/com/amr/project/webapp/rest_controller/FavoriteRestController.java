package com.amr.project.webapp.rest_controller;

import com.amr.project.model.entity.Favorite;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteRestController {
    private ReadWriteService<Favorite, Long> favoriteService;

    public FavoriteRestController(ReadWriteService<Favorite, Long> favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {

        favoriteService.update(favorite);
        return favorite;
    }

//    @GetMapping("/get")
//    public List<Favorite> getFavorite(List<Favorite> favority) {
//        favoriteService.getAll(Favorite.class);
//        return favority;
//    }

}
