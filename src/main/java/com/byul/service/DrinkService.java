package com.byul.service;

import com.byul.domain.Category;
import com.byul.domain.repository.CategoryRepository;
import com.byul.domain.repository.DrinkRepository;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.request.MenuListRequestDto;
import com.byul.web.dto.request.Sorting;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository drinkRepository;

    private final CategoryRepository categoryRepository;

    /**
     * GET /api/v1/menus

    public Page<ItemListResponseDto> findAll (MenuListRequestDto requestDto) {
        Sorting sorting = Sorting.valueOf(requestDto.getSort().toUpperCase());
        PageRequest page = PageRequest.of(requestDto.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return drinkRepository.findAllByCategory(null, page)
                .map(ItemListResponseDto::new);
    }
     */

    /**
     * GET /api/v1/menus/{parentCategoryName}
     * 음료/푸드/상품을 카테고리별로 전체 조회한다.
     */
    public Page<ItemListResponseDto> findAllCategory (MenuListRequestDto requestDto, String parentCategoryName) {
        Category parent = categoryRepository.findParentByName(parentCategoryName)
                .orElseThrow(() -> new NoSuchElementException(String.format("카테고리를 찾을 수 없습니다. eng_name = [%s]", parentCategoryName)));
        Sorting sorting = Sorting.valueOf(requestDto.getSort().toUpperCase());

        PageRequest page = PageRequest.of(requestDto.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return  drinkRepository.findAllByCategory(parent, page)
                .map(ItemListResponseDto::new);
    }

    /**
     * /api/v1/menus/{parentCategory}/{childCategory}/{menuId}
     * 음료/푸드/상품을 조회한다.
     */
    public MenuResponseDto findOne (Long menuId) {

        return drinkRepository.findById(menuId).stream()
                .findAny().map(MenuResponseDto::new)
                .orElseThrow(() -> new NoSuchElementException(String.format("메뉴를 찾을 수 없습니다. menu_id = [%d]", menuId)));
    }

    /**
     * 최근 n 개의 상품 찾기
     */
    public List<ItemListResponseDto> findTopN(ItemParam param) {
        return drinkRepository.findTopN(param).stream()
                .map(ItemListResponseDto::new)
                .toList();
    }
}
