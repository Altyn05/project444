package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.ItemMapper;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.ItemDto;
import com.amr.project.model.dto.ModeratorItemDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.service.abstracts.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminapi")

public class ModeratorRestController {
    private final ModeratorService moderatorService;
    private final ShopMapper shopMapper;
    private final ItemMapper itemMapper;
    @Autowired
    public ModeratorRestController(ModeratorService moderatorService, ShopMapper shopMapper, ItemMapper itemMapper) {
        this.moderatorService = moderatorService;
        this.shopMapper = shopMapper;
        this.itemMapper = itemMapper;
    }

    //////////////////////////////// shops//////////////////////////////
    @GetMapping("/notModeratedShops")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ShopDto> showNotModeratedShops() {
        return  shopMapper.shopListToListShopDto(moderatorService.getNotModeratedShops());
    }
    @PutMapping("/moderatorReject")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String rejectShop(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        moderatorService.rejectShop(id,reason);
        return  "";
    }

    @PutMapping("/moderatorApprove/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String approveShop(@PathVariable("id") Long id) {
        moderatorService.approveShop(id);
        return  "";
    }

    //////////////////////////// items ////////////////////////////////////
    @GetMapping("/notModeratedItems")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public List<ModeratorItemDto> showNotModeratedItems() {
        return  itemMapper.itemListToListModeratorItemDto(moderatorService.getNotModeratedItems());
    }
    @PutMapping("/moderatorRejectItem")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String rejectItem(@RequestParam(value ="shopId") Long id,
                             @RequestParam(value ="rejectReason") String reason) {
        moderatorService.rejectItem(id,reason);
        return  "";
    }

    @PutMapping("/moderatorApproveItem/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String approveItem(@PathVariable("id") Long id) {
        moderatorService.approveItem(id);
        return  "";
    }

}
