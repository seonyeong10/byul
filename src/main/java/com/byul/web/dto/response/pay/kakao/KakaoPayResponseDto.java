package com.byul.web.dto.response.pay.kakao;


import com.byul.domain.pay.Payment;
import com.byul.domain.pay.PaymentMethod;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoPayResponseDto {
    private String paymentMethodType;

    private Amount amount;

    @Builder
    public KakaoPayResponseDto(String paymentMethodType, Amount amount) {
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
    }

    public Payment toPayment() {
        return Payment.builder()
                .paymentMethodType(PaymentMethod.KAKAO)
                .amount(amount.getTotal())
                .discount(amount.getDiscount())
                .vat(amount.getVat())
                .taxFree(amount.getTaxFree())
                .build();
    }
}
