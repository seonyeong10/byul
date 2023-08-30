package com.byul.web;

import com.byul.service.OrderService;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.request.OrderSearchReqDto;
import com.byul.web.dto.response.OrderHistoryResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/order/{memberId}")
    public ResponseEntity<?> order(
            @PathVariable(name = "memberId") Long memberId,
            @RequestBody OrderItemRequestDto.Params params
            ) throws Exception {
        return orderService.order(memberId, params.getOrderItems());
    }

    @GetMapping("/api/v1/my/{memberId}/history")
    public List<OrderHistoryResDto> findAll(
            @PathVariable(name = "memberId") Long memberId,
            OrderSearchReqDto requestDto
    ) {
        return orderService.findAll(memberId, requestDto);
    }

    @GetMapping("/api/v1/my/{memberId}/history/{orderId}")
    public OrderHistoryResDto findOne(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "orderId") Long orderId
    ) {
        return orderService.findOne(memberId, orderId);
    }

    @PutMapping("/api/v1/my/{memberId}/history/{orderId}")
    public void cancel(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "orderId") Long orderId
    ) {
        orderService.cancel(memberId, orderId);
    }
}
