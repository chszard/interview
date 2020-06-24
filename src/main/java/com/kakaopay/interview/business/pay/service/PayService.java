package com.kakaopay.interview.business.pay.service;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.pay.dto.PayDto;
import com.kakaopay.interview.business.pay.entity.Pay;

import java.util.List;

public interface PayService {
    Pay createPay(Pay pay);

    PayDto.PayDecryptDto getPayByPayNo(Long payNo);

    PayDto.PayDecryptDto getPayCancelByPayNo(Long payNo);

    Pay updatePay(Pay pay);

    List<Pay> getPayListByMember(Member member);

    List<Pay> getPayListByOrder(Order order);

    Pay getOriginPayByOrder(Order order);
}
