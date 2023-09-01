package com.byul.service;

import com.byul.domain.PlatformType;
import com.byul.domain.order.Order;
import com.byul.domain.order.OrderStatus;
import com.byul.domain.pay.Payment;
import com.byul.domain.repository.OrderRepository;
import com.byul.domain.repository.PaymentRepository;
import com.byul.pay.KakaoPay;
import com.byul.web.dto.request.pay.KakaoPayRequestDto;
import com.byul.web.dto.request.pay.KakaoPrepareRequestDto;
import com.byul.web.dto.response.pay.kakao.KakaoPayResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayService {

    private final KakaoPay kakaoPay;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;


    /**
     * 결제 준비 하기
     * @param platformType 결제 수단(카카오, 네이버 등)
     */
    public Object prepare(PlatformType platformType, KakaoPrepareRequestDto requestDto) throws IOException {
        switch (platformType) {
            case KAKAO -> {
                //결제 요청 준비하기
                return kakaoPay.prepare(requestDto);

                //결제 요청하기
                //kakaoPay.pay(response.getTid(), response.getNext_redirect_pc_url(), requestDto);
            }
            default -> throw new IllegalArgumentException("알 수 없는 결제 형식입니다.");
        }
    }

    public String pay(PlatformType platformType, KakaoPayRequestDto requestDto) {
        switch (platformType) {
            case KAKAO -> {
                //주문 내역 조회하기
                Order order = orderRepository.findById(requestDto.getOrderId())
                        .stream().findAny()
                        .orElseThrow(() -> new NoSuchElementException("주문 내역이 없습니다. order_id = " + requestDto.getOrderId()));

                if (order.getStatus() != OrderStatus.ORDER) {
                    return "결제 완료된 주문입니다.";
                }

                //결제 요청하기
                KakaoPayResponseDto response = kakaoPay.pay(requestDto);

                //결제 데이터 저장하기
                Payment payment = response.toPayment();
                order.setOrderStatus(OrderStatus.PAYED);
                payment.addOrder(order);

                paymentRepository.save(payment);
                return "결제가 완료되었습니다.";
            }
            default -> throw new IllegalArgumentException("알 수 없는 결제 형식입니다.");
        }
    }
}
