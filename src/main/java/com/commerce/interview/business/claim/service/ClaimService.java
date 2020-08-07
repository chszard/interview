package com.commerce.interview.business.claim.service;

import com.commerce.interview.business.claim.dto.ClaimDto;
import com.commerce.interview.business.claim.entity.Claim;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;

import java.util.List;

public interface ClaimService {
    List<Claim> getClaimListByOrder(Order order);

    List<Claim> getClaimListByMember(Member member);

    Claim cancelOrder(Member member, ClaimDto.CancelDto cancelDto) throws Exception;

    Claim getClaimByClaimNo(Long claimNo);
}
