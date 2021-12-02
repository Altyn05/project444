package com.amr.project.model.dto;

import com.amr.project.util.ImgUtil;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String text;
    private Date date;
    private byte[] picture;
    private String url;
    private int rating;
    private String dignity; //плюсы
    private String flaw; //минусы
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private List<ImageDto> userImages;
    private Long itemId;
    private String itemName;
    private Long shopId;
    private String shopName;
    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;

    public String toBase64() {
        if (picture != null) {
            return ImgUtil.toBase64img(url, picture);
        }
        return null;
    }

}
