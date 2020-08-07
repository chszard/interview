package com.commerce.interview.business.pay.entity;

import com.commerce.interview.business.claim.entity.Claim;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;

import com.commerce.interview.common.entity.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PayInfo"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"createdDate", "encryptCardInfo"}))

public class Pay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payNo;

    private Long mainPayNo;

    @NotNull
    private String encryptCardInfo;

    @Column(length = 450)
    private String encryptPaymentInfo;

    //(o ~ 12)
    @NotNull
    @Max(12)
    @Min(0)
    private Integer monthlyPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @OneToOne(mappedBy = "pay")
    private Claim claim;

    @NotNull
    private PayType payType;

    @NotNull
    private PayStatus payStatus;

    private String payCode1;
    private String payCode2;
    private String updator;
    private String creator;
    // 동시 결제 테스트 용 주석
    // private LocalDateTime createdDate;

}
