package com.byul.domain.repository;

import com.byul.domain.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c where c.member.id = :member_id order by c.id")
    List<Cart> findByMemberId(@Param(value = "member_id") Long memberId);

    @Modifying
    @Query("delete from Cart c where c.member.id = :member_id")
    void deleteByMemberId(@Param(value = "member_id") Long memberId);
}
