package com.byul.web;

import com.byul.service.FoodService;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.response.ItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/api/v1/menus/home/food")
    public List<ItemListResponseDto> findTopN(
            @RequestParam(name = "limit") Integer limit
    ) {
        ItemParam param = ItemParam.builder().limit(limit).build();
        return foodService.findTopN(param);
    }

    @GetMapping("/api/v1/menus/food")
    public Page<ItemListResponseDto> findAll(
            ItemParam param
    ) {
        return foodService.findAll(param);
    }

    @GetMapping("/api/v1/menus/food/{category}")
    public Page<ItemListResponseDto> findAllByCategory(
            @PathVariable(name = "category") String category,
            ItemParam param
    ) {
        return foodService.findAllByCategory(param, category);
    }
}
