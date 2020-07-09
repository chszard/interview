package com.kakaopay.interview.business.order.service;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrderListByMember(Member member);

    List<Order> getOrderListByMemberNo(Long memberNo);

    Order createOrder(Member member, OrderDto.PaymentDto paymentDto);

    Order getOrderByOrderNo(Long orderNo);

}
