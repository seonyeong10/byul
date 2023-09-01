package com.byul.web.dto.request.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoPrepareRequestDto {
    private Long orderId;

    private String itemName;

    private int quantity;

    private int totalAmount;

    private Long memberId;

    @Builder
    public KakaoPrepareRequestDto(Long orderId, String itemName, int quantity, int totalAmount) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public void addMember(Long memberId) {
        this.memberId = memberId;
    }
}
