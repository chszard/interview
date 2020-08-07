package com.commerce.interview.business.claim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;
import com.commerce.interview.business.pay.entity.Pay;
import com.commerce.interview.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "ClaimInfo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Claim extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_no")
    private Pay pay;

    private ClaimStatus claimStatus;

    private Long totalAmt;
    private Long cancelAmt;
    private Long cancelVatAmt;

    private String updater;
    private String creator;

    public Claim(Member member
            , Order order
            , Pay pay
            , ClaimStatus claimStatus
            , Long totalAmt
            , Long cancelAmt
            , Long cancelVatAmt
            , String updater
            , String creator) {
        this.member = member;
        this.order = order;
        this.pay = pay;
        this.claimStatus = claimStatus;
        this.totalAmt = totalAmt;
        this.cancelAmt = cancelAmt;
        this.cancelVatAmt = cancelVatAmt;
        this.updater = updater;
        this.creator = creator;
    }
}
