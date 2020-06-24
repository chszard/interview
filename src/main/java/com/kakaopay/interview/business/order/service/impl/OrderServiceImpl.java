package com.kakaopay.interview.business.order.service.impl;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.entity.OrderStatus;
import com.kakaopay.interview.business.order.repository.OrderRepository;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.business.pay.entity.*;
import com.kakaopay.interview.business.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final PayService payService;

    public OrderServiceImpl(OrderRepository orderRepository, MemberService memberService, PayService payService) {
        this.orderRepository = orderRepository;
        this.memberService = memberService;
        this.payService = payService;
    }

    @Override
    public Order getOrderByOrderNo(Long orderNo) {
        return orderRepository.getOrderByOrderNo(orderNo);
    }

    @Override
    public List<Order> getOrderListByMember(Member member) {
        return orderRepository.getOrderListByMember(member);
    }

    @Override
    public List<Order> getOrderListByMemberNo(Long memberId) {
        Member member;
        try {
            member = memberService.getMemberByMemberNo(memberId);
        } catch (Exception e) {
            throw e;
        }
        return getOrderListByMember(member);
    }

    @Override
    @Transactional
    public Order createOrder(Member member, OrderDto.PaymentDto paymentDto) {

        Order order = Order.create(member, paymentDto);

        try {
            order = preOrderProcess(order);
        } catch (Exception e) {
            throw new RuntimeException("주문데이터 생성에 실패했습니다.");
        }

        Pay pay;
        try {
            pay = PayFactory.create(order, paymentDto);
            doPayProcess(pay);
        } catch (Exception e) {
//            log.debug(e.getMessage());
            throw new RuntimeException("결제 데이터 생성에 실패했습니다.");
        }

        try {
            PayFactory.update(pay);
            donePayProcess(pay);
        } catch (Exception e) {
//            log.debug(e.getMessage());
            throw new RuntimeException("결제 데이터 생성에 실패했습니다.");
        }

        try {
            order.setOrderStatus(OrderStatus.AV);
            order.setUpdator(member.getUsername());
            order = doneOrderProcess(order);
        } catch (Exception e) {
            throw new RuntimeException("주문에 실패했습니다.");
        }

        return order;
    }

    public Order preOrderProcess(Order order) {
        return orderRepository.save(order);
    }
    public Pay doPayProcess(Pay pay) {
        return payService.createPay(pay);
    }
    public Pay donePayProcess(Pay pay) {
        return payService.createPay(pay);
    }

    public Order doneOrderProcess(Order order) {
        return orderRepository.save(order);
    }
}
