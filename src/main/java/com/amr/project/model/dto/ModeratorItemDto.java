package com.amr.project.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModeratorItemDto {
    private Long id;
    private String name;
    private String[] categoriesName;
    private List<ImageDto> images;
    private String description;

    private String shopName;
    private ImageDto shopLogo;

    private boolean isModerateAccept;
    private boolean isModerated;
    private String moderatedRejectReason;
}
