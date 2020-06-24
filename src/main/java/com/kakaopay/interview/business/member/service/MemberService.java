package com.kakaopay.interview.business.member.service;

import com.kakaopay.interview.business.member.dto.MemberDto;
import com.kakaopay.interview.business.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberService {
    Member getMemberByMemberNo(Long memberNo);
    Member getMemberByUsername(String username);

    Member createMember(MemberDto.CreateDto createDto);
}
