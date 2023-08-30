package com.byul.service;

import com.byul.domain.Category;
import com.byul.domain.item.Menu;
import com.byul.domain.repository.CategoryRepository;
import com.byul.domain.repository.MenuRepository;
import com.byul.web.dto.request.MenuListRequestDto;
import com.byul.web.dto.request.Sorting;
import com.byul.web.dto.response.MenuListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final CategoryRepository categoryRepository;

    /**
     * GET /api/v1/menus
     */
    public Page<MenuListResponseDto> findAll (MenuListRequestDto requestDto) {
        Sorting sorting = Sorting.valueOf(requestDto.getSort().toUpperCase());
        PageRequest page = PageRequest.of(requestDto.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return menuRepository.findAllByCategory(null, page)
                .map(MenuListResponseDto::new);
    }

    /**
     * GET /api/v1/menus/{parentCategoryName}
     * 음료/푸드/상품을 카테고리별로 전체 조회한다.
     */
    public Page<MenuListResponseDto> findAllCategory (MenuListRequestDto requestDto, String parentCategoryName) {
        Category parent = categoryRepository.findParentByName(parentCategoryName)
                .orElseThrow(() -> new NoSuchElementException(String.format("카테고리를 찾을 수 없습니다. eng_name = [%s]", parentCategoryName)));
        Sorting sorting = Sorting.valueOf(requestDto.getSort().toUpperCase());

        PageRequest page = PageRequest.of(requestDto.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return  menuRepository.findAllByCategory(parent, page)
                .map(MenuListResponseDto::new);
    }

    /**
     * /api/v1/menus/{parentCategory}/{childCategory}/{menuId}
     * 음료/푸드/상품을 조회한다.
     */
    public MenuResponseDto findOne (Long menuId) {

        return menuRepository.findById(menuId).stream()
                .findAny().map(MenuResponseDto::new)
                .orElseThrow(() -> new NoSuchElementException(String.format("메뉴를 찾을 수 없습니다. menu_id = [%d]", menuId)));
    }
}
