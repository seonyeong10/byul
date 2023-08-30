package com.byul.web.dto.response;

import com.byul.domain.item.MenuDetail;
import com.byul.web.dto.SyrupType;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @ToString
@NoArgsConstructor
public class MenuDetailResponseDto implements Comparable<MenuDetailResponseDto> {

    @Override
    public int compareTo(MenuDetailResponseDto o) {
        return this.capacity - o.capacity;
    }

    private String sizes;

    private int capacity;

    private int charge;

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

    private int espresso;

    private List<SyrupType> syrup = new ArrayList<>();

    private String milkType;

    public MenuDetailResponseDto(MenuDetail entity) {
        sizes = (entity.getSizes().name().charAt(0) + entity.getSizes().name().substring(1).toLowerCase());
        capacity = entity.getCapacity();
        charge = entity.getCharge();
        calorie = entity.getCalorie();
        carbohydrate = entity.getCarbohydrate();
        sugar = entity.getSugar();
        protein = entity.getProtein();
        fat = entity.getFat();
        saturFat = entity.getSaturFat();
        transFat = entity.getTransFat();
        cholesterol = entity.getCholesterol();
        caffeine = entity.getCaffeine();
        sodium = entity.getSodium();
        espresso = entity.getEspresso();
        syrup = SyrupType.dataToWeb(entity.getSyrup());
        milkType = entity.getMilkType();
    }

}
