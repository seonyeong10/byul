package com.byul.domain.item;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Table(name = "menu_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_detail_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Sizes sizes;

    private int capacity;

    private int charge; //추가금액

    private Long calorie = 0L;

    private int carbohydrate;

    private int sugar;

    private int protein;

    private int fat;

    private int saturFat;

    private int transFat;

    private int cholesterol;

    private int caffeine;

    private int sodium;

    /* 옵션 */
    private int espresso;

    private String syrup;

    private String milkType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Menu menu;

    @Builder
    public MenuDetail(Long id, Sizes sizes, int capacity, int charge, Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium, int espresso, String syrup, String milkType) {
        this.id = id;
        this.sizes = sizes;
        this.capacity = capacity;
        this.charge = charge;
        this.calorie = calorie;
        this.carbohydrate = carbohydrate;
        this.sugar = sugar;
        this.protein = protein;
        this.fat = fat;
        this.saturFat = saturFat;
        this.transFat = transFat;
        this.cholesterol = cholesterol;
        this.caffeine = caffeine;
        this.sodium = sodium;
        this.espresso = espresso;
        this.syrup = syrup;
        this.milkType = milkType;
    }

    //==연관관계 메서드==//
    /**
     * 메뉴를 저장한다.
     */
    public void addMenu (Menu menu) {
        this.menu = menu;
        menu.getMenuDetail().add(this);
    }


    //==비지니스 메서드==//
}
