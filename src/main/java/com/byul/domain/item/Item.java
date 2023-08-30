package com.byul.domain.item;

import com.byul.domain.AttachFile;
import com.byul.domain.BaseTimeEntity;
import com.byul.domain.Category;
import com.byul.domain.Period;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;

    private String engName;

    private int price;

    @Embedded
    private Period period;

    private String season = "N";

    private String pick = "N";

    @Lob
    private String etc;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AttachFile> attachFiles = new ArrayList<>();

    private int orderCount = 0; // 주문 횟수 -> 인기순

    public Item(Long id, Category category, String name, String engName, int price, Period period, String season, String pick, String etc, int orderCount) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.engName = engName;
        this.price = price;
        this.period = period;
        this.season = season;
        this.pick = pick;
        this.etc = etc;
        this.orderCount = orderCount;
    }

    public Period getPeriod () {
        return period == null ? Period.builder().build() : period;
    }

    public Category getCategory () {
        return category == null ? Category.builder().build() : category;
    }

    //==연관관계 메서드==//

    //==비지니스 메서드==//
    public void update(String name, String engName, int price, Period period, String season, String pick, String etc) {
        this.name = name;
        this.engName = engName;
        this.price = price;
        this.period = period;
        this.season = season;
        this.pick = pick;
        this.etc = etc;
    }

    public void addCategory(Category category) {
        this.category = category;
    }

    public void minusOrderCount() {
        this.orderCount = orderCount - 1;
    }

    public void plusOrderCount() {
        orderCount = orderCount + 1;
    }
}
