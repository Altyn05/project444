package com.amr.project.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dignity; //плюсы
    private String flaw; //минусы
    private String text;
    private Date date;
    private Integer rating;
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] picture;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    @ToString.Exclude
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @ToString.Exclude
    private Item item;

    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;

    public Review(String dignity, String flaw, String text, Date date, Integer rating, Item item, User user, Shop shop) {
        this.dignity = dignity;
        this.flaw = flaw;
        this.text = text;
        this.date = date;
        this.rating = rating;
        this.item = item;
        this.user = user;
        this.shop = shop;
    }

}
