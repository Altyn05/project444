package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper2;
import com.amr.project.converter.ShopMapper2;
import com.amr.project.model.dto.ItemDto2;
import com.amr.project.model.dto.ShopDto2;
import com.amr.project.model.entity.Favorite;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.repository.FavoriteRepository;
import com.amr.project.service.abstracts.ReadWriteService;
import com.amr.project.service.impl.FavoriteImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorites")
public class FavoriteRestController {
    private ReadWriteService<Favorite, Long> favoriteService;
    private FavoriteRepository favoriteRepository;
    private final ReadWriteService<User, Long> rwUser;
    private FavoriteImpl favoriteImpl;
    private ShopMapper2 shopMapper2;
    private ItemMapper2 itemMapper2;

    public FavoriteRestController(ReadWriteService<Favorite, Long> favoriteService, FavoriteRepository favoriteRepository, ReadWriteService<User, Long> rwUser, FavoriteImpl favoriteImpl, ShopMapper2 shopMapper2, ItemMapper2 itemMapper2) {
        this.favoriteService = favoriteService;
        this.favoriteRepository = favoriteRepository;
        this.rwUser = rwUser;
        this.favoriteImpl = favoriteImpl;
        this.shopMapper2 = shopMapper2;
        this.itemMapper2 = itemMapper2;
    }

    @PostMapping
    public Favorite addFavorite(@RequestBody Favorite favorite) {
        favoriteService.update(favorite);
        return favorite;
    }

    @GetMapping("/getIdItem")
    public ResponseEntity<Map<Long, Long>> getFavoriteIdForItem() {
        List<Favorite> favoriteList = getFavoriteList();
        Map<Long, Long> favoriteId = new HashMap<>();
        for (Favorite a : favoriteList) {
            List<Item> item = a.getItems();
            for (Item b : item) {
                favoriteId.put(b.getId(), a.getId());
            }
        }
        return new ResponseEntity<>(favoriteId, HttpStatus.OK);
    }

    @GetMapping("/getIdShop")
    public ResponseEntity<Map<Long, Long>> getFavoriteIdForShop() {
        List<Favorite> favoriteList = getFavoriteList();
        Map<Long, Long> favoriteId = new HashMap<>();
        for (Favorite a : favoriteList) {
            List<Shop> item = a.getShops();
            for (Shop b : item) {
                favoriteId.put(b.getId(), a.getId());
            }
        }
        return new ResponseEntity<>(favoriteId, HttpStatus.OK);
    }

    public List<Favorite> getFavoriteList() {
        User user = rwUser.getByKey(User.class,
                ((User) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal()).getId()).orElse(null);
        Long id = user.getId();
        List<Favorite> favorite = favoriteImpl.getFavoriteList(id);
        return favorite;
    }

    @GetMapping("/getShopByFavorite")
    public ResponseEntity<List<List<ShopDto2>>> getShopByFavorite() {
        List<Favorite> favoriteList = getFavoriteList();
        List<List<ShopDto2>> shops = new ArrayList<>();
        for (Favorite a : favoriteList) {
            if (!a.getShops().isEmpty()) {
                shops.add(shopMapper2.shopListToListShopDto(a.getShops()));
            }
        }
        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/getItemsByFavorite")
    public ResponseEntity<List<List<ItemDto2>>> getItemByFavorite() {
        Favorite favorite = null;
        List<Favorite> favoriteList = getFavoriteList();
        List<List<ItemDto2>> items = new ArrayList<>();
        for (Favorite a : favoriteList) {
            if (a != null) {
                items.add(itemMapper2.itemListToListItemDto(a.getItems()));
            }
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFavoriteItem(@PathVariable("id") Long id) {
        favoriteRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/getItemsByFavorites")
    public ResponseEntity<List<List<ItemDto2>>> getItemByFavorites() {
        List<Favorite> favorite = (List<Favorite>) favoriteRepository.findAll();
        List<List<ItemDto2>> items = new ArrayList<>();
        for (Favorite a : favorite) {
            if (a.getItems() != null) {
                items.add(itemMapper2.itemListToListItemDto(a.getItems()));
            }
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

}
