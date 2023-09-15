package com.byul.service;

import com.byul.domain.Category;
import com.byul.domain.item.Drink;
import com.byul.domain.item.MenuDetail;
import com.byul.domain.item.Sizes;
import com.byul.domain.repository.CategoryRepository;
import com.byul.domain.repository.MenuDetailRepository;
import com.byul.domain.repository.DrinkRepository;
import com.byul.web.dto.request.MenuListRequestDto;
import com.byul.web.dto.response.ItemListResponseDto;
import com.byul.web.dto.response.MenuResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class DrinkServiceTest {

    @Autowired private DrinkService drinkService;

    @Autowired private DrinkRepository drinkRepository;

    @Autowired private CategoryRepository categoryRepository;

    @Autowired private MenuDetailRepository detailRepository;

    private Category category;

    @BeforeEach
    public void setup() {
        Category parent = categoryRepository.saveAndFlush(Category.builder().name("DRINK").build());
        category = categoryRepository.saveAndFlush(Category.builder().name("ESPRESSO").parent(parent).build());
    }

    @Test
    public void findAllByCategory_test () throws Exception {
        //given
        Category parent = categoryRepository.saveAndFlush(Category.builder().name("음료").engName("drink").build());
        Category child = categoryRepository.saveAndFlush(Category.builder().name("에스프레소").engName("espresso").parent(parent).build());
        String name = "커피";

        //for (int i=0 ; i<30 ; i++) {
            drinkRepository.saveAndFlush(Drink.builder().name(name).category(child).build());
        //}

        MenuListRequestDto requestDto = new MenuListRequestDto();
        requestDto.setPage(0);
        requestDto.setSort("default");

        //when
        Category findCtg = categoryRepository.findById(child.getId()).orElse(null);
        //System.out.println(findCtg.getEngName());
        Page<ItemListResponseDto> all = drinkService.findAllCategory(requestDto, parent.getEngName());

        //then
        assertEquals(all.getContent().get(0).getName(), name);
    }

    @Test
    public void findOne_test () throws Exception {
        //given
        String name = "아이스 아메리카노";
        String engName = "Iced Americano";
        String temp = "ICED";
        String etc = "테스트 중입니다.";

        Drink saved = Drink.builder()
                .name(name)
                .engName(engName)
                .category(category)
                .etc(etc)
                .temp(temp)
                .build();
        drinkRepository.saveAndFlush(saved);

        MenuDetail tall = MenuDetail.builder().sizes(Sizes.TALL).capacity(355).calorie(100L).build();
        MenuDetail grande = MenuDetail.builder().sizes(Sizes.GRANDE).capacity(473).calorie(200L).build();
        MenuDetail venti = MenuDetail.builder().sizes(Sizes.VENTI).capacity(591).calorie(300L).build();

        tall.addMenu(saved);
        grande.addMenu(saved);
        venti.addMenu(saved);
        detailRepository.saveAllAndFlush(Arrays.asList(tall, grande, venti));


        //when
        MenuResponseDto find = drinkService.findOne(saved.getId());

        //then
        assertEquals(saved.getId(), find.getId());
        assertEquals(saved.getName(), find.getName());
    }

    @Test
    public void table_test () throws Exception {
        String json = "{\"aid\":\"A4ef05dd654c03b57011\",\"tid\":\"T4ef05bd2a5910121cc5\",\"cid\":\"TC0ONETIME\",\"partner_order_id\":\"1\",\"partner_user_id\":\"1\",\"payment_method_type\":\"MONEY\",\"item_name\":\"아이스 아메리카노\",\"quantity\":1,\"amount\":{\"total\":4500,\"tax_free\":0,\"vat\":409,\"point\":0,\"discount\":0,\"green_deposit\":0},\"created_at\":\"2023-08-30T18:02:54\",\"approved_at\":\"2023-08-30T18:04:05\"}";

//        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        System.out.println("-------------");
//        System.out.println(objectMapper.readValue(json, KakaoPayResponseDto.class));
    }
}