package com.kakaopay.interview.business.order.repository;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;

import java.util.List;

interface CustomOrderRepository {
    List<Order> getOrderListByMember(Member member);
    Order getOrderByOrderNo(Long orderNo);
}
