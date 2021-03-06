package com.commerce.interview.interview.business.member.service;

import com.commerce.interview.business.member.dto.MemberDto;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class MemberServiceTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Test
    public void createMemberTest() {
        MemberDto.CreateDto createDto = MemberDto.CreateDto.builder()
                .username("username")
                .password("password")
                .enabled(true)
                .email("test@test.com")
                .build();
        Member member = memberService.createMember(createDto);
        Assert.isInstanceOf(Member.class, member);
    }
}
