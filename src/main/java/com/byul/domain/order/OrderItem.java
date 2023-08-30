package com.byul.domain.order;

import com.byul.domain.converter.MilkTypeConverter;
import com.byul.domain.item.Item;
import com.byul.domain.item.Sizes;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int price;

    private int additionalPrice;

    private int orderPrice; //주문가격 = 수량*(기본금액+추가금액)

    private int count; //주문 수량

    @Enumerated(EnumType.STRING)
    private Sizes sizes;

    private String temp;

    private String pack;

    private int espresso;

    private String syrup;

    @Convert(converter = MilkTypeConverter.class)
    private MilkType milkType;

    private String drizzle;

    private String whipping;

    private String topping;

    @Builder
    public OrderItem(Long id, Item item, Order order, int price, int additionalPrice, int count, Sizes sizes, String temp, String pack, int espresso, String syrup, MilkType milkType, String drizzle, String whipping, String topping) {
        this.id = id;
        this.item = item;
        this.order = order;
        this.price = price;
        this.additionalPrice = additionalPrice;
        this.count = count;
        this.sizes = sizes;
        this.temp = temp;
        this.pack = pack;
        this.espresso = espresso;
        this.syrup = syrup;
        this.milkType = milkType;
        this.drizzle = drizzle;
        this.whipping = whipping;
        this.topping = topping;
    }

    //==비지니스 로직==//
    public void cancel() {
        getItem().minusOrderCount();
    }

    //==연관관계 메서드==//
    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
        this.orderPrice = getCount() * (getPrice() + getAdditionalPrice());
        item.plusOrderCount();
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", item=" + item.getId() +
                ", additionalPrice=" + additionalPrice +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                ", sizes=" + sizes +
                ", pack='" + pack + '\'' +
                ", espresso=" + espresso +
                ", syrup='" + syrup + '\'' +
                ", milkType=" + milkType +
                ", drizzle='" + drizzle + '\'' +
                ", whipping='" + whipping + '\'' +
                ", topping='" + topping + '\'' +
                '}';
    }
}
