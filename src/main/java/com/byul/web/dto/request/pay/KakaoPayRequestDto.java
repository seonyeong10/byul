package com.byul.web.dto.request.pay;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class KakaoPayRequestDto {

    private Long orderId;

    private Long memberId;

    private String pgToken;

    private String tid;

    public void addMember(Long memberId) {
        this.memberId = memberId;
    }
}
