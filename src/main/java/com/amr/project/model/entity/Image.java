package com.amr.project.model.entity;

import com.amr.project.util.ImgUtil;
import com.amr.project.util.ImgUtilFromUrl;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Column(columnDefinition = "mediumblob")
    private byte[] picture;

    private Boolean isMain = false;

    public Image(String url) {
        this.url = url;
        this.picture = ImgUtil.toByteArr(url);
    }

    public Image(String url, byte[] picture, Boolean isMain) {
        this.url = url;
        this.picture = picture;
        this.isMain = isMain;
    }

    public Image(String url, Boolean isMain) {
        this(url);
        this.isMain = isMain;
    }

    public Image() {

    }

    public Image ImageFromURL (String url) {
        this.url = url;
        this.picture = ImgUtilFromUrl.toByteArray(url);
        return this;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", isMain=" + isMain +
                '}';
    }
}
