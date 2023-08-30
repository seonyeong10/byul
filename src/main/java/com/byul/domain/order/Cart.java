package com.byul.domain.order;

import com.byul.domain.BaseTimeEntity;
import com.byul.domain.Member;
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
public class Cart extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int price; //기본금액

    private int additionalPrice; //추가금액

    private int orderPrice; //주문금액 = 수량*(기본금액+추가금액)

    private int count; //주문 수량

    @Enumerated(EnumType.STRING)
    private Sizes sizes; //사이즈

    private String temp; //온도

    private String pack; //포장

    //옵션
    private int espresso;

    private String syrup;

    @Convert(converter = MilkTypeConverter.class)
    private MilkType milkType;

    private String drizzle;

    private String whipping;

    private String topping;

    //==생성 메서드==//
    @Builder
    public Cart(Long id, int price, int additionalPrice, int count, Sizes sizes, String temp, String pack, int espresso, String syrup, MilkType milkType, String drizzle, String whipping, String topping) {
        this.id = id;
        this.price = price;
        this.additionalPrice = additionalPrice;
        this.orderPrice = count * (price + additionalPrice);
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


    //==비지니스 메서드==//

    //==연관관계 메서드==//
    public void addMember(Member member) {
        this.member = member;
    }

    public void addItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", member=" + member.getId() +
                ", item=" + item.getName() +
                ", price=" + price +
                ", additionalPrice=" + additionalPrice +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                ", sizes=" + sizes +
                ", temp='" + temp + '\'' +
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
