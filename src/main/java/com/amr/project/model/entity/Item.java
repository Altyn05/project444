package com.amr.project.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private boolean favorite;


    @ManyToOne
    @JoinTable(
            name = "users_items",
            joinColumns = @JoinColumn(name = "items_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User user;

    @ManyToMany
    @JoinTable(
            name = "categories_items",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @ManyToMany(mappedBy = "items", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    private Integer count;
    @Min(value = 1, message = "Must be greater than 0")
    @Max(value = 10, message = "Must be less than 11")
    private Integer  rating;

    @Column(columnDefinition = "text")
    private String description;
    private Integer discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private boolean isModerated;
    private boolean isModerateAccept;
    private String moderatedRejectReason;
    private boolean isPretendedToBeDeleted;


    public void addCategory(Category category) {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        categories.add(category);
    }

    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

}
