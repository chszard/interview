package com.commerce.interview.business.order.repository;

import com.commerce.interview.business.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomOrderRepository {

}
