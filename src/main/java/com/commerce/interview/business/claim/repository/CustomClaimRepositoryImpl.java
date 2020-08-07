package com.commerce.interview.business.claim.repository;

import com.commerce.interview.business.claim.entity.Claim;
import com.commerce.interview.business.claim.entity.ClaimStatus;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.claim.entity.QClaim;
import com.commerce.interview.business.order.entity.Order;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomClaimRepositoryImpl extends QuerydslRepositorySupport implements CustomClaimRepository {

    private QClaim qClaim;

    public CustomClaimRepositoryImpl() {
        super(QClaim.class);
        this.qClaim = QClaim.claim;

    }

    @Override
    public Claim getClaimByClaimNo(Long claimNo) {
        return from(qClaim)
                .where(qClaim.claimNo.eq(claimNo))
                .where(qClaim.claimStatus.eq(ClaimStatus.AV))
                .fetchOne();
    }

    @Override
    public List<Claim> getClaimListByOrder(Order order) {
        return from(qClaim)
                .where(qClaim.order.eq(order))
                .where(qClaim.claimStatus.eq(ClaimStatus.AV))
                .fetch();
    }

    @Override
    public List<Claim> getClaimListByMember(Member member) {
        return from(qClaim)
                .where(qClaim.member.eq(member))
                .where(qClaim.claimStatus.eq(ClaimStatus.AV))
                .fetch();
    }

}
