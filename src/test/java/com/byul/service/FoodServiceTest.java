package com.byul.service;

import com.byul.domain.Category;
import com.byul.domain.item.Food;
import com.byul.domain.repository.CategoryRepository;
import com.byul.domain.repository.item.FoodRepository;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class FoodServiceTest {
    @Autowired private FoodService foodService;
    @Autowired private FoodRepository foodRepository;
    @Autowired private CategoryRepository categoryRepository;

    private static Category category;
    private static Food food;

    @BeforeAll
    public static void setup(
            @Autowired CategoryRepository categoryRepository,
            @Autowired FoodRepository foodRepository
    ) {
        category = categoryRepository.saveAndFlush(Category.builder().name("bread").engName("bread").build());
        food = foodRepository.saveAndFlush(Food.builder().category(category).name("빵").build());
    }

    @Test
    public void findTopN_test () throws Exception {
        //given
        String name = "빵";
        foodRepository.saveAndFlush(Food.builder().category(category).name(name).build());

        ItemParam param = ItemParam.builder().limit(20).build();

        //when
        List<ItemListResponseDto> all = foodService.findTopN(param);

        //then
        assertEquals(all.size(), 2);
    }

    @Test
    public void findAllByCategory_test () throws Exception {
        //given
        ItemParam param = ItemParam.builder().page(0).build();

        //when
        Page<ItemListResponseDto> all = foodService.findAllByCategory(param, category.getName());

        //then
        assertEquals(1, all.getContent().size());
        assertEquals("빵", all.getContent().get(0).getName());
    }

    @Test
    public void findOne_test () throws Exception {
        //given
        Long targetId = food.getId();

        //when
        MenuResponseDto find = foodService.findOne(targetId);

        //then
        assertEquals(targetId, find.getId());
        assertEquals(food.getName(), find.getName());
    }
}