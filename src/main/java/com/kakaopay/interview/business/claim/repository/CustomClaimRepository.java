package com.kakaopay.interview.business.claim.repository;

import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;

import java.util.List;

interface CustomClaimRepository {
    Claim getClaimByClaimNo(Long claimNo);
    List<Claim> getClaimListByOrder(Order order);
    List<Claim> getClaimListByMember(Member member);
}
