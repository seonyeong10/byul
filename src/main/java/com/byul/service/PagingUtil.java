package com.byul.service;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PagingUtil {

    private final EntityManager em;

    private Querydsl getQuerydsl(Class clazz) {
        PathBuilder<?> builder = new PathBuilderFactory().create(clazz);
        return new Querydsl(em, builder);
    }

    public <T> PageImpl<T> getPageImpl(Pageable pageable, JPQLQuery<T> query, Class clazz) {
        long totalCount = query.fetchCount();
        List<T> results = getQuerydsl(clazz).applyPagination(pageable, query).fetch();  // pageable 적용하여 query 실행
        return new PageImpl<>(results, pageable, totalCount);
    }
}
