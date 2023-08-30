package com.byul.domain.repository;

import com.byul.domain.order.Order;
import com.byul.web.dto.request.OrderSearchReqDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

//    @Query("select o from Order o where o.member.id = :memberId and o.createdDate between :period.startDate and :period.endDate order by o.createdDate desc")
//    List<Order> findAllByMemberIdAndPeriod(@Param(value = "memberId") Long memberId, @Param(value = "period") OrderSearchReqDto requestDto);
}
