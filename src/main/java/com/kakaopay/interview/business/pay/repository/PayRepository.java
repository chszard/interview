package com.kakaopay.interview.business.pay.repository;

import com.kakaopay.interview.business.pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayRepository extends JpaRepository<Pay, Long>, CustomPayRepository {

}
