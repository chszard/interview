package com.commerce.interview.business.claim.repository;

import com.commerce.interview.business.claim.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long>, CustomClaimRepository {

}
