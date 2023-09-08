package com.byul.domain.repository;

import com.byul.domain.item.Item;
import com.byul.web.dto.request.OrderSearchReqDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.byul.domain.item.QItem.item;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Item> findLatest() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.of(today.minusMonths(1).toLocalDate(), LocalTime.MIN).withNano(0);

        return queryFactory
                .selectFrom(item)
                .where(
                        item.createdDate.between(from, today)
                ).fetch();
    }
}
