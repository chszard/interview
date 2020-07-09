package com.kakaopay.interview.business.pay.repository;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.pay.entity.Pay;

import java.util.List;

interface CustomPayRepository {
    List<Pay> getPayListByMember(Member member);

    List<Pay> getPayListByOrder(Order order);

    Pay getPayByPayNo(Long pay);

    Pay getPayCancelByPayNo(Long pay);

    Pay getOriginPayByOrder(Order order);
}
