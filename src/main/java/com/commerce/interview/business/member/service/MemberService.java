package com.commerce.interview.business.member.service;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.member.dto.MemberDto;

public interface MemberService {
    Member getMemberByMemberNo(Long memberNo);

    Member getMemberByUsername(String username);

    Member createMember(MemberDto.CreateDto createDto);
}
