package com.kakaopay.interview.business.pay.entity;

import com.kakaopay.interview.business.claim.entity.Claim;
import com.kakaopay.interview.business.order.dto.OrderDto;
import com.kakaopay.interview.business.order.entity.Order;
import com.kakaopay.interview.business.pay.dto.PayDto;

import java.time.LocalDateTime;

import static com.kakaopay.interview.utils.encryption.Encryption.*;

public class PayFactory {
    public static Pay create(Order order, OrderDto.PaymentDto paymentDto) {
        Pay pay = new Pay();
        pay.setMember(order.getMember());
        pay.setPayStatus(PayStatus.WAIT);
        pay.setCreator(order.getMember().getUsername());
        pay.setPayType(PayType.PAY);
        Payment.Card card = new Payment.Card(paymentDto.getCardNo(), paymentDto.getExpirationDate(), paymentDto.getCvc());
        pay.setEncryptCardInfo(card.getEncryptedStr());
        pay.setMonthlyPayment(paymentDto.getMonthlyPayment());
        pay.setOrder(order);
        //동시 결제 테스트용
        // pay.setCreatedDate(LocalDateTime.of(2020, 6, 23, 00, 00, 00));
        return pay;
    }

    public static Pay update(Pay pay) throws Exception {
        pay.setPayStatus(PayStatus.AV);
        if (pay.getPayType().equals(PayType.PAY)) {
            pay.setMainPayNo(pay.getPayNo());
        }
        pay.setUpdator(pay.getMember().getUsername());
        pay.setEncryptPaymentInfo(encryptPayment(makeEncryptParam(pay)));
        return pay;
    }

    public static Pay cancel(Pay originPay, Claim claim) {
        Pay pay = new Pay();
        pay.setMainPayNo(originPay.getPayNo());
        pay.setMember(claim.getMember());
        pay.setPayStatus(PayStatus.WAIT);
        pay.setClaim(claim);
        pay.setPayType(PayType.CANCEL_PAY);
        pay.setCreator(claim.getMember().getUsername());
        pay.setEncryptCardInfo(originPay.getEncryptCardInfo());
        pay.setMonthlyPayment(originPay.getMonthlyPayment());
        pay.setOrder(claim.getOrder());
        return pay;
    }

    private static String encryptPayment(PayDto.PayEncryptDto payEncryptDto) throws Exception {

        StringBuffer sb = new StringBuffer();
//        1 데이터 구분 문자 10 기능 구분값, 승인(PAYMENT), 취소(CANCEL)
        sb.append(convertByteArrayRightPadding(payEncryptDto.getPayType().getBytes(), 10));
//        2 관리번호 문자 20 unique id(20자리)
        sb.append(convertByteArrayRightPadding(payEncryptDto.getPayNo().getBytes(), 20));
//        0 카드번호 숫자(L)20 카드번호 최소 10자리, 최대 20자리
        sb.append(convertByteArrayRightPadding(payEncryptDto.getCardNo().getBytes(), 20));

//        1 할부개월수 숫자(0)2 일시불인 경우 "00", 2개월인 경우는 "02"로 저장일시불, 2개월 ~ 12개월, 취소시에는 일시불"00"로 저장
        if (payEncryptDto.getPayType().equals(PayType.PAY.getType())) {
            sb.append(convertByteArrayRightZeroPadding(payEncryptDto.getMonthlyPayment().getBytes(), 2));
        } else {
            sb.append(convertByteArrayRightZeroPadding("00".getBytes(), 2));
        }

//        2 카드유효기간숫자(L)4 카드 유효기간월(2자리), 년도(2자리) ex) 0125 -> 2025년1월까지
        sb.append(payEncryptDto.getExpirationDate());
//        3 cvc 숫자(L)3 카드 cvc 데이터
        sb.append(payEncryptDto.getCvc());
//        4 거래금액 숫자10 결제/취소 금액결제 : 100원 이상, 취소 : 결제 금액보다 작아야 함
        sb.append(convertByteArrayLeftPadding(payEncryptDto.getTotalAmt().getBytes(), 10));
//        4 부가가치세 숫자(0) 10 결제/취소 금액의부가세 거래금액보다는 작아야한다. 취소의 경우, 원 거래 금액의 부가가치세와 총 취소금액의 부가가치세의 합과 같아야 한다.
        sb.append(convertByteArrayLeftZeroPadding(payEncryptDto.getVatAmt().getBytes(), 10));
//        5 원거래 관리번호 문자 20 취소시에만 결제 관리번호 저장 결제시에는 공백
        if (payEncryptDto.getPayType().equals(PayType.PAY.getType())) {
            sb.append(convertByteArrayRightPadding("".getBytes(), 20));
        } else {
            sb.append(convertByteArrayRightPadding(payEncryptDto.getMainPayNo().getBytes(), 20));
        }
//        6 암호화된카드정보 문자 300 카드번호, 유호기간, cvc 데이터를 안전하게 암호화 암/복호화 방식 자유롭게 선택
        sb.append(convertByteArrayRightPadding(payEncryptDto.getEncryptCardInfo().getBytes(), 300));
//        7 예비필드문자 47 예비 향후 생길 데이터를 위해 남겨두는 공간
        sb.append(convertByteArrayRightPadding(payEncryptDto.getEtc().getBytes(), 47));

        String data = sb.toString();
        if (data.length() != 446)
            throw new Exception("암호화시 에러가 발생했습니다.");

        StringBuffer header = new StringBuffer();
        //        0 데이터 길이 숫자 4 "데이터 길이"를 제외한 총 길이
        header.append(convertByteArrayLeftPadding(String.valueOf(data.length()).getBytes(), 4));
        header.append(data);

        return header.toString();
    }

    public static PayDto.PayEncryptDto makeEncryptParam(Pay pay) {

        Payment.Card card = Payment.Card.decrypt(pay.getEncryptCardInfo());

        PayDto.PayEncryptDto encryptDto = PayDto.PayEncryptDto.builder()
                .payNo(String.valueOf(pay.getPayNo()))
                .mainPayNo(String.valueOf(pay.getMainPayNo()))
                .payType(pay.getPayType().getType())
                .cardNo(card.getCardNo())
                .expirationDate(String.valueOf(card.getExpirationDate()))
                .cvc(card.getCvc())
                .monthlyPayment(String.valueOf(pay.getMonthlyPayment()))
                .totalAmt(String.valueOf(pay.getOrder().getTotalAmt()))
                .vatAmt((String.valueOf(pay.getOrder().getVatAmt())))
                .encryptCardInfo(pay.getEncryptCardInfo())
                .etc("")
                .build();
        return encryptDto;
    }

    public static PayDto.PayEncryptDto makeCancelEncryptParam(Pay pay) {

        Payment.Card card = Payment.Card.decrypt(pay.getEncryptCardInfo());

        PayDto.PayEncryptDto encryptDto = PayDto.PayEncryptDto.builder()
                .payNo(String.valueOf(pay.getPayNo()))
                .mainPayNo(String.valueOf(pay.getMainPayNo()))
                .payType(pay.getPayType().getType())
                .cardNo(card.getCardNo())
                .expirationDate(String.valueOf(card.getExpirationDate()))
                .cvc(card.getCvc())
                .monthlyPayment(String.valueOf(pay.getMonthlyPayment()))
                .totalAmt(String.valueOf(pay.getClaim().getTotalAmt()))
                .vatAmt((String.valueOf(pay.getClaim().getCancelVatAmt())))
                .encryptCardInfo(pay.getEncryptCardInfo())
                .etc("")
                .build();
        return encryptDto;
    }
}
