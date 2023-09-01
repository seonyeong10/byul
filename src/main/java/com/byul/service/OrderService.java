package com.byul.service;

import com.byul.domain.Member;
import com.byul.domain.item.Item;
import com.byul.domain.order.Order;
import com.byul.domain.order.OrderItem;
import com.byul.domain.order.OrderStatus;
import com.byul.domain.repository.*;
import com.byul.exception.CannotCancleOrderException;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.request.OrderSearchReqDto;
import com.byul.web.dto.response.OrderHistoryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;

    private final ItemRepository itemRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    /**
     * 상품을 주문한다.
     * POST /api/v1/order/{member_id}
     */
    @Transactional
    public ResponseEntity<Long> order(Long memberId, List<OrderItemRequestDto> requestDtos) {
        //회원 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id = [%d]", memberId)));
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequestDto dto : requestDtos) {
            OrderItem orderItem = dto.toEntity();
            Item item = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("상품을 찾을 수 없습니다. item_id = [%d]", dto.getItemId())));
            orderItem.setItem(item);

            orderItems.add(orderItem);
        }

        Order order = Order.builder()
                .orderItems(orderItems)
                .member(member)
                .build();

        orderRepository.save(order);

        return ResponseEntity.ok(order.getId());
    }

    /**
     * 전체 주문 조회
     * GET /api/v1/{member_id}/history
     */
    public List<OrderHistoryResDto> findAll(Long memberId, OrderSearchReqDto reqeuestDto) {
        //회원 검증겸 조회
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id = [%d]", memberId)));

        //toList()로 생성하면 수정할 수 없다.(추가도 불가능)
        return orderRepository.findAllComplex(member.getId(), reqeuestDto).stream()
                .map(OrderHistoryResDto::new)
                .toList();
    }

    /**
     * 주문 상세 조회
     * GET /api/v1/{memberId}/history/{orderId}
     */
    public OrderHistoryResDto findOne(Long memberId, Long orderId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id=[%d]", memberId)));
        return orderRepository.findById(orderId).stream()
                .findAny()
                .map(OrderHistoryResDto::new)
                .orElseThrow(() -> new NoSuchElementException(String.format("주문 내역이 존재하지 않습니다. order_id = [%d]", orderId)));
    }

    /**
     * 주문 취소
     * PUT /api/v1/{memberId}/history/{orderId}
     */
    @Transactional
    public void cancel(Long memberId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException(String.format("주문 내역이 존재하지 않습니다. order_id = [%d]", orderId)));

        if(order.getStatus() != OrderStatus.ORDER) {
            throw new CannotCancleOrderException("주문을 취소할 수 없습니다.");
        }
        order.setOrderStatus(OrderStatus.CANCEL);
    }

    public OrderHistoryResDto findForPay(Long orderId) {
        return orderRepository.findById(orderId).stream()
                .findAny()
                .map(OrderHistoryResDto::new)
                .orElseThrow(() -> new NoSuchElementException(String.format("주문 내역이 존재하지 않습니다. order_id = [%d]", orderId)));
    }
}
