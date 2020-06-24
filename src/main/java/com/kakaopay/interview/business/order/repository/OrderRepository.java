package com.kakaopay.interview.business.order.repository;

import com.kakaopay.interview.business.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

}
