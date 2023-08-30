package com.byul.domain.repository;

import com.byul.domain.Category;
import com.byul.domain.item.Menu;
import com.byul.web.dto.request.Sorting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepositoryCustom {

    Page<Menu> findAllByCategory(Category category, Pageable pageable);
}
