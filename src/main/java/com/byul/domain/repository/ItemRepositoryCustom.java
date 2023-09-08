package com.byul.domain.repository;

import com.byul.domain.item.Item;

import java.util.List;

public interface ItemRepositoryCustom {

    /**
     * 신 메뉴 가져오기
     * @return
     */
    List<Item> findLatest(); 
}
