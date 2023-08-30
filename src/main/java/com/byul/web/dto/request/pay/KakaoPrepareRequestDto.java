package com.byul.web.dto.request.pay;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class KakaoPrepareRequestDto {
    private Long orderId;

    private String itemName;

    private int quantity;

    private int totalAmount;

    private Long memberId;

    public void addMember(Long memberId) {
        this.memberId = memberId;
    }
}
