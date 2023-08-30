package com.byul.web.dto.response;

import com.byul.domain.item.Sizes;
import com.byul.domain.order.OrderItem;
import com.byul.web.dto.SyrupType;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class OrderItemResDto {

    private Long id;

    private ItemResponseDto item; //id, 사진, 이름, 가격

    private int price;

    private int additionalPrice;

    private int count;

    private int orderPrice;

    private Sizes sizes;

    private String temp;

    private String pack;

    private int espresso;

    private List<SyrupType> syrup;

    private String[] milkType;

    private String dirzzle;

    private String whipping;

    private String topping;

    public OrderItemResDto(OrderItem entity) {
        id = entity.getId();
        item = new ItemResponseDto(entity.getItem());
        price = entity.getPrice();
        additionalPrice = entity.getAdditionalPrice();
        count = entity.getCount();
        orderPrice = entity.getOrderPrice();
        sizes = entity.getSizes();
        temp = entity.getTemp();
        pack = entity.getPack();
        espresso = entity.getEspresso();
        syrup = SyrupType.dataToWeb(entity.getSyrup());
        if(entity.getMilkType() != null) {
            milkType = new String[]{entity.getMilkType().toString(), entity.getMilkType().getValue()};
        }
        dirzzle = entity.getDrizzle();
        whipping = entity.getWhipping();
        topping = entity.getTopping();
    }

}
