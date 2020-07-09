package com.kakaopay.interview.business.member.repository;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;

import java.util.List;
import java.util.Optional;

interface CustomMemberRepository {
    Member getMemberByMemberNo(Long memberNo);

    Member getMemberByUsername(String username);
}
