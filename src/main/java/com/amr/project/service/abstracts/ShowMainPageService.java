package com.amr.project.service.abstracts;

import com.amr.project.model.dto.ShowMainPageDTO;
import org.springframework.data.domain.Pageable;

public interface ShowMainPageService {
    ShowMainPageDTO findItemsByCategory(Long categoryId, Pageable itemPages, Pageable shopPages);
    ShowMainPageDTO showSearch(String s, Pageable itemPages, Pageable shopPages);
    ShowMainPageDTO show(Pageable itemPages, Pageable shopPages);
}
