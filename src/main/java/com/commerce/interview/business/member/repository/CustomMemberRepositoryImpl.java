package com.commerce.interview.business.member.repository;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.member.entity.QMember;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CustomMemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    private QMember qMember;

    public CustomMemberRepositoryImpl() {
        super(QMember.class);
        this.qMember = QMember.member;
    }

    public Member getMemberByMemberNo(Long memberNo) {
        return from(qMember)
                .where(qMember.memberNo.eq(memberNo))
                .where(qMember.enabled.eq(true))
                .fetchOne();
    }

    @Override
    public Member getMemberByUsername(String username) {
        return from(qMember)
                .where(qMember.username.eq(username))
                .where(qMember.enabled.eq(true))
                .fetchOne();
    }
}
