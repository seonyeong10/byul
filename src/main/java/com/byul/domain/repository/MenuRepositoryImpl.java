package com.byul.domain.repository;

import com.byul.domain.Category;
import com.byul.domain.item.Menu;
import com.byul.service.PagingUtil;
import com.byul.web.dto.request.Sorting;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.byul.domain.item.QMenu.menu;
import static com.byul.domain.QCategory.category;
import static com.byul.domain.QAttachFile.attachFile;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final PagingUtil pagingUtil;

    @Override
    public Page<Menu> findAllByCategory(Category paramCategory, Pageable pageable) {
        JPAQuery<Menu> content = queryFactory
                .selectFrom(menu)
                .leftJoin(menu.category, category)
                .leftJoin(menu.attachFiles, attachFile)
                .where(
                        attachFile.seq.isNull().or(attachFile.seq.eq(1)),
                        eqCategory(paramCategory)
                );

        JPAQuery<Long> count = queryFactory.select(menu.count())
                .from(menu)
                .where(eqCategory(paramCategory));

//        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
        return pagingUtil.getPageImpl(pageable, content, Menu.class);
    }

    public BooleanExpression eqCategory(Category paramCategory) {
        return paramCategory != null ? menu.category.eq(paramCategory).or(menu.category.parent.eq(paramCategory)) : null;
    }
}
