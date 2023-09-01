package com.byul.web.dto.request.pay;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoPayRequestDto {

    private Long orderId;

    private Long memberId;

    private String pgToken;

    private String tid;

    public void addMember(Long memberId) {
        this.memberId = memberId;
    }
}
