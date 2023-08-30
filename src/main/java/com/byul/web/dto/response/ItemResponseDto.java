package com.byul.web.dto.response;

import com.byul.domain.item.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private Long id;

    private String name;

    private String engName;

    private int price;

    private AttachFileResponseDto attachFile;

    public ItemResponseDto(Item entity) {
        id = entity.getId();
        name = entity.getName();
        engName = entity.getEngName();
        price = entity.getPrice();
        if(entity.getAttachFiles().size() > 0) {
            attachFile = new AttachFileResponseDto(entity.getAttachFiles().get(0));
        }
    }
}
