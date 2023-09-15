package com.byul.domain.repository.item;

import com.byul.domain.Category;
import com.byul.domain.item.Food;
import com.byul.web.dto.ItemParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryCustom {
    List<Food> findTopN(ItemParam param);

    Page<Food> findAllComplex(ItemParam param, Pageable page);
}
