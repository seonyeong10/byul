package com.byul.service;

import com.byul.domain.Category;
import com.byul.domain.item.Food;
import com.byul.domain.repository.CategoryRepository;
import com.byul.domain.repository.item.FoodRepository;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.request.Sorting;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    private final CategoryRepository categoryRepository;

    /**
     * 메뉴 홈 - 푸드 찾기
     */
    public List<ItemListResponseDto> findTopN(ItemParam param) {
        return foodRepository.findTopN(param).stream()
                .map(ItemListResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 푸드 찾기
     */
    public Page<ItemListResponseDto> findAllByCategory(ItemParam param, String category) {
        Category find = categoryRepository.findParentByName(category).orElse(null);
                //.orElseThrow(() -> new NoSuchElementException(String.format("카테고리를 찾을 수 없습니다")));

        if (find == null) {
            log.info("ERR! [findAllByCategory] 카테고리를 찾을 수 없습니다.");
            return new PageImpl<>(new ArrayList<>(), null, 0);
        }

        Sorting sorting = Sorting.valueOf(param.getSort().toUpperCase());

        param.setCategory(find);
        PageRequest page = PageRequest.of(param.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return foodRepository.findAllComplex(param, page)
                .map(ItemListResponseDto::new);
    }

    /**
     * 푸드 찾기
     */
    public MenuResponseDto findOne(Long itemId) {
        return foodRepository.findById(itemId).stream()
                .findAny().map(MenuResponseDto::new)
                .orElse(null);
    }

    /**
     * 푸드 전체 찾기
     */
    public Page<ItemListResponseDto> findAll(ItemParam param) {
        Sorting sorting = Sorting.valueOf(param.getSort().toUpperCase());
        PageRequest page = PageRequest.of(param.getPage(), 25, Sort.by(sorting.getValue()).descending());

        return foodRepository.findAllComplex(param, page)
                .map(ItemListResponseDto::new);
    }
}
