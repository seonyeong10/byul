package com.byul.domain.repository;

import com.byul.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    @Query("select c from Category c where c.id = :id")
    Optional<Category> findById(@Param(value="id") Long categoryId);

    @Query("select c from Category c where c.engName = :engName")
    Optional<Category> findParentByName(@Param(value = "engName") String parentCategoryName);
}
