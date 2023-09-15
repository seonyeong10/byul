package com.byul.domain.item;

import com.byul.domain.Category;
import com.byul.domain.Period;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("D")
public class Drink extends Item {
    private String temp;


    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuDetail> menuDetails = new ArrayList<>();

    @Builder
    public Drink(Long id, Category category, String name, String engName, int price, Period period, String season, String etc, String temp, int orderCount) {
        super(id, category, name, engName, price, period, season, etc, orderCount);
        this.temp = temp;
    }

    //== 연관관계 메서드 ==//
    public void addMenuDetail(MenuDetail detail) {
        menuDetails.add(detail);
        menuDetails.forEach(md -> md.addMenu(this));
    }
}
