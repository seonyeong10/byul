package com.byul.domain.repository;

import com.byul.domain.order.Order;
import com.byul.web.dto.request.OrderSearchReqDto;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findAllComplex(Long memberId, OrderSearchReqDto requestDto);
}