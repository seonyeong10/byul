package com.byul.web.dto.response;

import com.byul.domain.item.Item;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ItemListResponseDto {

    private Long id;

    private CategoryResponseDto category;

    private String name;

    private Long attachFileId;

    public ItemListResponseDto(Item entity) {
        id = entity.getId();
        category = new CategoryResponseDto(entity.getCategory());
        name = entity.getName();
        attachFileId = entity.getAttachFiles().size() > 0 ? entity.getAttachFiles().get(0).getId() : 0L;
    }

}
