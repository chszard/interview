package com.kakaopay.interview.business.member.service.Impl;

import com.kakaopay.interview.business.member.dto.MemberDto;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.member.repository.MemberRepository;
import com.kakaopay.interview.business.member.service.MemberService;
import com.kakaopay.interview.utils.exceptions.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;


    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member getMemberByMemberNo(Long memberNo) {
        return memberRepository.getMemberByMemberNo(memberNo);
    }

    @Override
    public Member getMemberByUsername(String username) {
        return memberRepository.getMemberByUsername(username);
    }

    @Override
    public Member createMember(MemberDto.CreateDto createDto) {
        Member member = modelMapper.map(createDto, Member.class);
        return memberRepository.save(member);
    }
}
