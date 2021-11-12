package com.amr.project.model.dto;

import com.amr.project.util.ImgUtil;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageDto {
    private Long id;
    private String url;
    private byte[] picture;
    private Boolean isMain = false;

    public String toBase64() {
        return ImgUtil.toBase64img(url, picture);
    }

    @Override
    public String toString() {
        return "ImageDto{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", isMain=" + isMain +
                '}';
    }
}
