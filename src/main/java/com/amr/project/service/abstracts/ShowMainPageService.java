package com.amr.project.service.abstracts;


import com.amr.project.model.dto.ShowMainPageDTO;


public interface ShowMainPageService {

    ShowMainPageDTO findItemsByCategory(Long categoryId);
    ShowMainPageDTO showSearch(String s);
    ShowMainPageDTO show();


}
