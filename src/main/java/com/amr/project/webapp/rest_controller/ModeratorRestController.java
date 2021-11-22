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
    public String rejectShop(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        shopService.rejectShop(id,reason);
        return  "";
    }

    @PutMapping("/moderatorApprove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String approveShop(@PathVariable("id") Long id) {
        shopService.approveShop(id);
        return  "";
    }

    //////////////////////////// items ////////////////////////////////////
    @GetMapping("/notModeratedItems")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ModeratorItemDto> showNotModeratedItems() {
        return  itemMapper.itemListToListModeratorItemDto(itemService.getNotModeratedItems());
    }
    @PutMapping("/moderatorRejectItem")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String rejectItem(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        itemService.rejectItem(id,reason);
        return  "";
    }

    @PutMapping("/moderatorApproveItem/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String approveItem(@PathVariable("id") Long id) {
        itemService.approveItem(id);
        return  "";
    }

    //////////////////////////// Reviews ////////////////////////////////////
    @GetMapping("/notModeratedReviews")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ReviewDto> showNotModeratedReviews() {
        return  reviewMapper.reviewListToListReviewDto (reviewService.getNotModeratedReviews());
    }
    @PutMapping("/moderatorRejectReview")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String rejectReview(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        reviewService.rejectReview(id,reason);
        return  "";
    }

    @PutMapping("/moderatorApproveReview/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String approveReview(@PathVariable("id") Long id) {
        reviewService.approveReview(id);
        return  "";
    }


}
