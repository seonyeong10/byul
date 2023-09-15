package com.byul.web;

import com.byul.service.DrinkService;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.request.MenuListRequestDto;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/api/v1/menus/home/drink")
    public List<ItemListResponseDto> findTopN(
            @RequestParam(name = "limit") Integer limit
    ) {
        ItemParam param = ItemParam.builder().limit(limit).build();
        return drinkService.findTopN(param);
    }

    @GetMapping("/api/v1/menus/drink")
    public Page<ItemListResponseDto> findAllCategory(
            //@PathVariable(name = "parentCategoryName") String parentCategoryName,
            MenuListRequestDto requestDto
    ) {
        return drinkService.findAllCategory(requestDto, "drink");
    }

    @GetMapping("/api/v1/menus/drink/{childCategory}/{menuId}")
    public MenuResponseDto findOne(
            @PathVariable(name = "menuId") Long menuId
    ) {
        return drinkService.findOne(menuId);
    }

}
