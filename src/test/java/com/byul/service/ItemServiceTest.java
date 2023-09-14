package com.byul.service;

import com.byul.web.dto.response.ItemListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ItemServiceTest {
    @Autowired ItemService itemService;

    @Test
    public void findAllComplex_test () throws Exception {
        //given

        //when
        List<ItemListResponseDto> allLatest = itemService.findAllLatest();
        List<ItemListResponseDto> allAdvised = itemService.findAllAdvised();

        //then
    }
}