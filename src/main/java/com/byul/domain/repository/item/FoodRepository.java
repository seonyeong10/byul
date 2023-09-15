package com.byul.domain.repository.item;

import com.byul.domain.item.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryCustom {
}
