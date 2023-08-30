package com.byul.web.dto.response.pay.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Amount {
    private int total;

    private int taxFree;

    private int vat;

    private int discount;


    @Builder
    public Amount(int total, int taxFree, int vat, int discount) {
        this.total = total;
        this.taxFree = taxFree;
        this.vat = vat;
        this.discount = discount;
    }
}
