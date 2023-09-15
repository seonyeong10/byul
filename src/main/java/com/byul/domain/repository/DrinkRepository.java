package com.byul.domain.repository;

import com.byul.domain.item.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long>, DrinkRepositoryCustom {
}
