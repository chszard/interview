package com.commerce.interview.business.pay.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PayDto {

    @Setter
    @Getter
    public static class PayEncryptDto {
        private String payType;
        private String payNo;
        private String cardNo;
        private String monthlyPayment;
        private String expirationDate;
        private String cvc;
        private String totalAmt;
        private String vatAmt;
        private String mainPayNo;
        private String encryptCardInfo;
        private String encryptPaymentInfo;
        private String etc;

        @Builder
        public PayEncryptDto(String payType, String payNo, String cardNo, String monthlyPayment, String expirationDate, String cvc, String totalAmt, String vatAmt, String mainPayNo, String encryptCardInfo, String encryptPaymentInfo, String etc) {
            this.payType = payType;
            this.payNo = payNo;
            this.cardNo = cardNo;
            this.monthlyPayment = monthlyPayment;
            this.expirationDate = expirationDate;
            this.cvc = cvc;
            this.totalAmt = totalAmt;
            this.vatAmt = vatAmt;
            this.mainPayNo = mainPayNo;
            this.encryptCardInfo = encryptCardInfo;
            this.encryptPaymentInfo = encryptPaymentInfo;
            this.etc = etc;
        }

    }

    @Setter
    @Getter
    public static class PayDecryptDto {
        private String payType;
        private Long payNo;
        private String cardNo;
        private String monthlyPayment;
        private String expirationDate;
        private String cvc;
        private Long totalAmt;
        private Long vatAmt;
        private Long mainPayNo;
        private String etc;
        private Long claimNo;
        private Long orderNo;
        private String encryptPaymentInfo;

        @Builder

        public PayDecryptDto(String payType, Long payNo, String cardNo, String monthlyPayment, String expirationDate, String cvc, Long totalAmt, Long vatAmt, Long mainPayNo, String etc, Long claimNo, Long orderNo, String encryptPaymentInfo) {
            this.payType = payType;
            this.payNo = payNo;
            this.cardNo = cardNo;
            this.monthlyPayment = monthlyPayment;
            this.expirationDate = expirationDate;
            this.cvc = cvc;
            this.totalAmt = totalAmt;
            this.vatAmt = vatAmt;
            this.mainPayNo = mainPayNo;
            this.etc = etc;
            this.claimNo = claimNo;
            this.orderNo = orderNo;
            this.encryptPaymentInfo = encryptPaymentInfo;
        }
    }
}
