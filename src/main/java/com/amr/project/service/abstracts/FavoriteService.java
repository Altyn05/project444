package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Favorite;
import org.springframework.stereotype.Service;

@Service
public interface FavoriteService {
    public Favorite getFavorite(Long id);

}
