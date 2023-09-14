package com.byul.domain.repository;

import com.byul.domain.item.Item;
import com.byul.web.dto.ItemParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.byul.domain.item.QItem.item;
import static com.byul.domain.item.QSpecialty.specialty;

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

    @Override
    public List<Item> findAllComplex(ItemParam param) {
        return queryFactory.selectFrom(item)
                .leftJoin(specialty).on(item.id.eq(specialty.item.id))
                .where(
                        isNew(param.isLatest()),       //신상품 찾기
                        isSpecialty(param.isAdvised()) //추천메뉴 찾기
                )
                .fetch();
    }

    /**
     * 추천 상품 찾기
     * @param isAdvised 추천 상품 조회여부
     * @return
     */
    private BooleanExpression isSpecialty(boolean isAdvised) {
        return isAdvised ? specialty.isNotNull() : null;
    }

    /**
     * 신상품 찾기
     * : 현재일로부터 1개월 전까지 등록된 상품을 조회한다.
     * @param isLatest 신상품 조회여부
     * @return
     */
    private BooleanExpression isNew(boolean isLatest) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.of(to.minusMonths(1).toLocalDate(), LocalTime.MIN).withNano(0);

        return isLatest ? item.period.startDate.between(from, to).and(item.period.endDate.after(to).or(item.period.endDate.isNull())) : null;
    }
}
