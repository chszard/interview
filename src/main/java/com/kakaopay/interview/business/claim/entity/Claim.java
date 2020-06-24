package com.kakaopay.interview.business.claim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.pay.entity.Pay;
import com.kakaopay.interview.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Slf4j
@Entity
@Getter
@Setter
@Table(name="ClaimInfo")
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

    private String updator;
    private String creator;
}
