package com.amr.project.service.abstracts;


import com.amr.project.model.dto.ShowMainPageDTO;


public interface ShowMainPageService {

    ShowMainPageDTO findItemsByCategory(
            Long categoryId
            , int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    );
    ShowMainPageDTO showSearch(
            String s
            , int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    );
    ShowMainPageDTO show(
            int itemPage
            , int itemsPerPage
            , int shopPage
            , int shopsPerPage
    );
}
