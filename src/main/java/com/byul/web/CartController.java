package com.byul.web;

import com.byul.service.CartService;
import com.byul.web.dto.request.CartRequestDto;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.response.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/api/v1/my/{member_id}/cart")
    public ResponseEntity<String> add(
            @PathVariable(value = "member_id") Long memberId,
            @RequestBody OrderItemRequestDto.Params params
            ) {
        return cartService.add(memberId, params.getOrderItems());
    }

    @GetMapping("/api/v1/my/{member_id}/cart")
    public List<CartResponseDto> findAll(
            @PathVariable(value = "member_id") Long memberId
    ) {
        return cartService.findAll(memberId);
    }

    @DeleteMapping("/api/v1/my/{member_id}/cart/delete/all")
    public void deleteAll(
            @PathVariable(value = "member_id") Long memberId
    ) {
        cartService.deleteAll(memberId);
    }

    @DeleteMapping("/api/v1/my/{member_id}/cart/delete")
    public void delete(
            @PathVariable(value = "member_id") Long memberId,
            @RequestParam(name = "cartIds") List<Long> cartIds
    ) {
        cartService.delete(memberId, cartIds);
    }
}
