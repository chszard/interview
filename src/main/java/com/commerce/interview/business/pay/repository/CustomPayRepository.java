package com.commerce.interview.business.pay.repository;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;
import com.commerce.interview.business.pay.entity.Pay;

import java.util.List;

interface CustomPayRepository {
    List<Pay> getPayListByMember(Member member);

    List<Pay> getPayListByOrder(Order order);

    Pay getPayByPayNo(Long pay);

    Pay getPayCancelByPayNo(Long pay);

    Pay getOriginPayByOrder(Order order);
}
