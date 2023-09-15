package com.byul.domain.repository;

import com.byul.domain.Category;
import com.byul.domain.item.Drink;
import com.byul.web.dto.ItemParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrinkRepositoryCustom {

    Page<Drink> findAllByCategory(Category category, Pageable pageable);

    /**
     * 최근 N 개 음료 찾기
     */
    List<Drink> findTopN(ItemParam param);
}
