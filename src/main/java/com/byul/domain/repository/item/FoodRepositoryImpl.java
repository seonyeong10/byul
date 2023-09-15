package com.byul.domain.repository.item;

import com.byul.domain.Category;
import com.byul.domain.item.Food;
import com.byul.service.PagingUtil;
import com.byul.web.dto.ItemParam;
import com.byul.web.dto.response.ItemListResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.byul.domain.item.QFood.food;
import static com.byul.domain.item.QMenuDetail.menuDetail;
import static com.byul.domain.QCategory.category;
import static com.byul.domain.QAttachFile.attachFile;

@Repository
@RequiredArgsConstructor
public class FoodRepositoryImpl implements FoodRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    @Override
    public List<Food> findTopN(ItemParam param) {
        return queryFactory.selectFrom(food)
                .leftJoin(food.category, category)
                .leftJoin(food.attachFiles, attachFile)
                .where()
                .limit(param.getLimit())
                .fetch();
    }

    @Override
    public Page<Food> findAllComplex(ItemParam param, Pageable page) {
        JPAQuery<Food> content = queryFactory
                .selectFrom(food)
                .leftJoin(food.category, category)
                .leftJoin(food.attachFiles, attachFile)
                .where(
                        attachFile.seq.isNull().or(attachFile.seq.eq(1)),
                        eqCategory(param.getCategory())
                );

        return pagingUtil.getPageImpl(page, content, Food.class);
    }

    //카테고리 찾기
    private BooleanExpression eqCategory(Category target) {
        return target != null ? food.category.eq(target) : null;
    }
}
