package com.amr.project.repository;

import com.amr.project.model.entity.Favorite;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
}
