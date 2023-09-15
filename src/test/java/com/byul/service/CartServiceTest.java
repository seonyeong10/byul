package com.byul.service;

import com.byul.domain.Member;
import com.byul.domain.item.Drink;
import com.byul.domain.item.Item;
import com.byul.domain.order.Cart;
import com.byul.domain.repository.*;
import com.byul.web.dto.SyrupType;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.response.CartResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class CartServiceTest {

    @Autowired private CartService cartService;

    @Autowired private MemberRepository memberRepository;

    @Autowired private ItemRepository itemRepository;

    @Autowired private DrinkRepository drinkRepository;

    @Autowired private CartRepository cartRepository;

    private Item item;

    private Member member;

    private List<SyrupType> syrups = new ArrayList<>();

    @BeforeEach
    public void setup() {
        item = itemRepository.saveAndFlush(Drink.builder().name("아이스 아메리카노").price(100).build());
        member = memberRepository.saveAndFlush(Member.builder().name("테스트").build());

        SyrupType type1 = new SyrupType("클래식", "2");
        SyrupType type2 = new SyrupType("바닐라", "1");
        syrups.addAll(Arrays.asList(type1, type2));
    }

    @Test
    public void cart_save_test () throws Exception {
        //given
        List<OrderItemRequestDto> requestDtos = new ArrayList<>();

        OrderItemRequestDto dto1 = OrderItemRequestDto.builder()
                .sizes("Tall")
                .pack("mug")
                .syrup(syrups)
                .count(1)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("ALMOND")
                .build();
        OrderItemRequestDto dto2 = OrderItemRequestDto.builder()
                .sizes("GRANDE")
                .pack("tumbler")
                .count(2)
                .itemId(item.getId())
                .price(item.getPrice())
                .build();
        requestDtos.add(dto1); requestDtos.add(dto2);

        //when
        cartService.add(member.getId(), requestDtos);

        //then
        List<Cart> saved = cartRepository.findAll();

        assertEquals(requestDtos.size(), saved.size());
        assertEquals(saved.get(0).getMember().getId(), member.getId());
    }

    @Test
    public void cart_findAll_test () throws Exception {
        //given
        int totalPrice = 3900, totalCount = 3;

        List<OrderItemRequestDto> requestDtos = new ArrayList<>();

        // 100 + 1800 + 600 = 2500
        OrderItemRequestDto dto1 = OrderItemRequestDto.builder()
                .sizes("Tall")
                .pack("mug")
                .syrup(syrups)
                .count(1)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("ALMOND")
                .build();
        // (100 + 600) * 2 = 1400
        OrderItemRequestDto dto2 = OrderItemRequestDto.builder()
                .sizes("GRANDE")
                .pack("tumbler")
                .count(2)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("oat")
                .build();
        requestDtos.add(dto1); requestDtos.add(dto2);

        cartService.add(member.getId(), requestDtos);

        //when
        List<CartResponseDto> response = cartService.findAll(member.getId());

        //then
        assertEquals(totalPrice, response.stream().mapToInt(res -> res.getOrderPrice()).sum());
        assertEquals(totalCount, response.stream().mapToInt(res -> res.getCount()).sum());
    }

    @Test
    public void cart_delete_test () throws Exception {
        //given
        OrderItemRequestDto dto1 = OrderItemRequestDto.builder()
                .sizes("Tall")
                .pack("mug")
                .syrup(syrups)
                .count(1)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("ALMOND")
                .build();
        OrderItemRequestDto dto2 = OrderItemRequestDto.builder()
                .sizes("GRANDE")
                .pack("tumbler")
                .count(2)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("oat")
                .build();

        cartService.add(member.getId(), Arrays.asList(dto1));
        cartService.add(member.getId(), Arrays.asList(dto2));


        List<Long> cartIds = new ArrayList<>();
        List<Cart> carts = cartRepository.findByMemberId(member.getId());
        cartIds.add(carts.get(0).getId());

        //when
        cartService.delete(member.getId(), cartIds);

        List<Cart> after = cartRepository.findByMemberId(member.getId());

        //then
        assertEquals(carts.size() - 1, after.size());
    }

    @Test
    public void cart_deleteAll_test () throws Exception {
        //given
        OrderItemRequestDto dto1 = OrderItemRequestDto.builder()
                .sizes("Tall")
                .pack("mug")
                .syrup(syrups)
                .count(1)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("ALMOND")
                .build();
        OrderItemRequestDto dto2 = OrderItemRequestDto.builder()
                .sizes("GRANDE")
                .pack("tumbler")
                .count(2)
                .itemId(item.getId())
                .price(item.getPrice())
                .milkType("oat")
                .build();

        cartService.add(member.getId(), Arrays.asList(dto1));
        cartService.add(member.getId(), Arrays.asList(dto2));

        //when
        cartService.deleteAll(member.getId());
        List<Cart> all = cartRepository.findByMemberId(member.getId());

        //then
        assertEquals(0, all.size());
    }
}