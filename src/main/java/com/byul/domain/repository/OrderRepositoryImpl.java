package com.byul.domain.repository;

import com.byul.domain.order.Order;
import com.byul.web.dto.request.OrderSearchReqDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.byul.domain.order.QOrder.order;
import static com.byul.domain.order.QOrderItem.orderItem;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findAllComplex(Long memberId, OrderSearchReqDto requestDto) {
        return queryFactory.selectFrom(order)
                .leftJoin(order.orderItems, orderItem)
                .where(
                        order.member.id.eq(memberId),
                        betweenPeriod(requestDto)
                )
                .fetch();
    }

    public BooleanExpression betweenPeriod(OrderSearchReqDto dto) {
        return dto.getStartDate() != null ?
                order.createdDate.between(dto.getStartDate(), LocalDateTime.of(dto.getEndDate(), LocalTime.MAX).withNano(0))
                : null;
    }
}
