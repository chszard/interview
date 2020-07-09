package com.kakaopay.interview.business.member.controller;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.member.dto.MemberDto;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.utils.exceptions.DuplicatedUserException;
import com.kakaopay.interview.utils.exceptions.UserNotFoundException;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping(value = "/v1/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "사용자를 생성한다.", notes = "사용자를 생성한다.")
    @PostMapping("/create")
    public ResponseEntity createMember(@RequestBody MemberDto.CreateDto createDto) throws Exception {
        Member member = memberService.getMemberByUsername(createDto.getUsername());
        if (member != null) {
            throw new DuplicatedUserException("사용자가 존재합니다. username: " + createDto.getUsername());
        }

        return ResponseEntity.ok(memberService.createMember(createDto));
    }

}
