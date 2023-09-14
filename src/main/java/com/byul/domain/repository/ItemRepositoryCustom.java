package com.byul.domain.repository;

import com.byul.domain.item.Item;
import com.byul.web.dto.ItemParam;

import java.util.List;

public interface ItemRepositoryCustom {

    /**
     * 신 메뉴 가져오기
     * @return
     */
    List<Item> findLatest();

    List<Item> findAllComplex(ItemParam param);
}
