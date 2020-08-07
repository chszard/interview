package com.commerce.interview.business.claim.repository;

import com.commerce.interview.business.claim.entity.Claim;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;

import java.util.List;

interface CustomClaimRepository {
    Claim getClaimByClaimNo(Long claimNo);

    List<Claim> getClaimListByOrder(Order order);

    List<Claim> getClaimListByMember(Member member);
}
