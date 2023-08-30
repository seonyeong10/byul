package com.byul.web.dto.response;

import com.byul.domain.order.Cart;
import com.byul.domain.order.MilkType;
import com.byul.web.dto.SyrupType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartResponseDto {

    private Long id;

    private ItemResponseDto item;

    private int price;

    private int additionalPrice;

    private int orderPrice;

    private int count;

    private String temp;

    private String sizes;

    private String pack;

    private int espresso;

    private List<SyrupType> syrup;

    private String[] milkType;

    private String whipping;

    private String topping;

    public CartResponseDto(Cart entity) {
        id = entity.getId();
        item = new ItemResponseDto(entity.getItem());
        additionalPrice = entity.getAdditionalPrice();
        orderPrice = entity.getOrderPrice();
        count = entity.getCount();
        temp = entity.getTemp();
        sizes = entity.getSizes().name();
        price = entity.getPrice();
        pack = entity.getPack();
        espresso = entity.getEspresso();
        syrup = SyrupType.dataToWeb(entity.getSyrup());
//        milkType = entity.getMilkType();
        if(entity.getMilkType() != null) {
            milkType = new String[]{entity.getMilkType().toString(), entity.getMilkType().getValue()};
        }
        whipping = entity.getWhipping();
        topping = entity.getTopping();
    }

    @Override
    public String toString() {
        return "CartItemResponseDto{" +
                "item=" + item +
                ", additionalPrice=" + additionalPrice +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                ", temp='" + temp + '\'' +
                ", sizes='" + sizes + '\'' +
                ", pack='" + pack + '\'' +
                ", espresso=" + espresso +
                ", syrup=" + syrup +
                ", milkType='" + milkType + '\'' +
                ", whipping='" + whipping + '\'' +
                ", topping='" + topping + '\'' +
                '}';
    }
}
