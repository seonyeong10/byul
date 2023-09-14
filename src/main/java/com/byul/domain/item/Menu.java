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
@DiscriminatorValue("M")
public class Menu extends Item {
    private String temp;


    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuDetail> menuDetail = new ArrayList<>();

    @Builder
    public Menu(Long id, Category category, String name, String engName, int price, Period period, String season, String etc, String temp, int orderCount) {
        super(id, category, name, engName, price, period, season, etc, orderCount);
        this.temp = temp;
    }
}
