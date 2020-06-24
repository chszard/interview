package com.kakaopay.interview.business.claim.service.Impl;

import com.kakaopay.interview.business.claim.dto.ClaimDto;
import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.claim.entity.ClaimStatus;
import com.kakaopay.interview.business.claim.repository.ClaimRepository;
import com.kakaopay.interview.business.claim.service.ClaimService;
import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.order.service.OrderService;
import com.kakaopay.interview.business.pay.entity.Pay;
import com.kakaopay.interview.business.pay.entity.PayFactory;
import com.kakaopay.interview.business.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class ClaimServiceImpl implements ClaimService {

    private ClaimRepository claimRepository;
    private OrderService orderService;
    private PayService payService;

    public ClaimServiceImpl(ClaimRepository claimRepository, OrderService orderService, PayService payService) {
        this.claimRepository = claimRepository;
        this.orderService = orderService;
        this.payService = payService;
    }

    @Override
    public Claim getClaimByClaimNo(Long claimNo) {
        return claimRepository.getClaimByClaimNo(claimNo);
    }

    @Override
    public synchronized List<Claim> getClaimListByOrder(Order order) {
        return claimRepository.getClaimListByOrder(order);
    }

    @Override
    public List<Claim> getClaimListByMember(Member member) {
        return claimRepository.getClaimListByMember(member);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Claim cancelOrder(Member member, ClaimDto.CancelDto cancelDto) throws Exception {
        Long orderNo = cancelDto.getOrderNo();

        Order order = orderService.getOrderByOrderNo(orderNo);
        List<Claim> claimList = getClaimListByOrder(order);

        try {
            preCancelProcess(order, claimList, cancelDto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        Claim claim;
        try {
            claim = doCancelProcess(order, member, cancelDto);
        } catch (Exception e) {
            throw new Exception("임시 취소 데이터 생성에 실패했습니다.");
        }

        Pay originPay = payService.getOriginPayByOrder(order);

        Pay pay;
        try {
            pay = PayFactory.cancel(originPay, claim);
            payService.createPay(pay);
        } catch (Exception e) {
            throw new Exception("임시 결제 취소 데이터 생성에 실패했습니다.");
        }

        try {
            PayFactory.update(pay);
            payService.updatePay(pay);
        } catch (Exception e) {
            throw new Exception("결제 취소 데이터 업데이트에 실패했습니다.");
        }

        claim.setPay(pay);
        claim.setClaimStatus(ClaimStatus.AV);

        try {
            doneCancelProcess(claim);
        } catch (Exception e) {
            throw new Exception("취소 데이터 생성에 실패했습니다.");
        }

        return claim;
    }

    private void preCancelProcess(Order order, List<Claim> claimList, ClaimDto.CancelDto cancelDto) throws Exception {
        calClaimAmt(order, claimList, cancelDto);
    }

    private Claim doCancelProcess(Order order, Member member, ClaimDto.CancelDto cancelDto) {
        Claim claim = new Claim();
        claim.setMember(member);
        claim.setCancelAmt(cancelDto.getCancelAmt());
        claim.setCancelVatAmt(cancelDto.getCancelVatAmt());
        claim.setTotalAmt(cancelDto.getCancelTotalAmt());
        claim.setClaimStatus(ClaimStatus.WAIT);
        claim.setOrder(order);
        claimRepository.save(claim);
        return claim;
    }

    private void doneCancelProcess(Claim claim) {
        claimRepository.save(claim);
    }


    private void calClaimAmt(Order order, List<Claim> claimList, ClaimDto.CancelDto cancelDto) throws Exception {
        Long buyTotalAmt = order.getTotalAmt();
        Long buyAmt = order.getBuyAmt();
        Long vatAmt = order.getVatAmt();
        Long cancelTotalTotAmt = 0L;
        Long cancelVatSumAmt = 0L;
        Long cancelSumAmt = 0L;
        Long remainTotalAmt = 0L;
        Long remainBuyAmt = 0L;
        Long remainVatAmt = 0L;

        if (!CollectionUtils.isEmpty(claimList)) {
//            cancelTotalTotAmt = claimList.stream()
//                    .filter(m -> m.getClaimStatus().equals(ClaimStatus.AV))
//                    .mapToLong(Claim::getTotalAmt)
//                    .sum();
            cancelVatSumAmt = claimList.stream()
                    .filter(m -> m.getClaimStatus().equals(ClaimStatus.AV))
                    .mapToLong(Claim::getCancelVatAmt)
                    .sum();
            cancelSumAmt = claimList.stream()
                    .filter(m -> m.getClaimStatus().equals(ClaimStatus.AV))
                    .mapToLong(Claim::getCancelAmt)
                    .sum();
        }

        remainBuyAmt = buyAmt - cancelSumAmt;
        if (remainBuyAmt - (cancelDto.getCancelTotalAmt() - cancelDto.getCancelVatAmt()) < 0) {
            throw new Exception("남은 결제 금액을 초과하였습니다.");
        }

        remainVatAmt = vatAmt - cancelVatSumAmt;
        if (remainVatAmt - cancelDto.getCancelVatAmt() < 0) {
            throw new Exception("남은 부가세 금액을 초과하였습니다.");
        }
    }
}
