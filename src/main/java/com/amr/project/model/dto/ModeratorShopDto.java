package com.amr.project.model.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModeratorShopDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String description;
    private CountryDto location;
    private ImageDto logo;
    private double rating;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendentToBeDeleted;

}
