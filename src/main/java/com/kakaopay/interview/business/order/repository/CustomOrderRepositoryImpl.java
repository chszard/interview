package com.kakaopay.interview.business.order.repository;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.entity.OrderStatus;
import com.kakaopay.interview.business.order.entity.QOrder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomOrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {

    private QOrder qOrder;

    public CustomOrderRepositoryImpl() {
        super(QOrder.class);
        this.qOrder = QOrder.order;
    }

    @Override
    public Order getOrderByOrderNo(Long orderNo) {
        return from(qOrder)
                .where(qOrder.orderNo.eq(orderNo))
                .where(qOrder.orderStatus.eq(OrderStatus.AV))
                .fetchOne();

    }

    public List<Order> getOrderListByMember(Member member) {
        return from(qOrder).where(qOrder.member.eq(member)).fetch();
    }
}
