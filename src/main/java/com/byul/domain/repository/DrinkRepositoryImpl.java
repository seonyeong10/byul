package com.byul.domain.repository;

import com.byul.domain.Category;
import com.byul.domain.item.Drink;
import com.byul.service.PagingUtil;
import com.byul.web.dto.ItemParam;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.byul.domain.item.QDrink.drink;
import static com.byul.domain.QCategory.category;
import static com.byul.domain.QAttachFile.attachFile;

@Repository
@RequiredArgsConstructor
public class DrinkRepositoryImpl implements DrinkRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final PagingUtil pagingUtil;

    @Override
    public Page<Drink> findAllByCategory(Category paramCategory, Pageable pageable) {
        JPAQuery<Drink> content = queryFactory
                .selectFrom(drink)
                .leftJoin(drink.category, category)
                .leftJoin(drink.attachFiles, attachFile)
                .where(
                        attachFile.seq.isNull().or(attachFile.seq.eq(1)),
                        eqCategory(paramCategory)
                );

        JPAQuery<Long> count = queryFactory.select(drink.count())
                .from(drink)
                .where(eqCategory(paramCategory));

//        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
        return pagingUtil.getPageImpl(pageable, content, Drink.class);
    }

    @Override
    public List<Drink> findTopN(ItemParam param) {
        return queryFactory.selectFrom(drink)
                .leftJoin(drink.category, category)
                .leftJoin(drink.attachFiles, attachFile)
                .where()
                .orderBy(drink.id.desc())
                .limit(param.getLimit())
                .fetch();
    }

    public BooleanExpression eqCategory(Category paramCategory) {
        return paramCategory != null ? drink.category.eq(paramCategory).or(drink.category.parent.eq(paramCategory)) : null;
    }
}
