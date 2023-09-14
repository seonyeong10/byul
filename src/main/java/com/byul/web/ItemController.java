package com.byul.web;

import com.byul.service.ItemService;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.response.ItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/api/v1/items/latest")
    public List<ItemListResponseDto> findAllLatest() {
        ItemParam param = ItemParam.builder().isLatest(true).build();
        return itemService.findAllComplex(param);
    }

    @GetMapping("/api/v1/items/advised")
    public List<ItemListResponseDto> findAllAdvised() {
        ItemParam param = ItemParam.builder().isAdvised(true).build();
        return itemService.findAllComplex(param);
    }
}
