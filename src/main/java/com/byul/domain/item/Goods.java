package com.byul.domain.item;

import com.byul.domain.Category;
import com.byul.domain.Period;
import com.byul.exception.NotEnoughStockException;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("G")
public class Goods extends Item {

    private int stock;

    @Builder
    public Goods(Long id, Category category, String name, String engName, int price, Period period, String season, String pick, String etc, int stock, int orderCount) {
        super(id, category, name, engName, price, period, season, pick, etc, orderCount);
        this.stock = stock;
    }

    //==비지니스 메서드==//
    /**
     * 수량이 증가한다.
     */
    public void addStock (int count) {
        this.stock += count;
    }

    /**
     * 수량이 감소한다.
     */
    public void removeStock (int count) {
        int restStock = stock - count;
        if(restStock < 0) {
            throw new NotEnoughStockException("최소 수량은 0 이상이어야 합니다.");
        }
        this.stock = restStock;
    }
}
