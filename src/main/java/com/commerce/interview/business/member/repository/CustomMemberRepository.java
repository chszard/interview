package com.commerce.interview.business.member.repository;

import com.commerce.interview.business.member.entity.Member;

interface CustomMemberRepository {
    Member getMemberByMemberNo(Long memberNo);

    Member getMemberByUsername(String username);
}
