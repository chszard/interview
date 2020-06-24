package com.kakaopay.interview.business.pay.service.impl;

import com.kakaopay.interview.business.member.entity.Member;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.pay.dto.PayDto;
import com.kakaopay.interview.business.pay.entity.Pay;
import com.kakaopay.interview.business.pay.entity.PayFactory;
import com.kakaopay.interview.business.pay.entity.Payment;
import com.kakaopay.interview.business.pay.repository.PayRepository;
import com.kakaopay.interview.business.pay.service.PayService;
import com.kakaopay.interview.utils.exceptions.PayNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;

    public PayServiceImpl(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    @Override
    public Pay createPay(Pay pay) {
        return payRepository.save(pay);
    }

    @Override
    public Pay updatePay(Pay pay) {
        return payRepository.save(pay);
    }

    @Override
    public List<Pay> getPayListByMember(Member member) {
        return payRepository.getPayListByMember(member);
    }

    @Override
    public Pay getOriginPayByOrder(Order order) {
        return payRepository.getOriginPayByOrder(order);
    }

    @Override
    public List<Pay> getPayListByOrder(Order order) {
        return payRepository.getPayListByOrder(order);
    }

    @Override
    public PayDto.PayDecryptDto getPayByPayNo(Long payNo) {

        Pay pay = Optional.ofNullable(payRepository.getPayByPayNo(payNo))
                .orElseThrow(() -> new PayNotFoundException("결제 정보를 찾을 수 없습니다. payNo: "+ payNo));

        Payment.Card card = Payment.Card.decrypt(pay.getEncryptCardInfo());
        PayDto.PayDecryptDto decryptDto = PayDto.PayDecryptDto.builder()
                .cardNo(card.getCardNo())
                .cvc(card.getCvc())
                .expirationDate(card.getExpirationDate())
                .monthlyPayment(String.valueOf(pay.getMonthlyPayment()))
                .payNo(pay.getPayNo())
                .payType(pay.getPayType().getType())
                .mainPayNo(pay.getMainPayNo())
                .vatAmt(pay.getOrder().getVatAmt())
                .totalAmt(pay.getOrder().getTotalAmt())
                .orderNo(pay.getOrder().getOrderNo())
                .encryptPaymentInfo(pay.getEncryptPaymentInfo())
                .etc("")
                .build();
        return decryptDto;
    }

    @Override
    public PayDto.PayDecryptDto getPayCancelByPayNo(Long payNo) {

        Pay pay = Optional.ofNullable(payRepository.getPayCancelByPayNo(payNo))
                .orElseThrow(() -> new PayNotFoundException("결제 취소 정보를 찾을 수 없습니다. payNo: "+ payNo));

        Payment.Card card = Payment.Card.decrypt(pay.getEncryptCardInfo());
        PayDto.PayDecryptDto decryptDto = PayDto.PayDecryptDto.builder()
                .cardNo(card.getCardNo())
                .cvc(card.getCvc())
                .expirationDate(card.getExpirationDate())
                .monthlyPayment(String.valueOf(pay.getMonthlyPayment()))
                .payNo(pay.getPayNo())
                .payType(pay.getPayType().getType())
                .mainPayNo(pay.getMainPayNo())
                .vatAmt(pay.getOrder().getVatAmt())
                .totalAmt(pay.getOrder().getTotalAmt())
                .orderNo(pay.getOrder().getOrderNo())
                .claimNo(pay.getClaim().getClaimNo())
                .encryptPaymentInfo(pay.getEncryptPaymentInfo())
                .etc("")
                .build();
        return decryptDto;
    }
}
