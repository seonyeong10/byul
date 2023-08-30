package com.byul.web.dto.response;

import com.byul.domain.Category;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class CategoryResponseDto {

    private Long id;

    private String name;

    private String engName;

    private CategoryResponseDto parent;

    public CategoryResponseDto(Category category) {
        id = category.getId();
        name = category.getName();
        engName = category.getEngName();
        if(category.getParent() != null) {
            parent = new CategoryResponseDto(category.getParent());
        }
    }
}
