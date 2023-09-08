package com.byul.web;

import com.byul.service.MenuService;
import com.byul.web.dto.request.MenuListRequestDto;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/api/v1/menus")
    public Page<ItemListResponseDto> findAll(
            MenuListRequestDto requestDto
    ) {
        return menuService.findAll(requestDto);
    }

    @GetMapping("/api/v1/menus/{parentCategoryName}")
    public Page<ItemListResponseDto> findAllCategory(
            @PathVariable(name = "parentCategoryName") String parentCategoryName,
            MenuListRequestDto requestDto
    ) {
        return menuService.findAllCategory(requestDto, parentCategoryName);
    }

    @GetMapping("/api/v1/menus/{parentCategory}/{childCategory}/{menuId}")
    public MenuResponseDto findOne(
            @PathVariable(name = "menuId") Long menuId
    ) {
        return menuService.findOne(menuId);
    }

}
