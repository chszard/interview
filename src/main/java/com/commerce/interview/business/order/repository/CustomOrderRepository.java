package com.commerce.interview.business.order.repository;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;

import java.util.List;

interface CustomOrderRepository {
    List<Order> getOrderListByMember(Member member);

    Order getOrderByOrderNo(Long orderNo);
}
