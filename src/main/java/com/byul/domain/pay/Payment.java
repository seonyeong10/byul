package com.byul.domain.pay;

import com.byul.domain.BaseTimeEntity;
import com.byul.domain.converter.PaymentMethodConverter;
import com.byul.domain.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    @Convert(converter = PaymentMethodConverter.class)
    private PaymentMethod paymentMethodType;
    
    private int amount; //결제 금액(총 금액)
    
    private int taxFree; //비과세 금액
    
    private int vat; //부가세 금액
    
    private int discount; //할인 금액


    @Builder
    public Payment(Long id, Order order, PaymentMethod paymentMethodType, int amount, int taxFree, int vat, int discount) {
        this.id = id;
        this.order = order;
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
        this.taxFree = taxFree;
        this.vat = vat;
        this.discount = discount;
    }

    //==연관관계 메서드==//
    public void addOrder(Order order) {
        this.order = order;
    }
}
