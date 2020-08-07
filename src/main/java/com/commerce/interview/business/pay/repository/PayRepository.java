package com.commerce.interview.business.pay.repository;

import com.commerce.interview.business.pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long>, CustomPayRepository {

}
