package com.kakaopay.interview.business.order.entity;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "OrderInfo")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @NotNull
    private Long totalAmt = 0L;

    @NotNull
    private Long buyAmt = 0L;

    private Long vatAmt = 0L;
    private OrderStatus orderStatus;
    private String creator;
    private String updator;

    public static Order create(Member member, OrderDto.PaymentDto paymentDto) {
        Order order = new Order();
        order.setMember(member);
        order.setOrderStatus(OrderStatus.WAIT);
        order.setCreator(member.getUsername());
        order.setTotalAmt(paymentDto.getTotalAmt());
        order.setBuyAmt(paymentDto.getTotalAmt() - paymentDto.getVatAmt());
        order.setVatAmt(paymentDto.getVatAmt());
        return order;
    }

}
