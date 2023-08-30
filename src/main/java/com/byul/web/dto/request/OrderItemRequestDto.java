package com.byul.web.dto.request;

import com.byul.domain.item.Sizes;
import com.byul.domain.order.Cart;
import com.byul.domain.order.MilkType;
import com.byul.domain.order.OrderItem;
import com.byul.web.dto.SyrupType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderItemRequestDto {

    @Getter
    public static class Params {
        private List<OrderItemRequestDto> orderItems;
    }


    private Long itemId;

    private Sizes sizes;

    private String temp;

    private String pack;

    private int count;

    private int price;

    private int additionalCharge;

    private int espresso;

    private List<SyrupType> syrup;

    private MilkType milkType;

    private String drizzle;

    private String whipping;

    private String topping;

    @Builder
    public OrderItemRequestDto(Long itemId, String sizes, String temp, String pack, int count, int price, int additionalCharge, int espresso, List<SyrupType> syrup, String milkType, String drizzle, String whipping, String topping) {
        this.itemId = itemId;
        if(sizes != null) {
            this.sizes = Sizes.valueOf(sizes.toUpperCase());
        }
        this.temp = temp;
        this.pack = pack;
        this.count = count;
        this.price = price; //사이즈별 기본 금액
        this.additionalCharge = additionalCharge;
        this.espresso = espresso;
        this.syrup = syrup;
        if(milkType != null) {
            this.milkType = MilkType.valueOf(milkType.toUpperCase());
        }
        this.drizzle = drizzle;
        this.whipping = whipping;
        this.topping = topping;
    }

    public OrderItem toEntity() {
        return OrderItem.builder()
                .sizes(sizes)
                .pack(pack)
                .count(count)
                .price(price)
                .temp(temp)
                .additionalPrice(subAdditionalPrice())
                .espresso(espresso)
                .syrup(SyrupType.dataToDB(syrup))
                .milkType(milkType)
                .drizzle(drizzle)
                .whipping(whipping)
                .topping(topping)
                .build();
    }

    public Cart toCart() {
        return Cart.builder()
                .sizes(sizes)
                .pack(pack)
                .count(count)
                .price(price)
                .temp(temp)
                .additionalPrice(subAdditionalPrice())
                .espresso(espresso)
                .syrup(SyrupType.dataToDB(syrup))
                .milkType(milkType)
                .drizzle(drizzle)
                .whipping(whipping)
                .topping(topping)
                .build();
    }

    /**
     * 추가 요금을 계산한다.
     */
    public int subAdditionalPrice() {
        int charge = 0;

        if(espresso > 0) {
            charge += espresso * 600;
        }
        if(syrup != null && syrup.size() > 0) {
            charge += SyrupType.sumAdditionalCharge(syrup);
        }
        if(milkType != null) {
            charge += 600;
        }
        if(drizzle != null && drizzle != "") {
            charge += 600;
        }
        if(whipping != null && whipping != "") {
            charge += 600;
        }
        if(topping != null && topping != "") {
            charge += 600;
        }

        return charge;
    }

}
