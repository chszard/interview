package com.commerce.interview.business.pay.repository;

import com.commerce.interview.business.member.entity.Member;
import com.commerce.interview.business.order.entity.Order;
import com.commerce.interview.business.order.entity.QOrder;
import com.commerce.interview.business.pay.entity.Pay;
import com.commerce.interview.business.pay.entity.PayStatus;
import com.commerce.interview.business.pay.entity.PayType;
import com.commerce.interview.business.pay.entity.QPay;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomPayRepositoryImpl extends QuerydslRepositorySupport implements CustomPayRepository {

    private QPay qPay;

    public CustomPayRepositoryImpl() {
        super(QOrder.class);
        this.qPay = QPay.pay;
    }

    @Override
    public List<Pay> getPayListByMember(Member member) {
        return from(qPay).where(qPay.member.eq(member)).fetch();
    }

    @Override
    public List<Pay> getPayListByOrder(Order order) {
        return from(qPay)
                .where(qPay.order.eq(order))
                .where(qPay.payStatus.eq(PayStatus.AV))
                .fetch();
    }

    @Override
    public Pay getPayByPayNo(Long payNo) {
        return from(qPay)
                .where(qPay.payNo.eq(payNo))
                .where(qPay.payStatus.eq(PayStatus.AV))
                .where(qPay.payType.eq(PayType.PAY))
                .fetchOne();
    }

    @Override
    public Pay getPayCancelByPayNo(Long payNo) {
        return from(qPay)
                .where(qPay.payNo.eq(payNo))
                .where(qPay.payStatus.eq(PayStatus.AV))
                .where(qPay.payType.eq(PayType.CANCEL_PAY))
                .fetchOne();
    }

    @Override
    public Pay getOriginPayByOrder(Order order) {
        return from(qPay)
                .where(qPay.order.eq(order))
                .where(qPay.payStatus.eq(PayStatus.AV))
                .where(qPay.payType.eq(PayType.PAY))
                .fetchOne();
    }
}
