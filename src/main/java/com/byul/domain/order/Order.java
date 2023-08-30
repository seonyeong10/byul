package com.byul.domain.order;

import com.byul.domain.BaseTimeEntity;
import com.byul.domain.Member;
import com.byul.domain.converter.OrderStatusConverter;
import com.byul.domain.pay.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status = OrderStatus.ORDER;

    private LocalDateTime finishedDate;

    private LocalDateTime acceptedDate;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    //==생성 메서드==//
    @Builder
    public Order(Long id, List<OrderItem> orderItems, Member member, LocalDateTime finishedDate, LocalDateTime acceptedDate, Payment payment ) {
        this.id = id;
        orderItems.forEach(item -> addOrderItem(item));
        this.member = member;
        this.finishedDate = finishedDate;
        this.acceptedDate = acceptedDate;
        this.payment = payment;
    }

    public void setOrderStatus(OrderStatus status) {
        this.status = status;
    }

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==비지니스 메서드==//
    /**
     * 전체 주문 가격을 리턴한다.
     */
    public int getTotalPrice() {
        return orderItems.stream().mapToInt(OrderItem::getOrderPrice).sum();
    }

    /**
     * 전체 주문 수량을 리턴한다.
     */
    public int getTotalCount() {
        return orderItems.stream().mapToInt(OrderItem::getCount).sum();
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", member_name=" + member.getName() +
                ", status=" + status +
                '}';
    }
}
