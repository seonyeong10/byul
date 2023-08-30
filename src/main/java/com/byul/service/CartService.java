package com.byul.service;

import com.byul.domain.Member;
import com.byul.domain.item.Item;
import com.byul.domain.order.Cart;
import com.byul.domain.repository.CartRepository;
import com.byul.domain.repository.ItemRepository;
import com.byul.domain.repository.MemberRepository;
import com.byul.web.dto.request.OrderItemRequestDto;
import com.byul.web.dto.response.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;


    /**
     * 장바구니에 저장한다.
     * POST /api/v1/my/{member_id}/cart
     */
    @Transactional
    public ResponseEntity<String> add(Long memberId, List<OrderItemRequestDto> requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NoSuchElementException(String.format("회원을 찾을 수 없습니다. member_id = [%d]", memberId)));
        List<Cart> carts = new ArrayList<>();

        for(OrderItemRequestDto dto : requestDto) {
            Item item = itemRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("상품을 찾을 수 없습니다. member_id = [%d]", dto.getItemId())));
            Cart cart = dto.toCart();

            cart.addItem(item);
            cart.addMember(member);
            carts.add(cart);
        }

        cartRepository.saveAll(carts);

        return ResponseEntity.ok("장바구니에 저장되었습니다.");
    }

    /**
     * 장바구니 상품을 조회한다.
     * GET /api/v1/my/{member_id}/cart
     */
    public List<CartResponseDto> findAll(Long memberId) {

        return cartRepository.findByMemberId(memberId).stream()
                .map(CartResponseDto::new).toList();
    }

    /**
     * 장바구니 상품을 모두 삭제한다.
     * DELETE /api/v1/my/{member_id}/cart/delete/all
     */
    @Transactional
    public void deleteAll(Long memberId) {
        cartRepository.deleteByMemberId(memberId);
    }

    /**
     * 선택 상품을 삭제한다.
     * DELETE /api/v1/my/{member_id}/cart/delete
     */
    @Transactional
    public void delete(Long memberId, List<Long> cartIds) {
        List<Cart> removes = cartRepository.findAllById(cartIds);

        cartRepository.deleteAll(removes);
    }
}
