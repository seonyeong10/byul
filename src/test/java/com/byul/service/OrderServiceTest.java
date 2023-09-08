package com.byul.service;

import com.byul.domain.Member;
import com.byul.domain.item.Item;
import com.byul.domain.item.Menu;
import com.byul.domain.item.Sizes;
import com.byul.domain.order.Order;
import com.byul.domain.order.OrderItem;
import com.byul.domain.order.OrderStatus;
import com.byul.domain.repository.*;
import com.byul.web.dto.SyrupType;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.request.OrderSearchReqDto;
import com.byul.web.dto.response.OrderHistoryResDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class OrderServiceTest {

    @Autowired private OrderService orderService;

    @Autowired private ItemRepository itemRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private OrderItemRepository orderItemRepository;

    @Autowired private OrderRepository orderRepository;
    
    private List<SyrupType> syrups = new ArrayList<>();
    
    private Item item;
    
    private Member member;

    @BeforeEach
    public void setup() {
        item = itemRepository.saveAndFlush(Menu.builder().name("아이스 아메리카노").price(4500).build());
        member = memberRepository.saveAndFlush(Member.builder().name("테스트").build());

        SyrupType type1 = new SyrupType("클래식", "2");
        SyrupType type2 = new SyrupType("바닐라", "1");
        syrups.addAll(Arrays.asList(type1, type2));
    }

    @Test
    public void order_save_test () throws Exception {
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

        //when
        orderService.order(member.getId(), Arrays.asList(dto1, dto2));

        //then
        List<OrderItem> savedItems = orderItemRepository.findAll();
        Order order = orderRepository.findAll().get(0);

        assertEquals(2, order.getOrderItems().size());
        assertEquals(member.getName(), order.getMember().getName());
    }

    @Test
    public void order_findAll_test () throws Exception {
        //given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        String startDate = now.withDayOfMonth(1).format(formatter);
        String endDate = now.withDayOfMonth(now.lengthOfMonth()).format(formatter);

        System.out.printf("startDate = %s || endDate = %s\n", startDate, endDate);

        OrderItem item1 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(500)
                .count(1)
                .temp("ICED")
                .sizes(Sizes.TALL)
                .build();
        OrderItem item2 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(0)
                .count(2)
                .temp("ICED")
                .sizes(Sizes.GRANDE)
                .build();
        item1.setItem(item);
        item2.setItem(item);

        Order saved = Order.builder()
                .member(member)
                .orderItems(Arrays.asList(item1, item2))
                .build();
        orderRepository.saveAndFlush(saved);

        OrderSearchReqDto request = OrderSearchReqDto.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        //when
        List<OrderHistoryResDto> result = orderService.findAll(member.getId(), request);

        //then
        assertEquals(saved.getTotalCount(), result.get(0).getTotalCount());
        assertEquals(saved.getTotalPrice(), result.get(0).getTotalPrice());
    }

    @Test
    public void history_findOne_test () throws Exception {
        //given
        OrderItem item1 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(500)
                .count(1)
                .temp("ICED")
                .sizes(Sizes.TALL)
                .build();
        OrderItem item2 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(0)
                .count(2)
                .temp("ICED")
                .sizes(Sizes.GRANDE)
                .build();
        item1.setItem(item);
        item2.setItem(item);

        Order saved = Order.builder()
                .member(member)
                .orderItems(Arrays.asList(item1, item2))
                .build();
        orderRepository.saveAndFlush(saved);

        //when
        OrderHistoryResDto find = orderService.findOne(member.getId(), saved.getId());

        //then
        assertEquals(saved.getTotalPrice(), find.getTotalPrice());
        assertEquals(saved.getTotalCount(), find.getTotalCount());
    }

    @Test
    public void cancle_order_test () throws Exception {
        //given
        OrderItem item1 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(500)
                .count(1)
                .temp("ICED")
                .sizes(Sizes.TALL)
                .build();
        OrderItem item2 = OrderItem.builder()
                .price(item.getPrice())
                .additionalPrice(0)
                .count(2)
                .temp("ICED")
                .sizes(Sizes.GRANDE)
                .build();
        item1.setItem(item);
        item2.setItem(item);

        Order saved = Order.builder()
                .member(member)
                .orderItems(Arrays.asList(item1, item2))
                .build();
        orderRepository.saveAndFlush(saved);

        //when
        orderService.cancel(member.getId(), saved.getId());

        //then
        assertEquals(OrderStatus.CANCEL, saved.getStatus());
    }
}