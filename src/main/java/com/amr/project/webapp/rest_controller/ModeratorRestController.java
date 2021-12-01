package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ModeratorItemDto;
import com.amr.project.model.dto.ModeratorShopDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.abstracts.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi")

public class ModeratorRestController {

    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    private final ReviewMapper reviewMapper;
    private final ShopService shopService;
    private final ItemService itemService;
    private final ReviewService reviewService;
    @Autowired
    public ModeratorRestController(ShopMapper shopMapper, ItemMapper itemMapper, ReviewMapper reviewMapper, ShopService shopService, ItemService itemService, ReviewService reviewService) {

        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
        this.reviewMapper = reviewMapper;
        this.shopService = shopService;
        this.itemService = itemService;
        this.reviewService = reviewService;
    }

    //////////////////////////////// shops//////////////////////////////
    @GetMapping("/notModeratedShops")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ModeratorShopDto> showNotModeratedShops() {
        return  shopMapper.shopListToListModeratorShopDto(shopService.getNotModeratedShops());
    }
    @PutMapping("/moderatorReject")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ModeratorShopDto rejectShop(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {

        return  shopMapper.shoptoModeratorShopDto(shopService.rejectShop(id,reason));
    }

    @PutMapping("/moderatorApprove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ModeratorShopDto approveShop(@PathVariable("id") Long id) {

        return  shopMapper.shoptoModeratorShopDto(shopService.approveShop(id));
    }

    //////////////////////////// items ////////////////////////////////////
    @GetMapping("/notModeratedItems")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ModeratorItemDto> showNotModeratedItems() {
        return  itemMapper.itemListToListModeratorItemDto(itemService.getNotModeratedItems());
    }
    @PutMapping("/moderatorRejectItem")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ModeratorItemDto rejectItem(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {

        return  itemMapper.itemToModeratorItemDto(itemService.rejectItem(id,reason));
    }

    @PutMapping("/moderatorApproveItem/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ModeratorItemDto approveItem(@PathVariable("id") Long id) {

        return  itemMapper.itemToModeratorItemDto(itemService.approveItem(id));
    }

    //////////////////////////// Reviews ////////////////////////////////////
    @GetMapping("/notModeratedReviews")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ReviewDto> showNotModeratedReviews() {
        return  reviewMapper.reviewListToListReviewDto (reviewService.getNotModeratedReviews());
    }
    @PutMapping("/moderatorRejectReview")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ReviewDto rejectReview(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        return  reviewMapper.reviewToReviewDto(reviewService.rejectReview(id,reason));
    }

    @PutMapping("/moderatorApproveReview/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ReviewDto approveReview(@PathVariable("id") Long id) {
        return  reviewMapper.reviewToReviewDto(reviewService.approveReview(id));
    }


}
