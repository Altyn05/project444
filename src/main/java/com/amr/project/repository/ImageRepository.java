package com.amr.project.repository;

import com.amr.project.model.entity.Image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByPicture (Image image);
}
