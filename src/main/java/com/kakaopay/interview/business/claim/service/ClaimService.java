package com.kakaopay.interview.business.claim.service;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;

import java.util.List;

public interface ClaimService {
    List<Claim> getClaimListByOrder(Order order);
    List<Claim> getClaimListByMember(Member member);
    Claim cancelOrder(Member member, ClaimDto.CancelDto cancelDto) throws Exception;
    Claim getClaimByClaimNo(Long claimNo);
}
