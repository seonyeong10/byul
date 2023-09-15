package com.byul.web.dto.request;

import com.byul.domain.Period;
import com.byul.domain.item.Drink;
import com.byul.domain.item.MenuDetail;
import com.byul.domain.item.Sizes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MenuRequestDto {
    private Long categoryId;

    private String name;

    private String engName;

    private int price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String season = "N";

    private String etc;

    private Sizes sizes;

    //MenuDetail
    private String temp;

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

    @Builder
    public MenuRequestDto(Long categoryId, String name, String engName, int price, LocalDateTime startDate, LocalDateTime endDate, String season, String etc, Sizes sizes, String temp, Long calorie, int carbohydrate, int sugar, int protein, int fat, int saturFat, int transFat, int cholesterol, int caffeine, int sodium) {
        this.categoryId = categoryId;
        this.name = name;
        this.engName = engName;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.season = season;
        this.etc = etc;
        this.sizes = sizes;
        this.temp = temp;
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
    }

    /**
     * Menu 엔티티를 생성한다.
     */
    public Drink toMenu () {
        return Drink.builder()
                .period(Period.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .build())
                .name(name)
                .engName(engName)
                .price(price)
                .etc(etc)
                .season(season)
                .temp(temp)
                .build();
    }

    /**
     * MenuDetail 엔티티를 생성한다.
     */
    public MenuDetail toMenuDetail () {
        return MenuDetail.builder()
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .sugar(sugar)
                .protein(protein)
                .fat(fat)
                .saturFat(saturFat)
                .transFat(transFat)
                .cholesterol(cholesterol)
                .caffeine(caffeine)
                .sodium(sodium)
                .sizes(sizes)
                .build();
    }
}
