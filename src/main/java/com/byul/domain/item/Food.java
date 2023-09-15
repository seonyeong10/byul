package com.byul.domain.item;

import com.byul.domain.Category;
import com.byul.domain.Period;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("F")
public class Food extends Item {

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuDetail> menuDetails = new ArrayList<>();

    @Builder
    public Food(Long id, Category category, String name, String engName, int price, Period period, String season, String etc, int orderCount) {
        super(id, category, name, engName, price, period, season, etc, orderCount);
    }
}
