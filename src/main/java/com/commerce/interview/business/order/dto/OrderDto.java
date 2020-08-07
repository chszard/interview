package com.commerce.interview.business.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
required
카드번호(10 ~ 16자리 숫자)
유효기간(4자리 숫자, mmyy)
cvc(3자리 숫자)
할부개월수 : 0(일시불), 1 ~ 12
결제금액(100원 이상, 10억원 이하, 숫자)
optional
부가가치세
*/

public class OrderDto {

    private static final String VAT_RATE = "11";

    @Setter
    @Getter
    public static class SearchDto {
        @NotNull
        private Long orderNo;

        @Builder
        public SearchDto(@NotNull Long orderNo) {
            this.orderNo = orderNo;
        }
    }

    @Setter
    @Getter
    public static class PaymentDto {
        private @NotNull
        @Length(min = 6, max = 10)
        String cardNo;

        @NotNull
        @Length(min = 4, max = 4, message = "4글자 여야 합니다.")
        private String expirationDate;

        @NotNull
        @Length(min = 3, max = 3, message = "3글자 여야 합니다.")
        private String cvc;

        @NotNull
        @Max(12)
        @Min(0)
        private Integer monthlyPayment;

        private Long vatAmt;

        @NotNull
        @Min(value = 100L, message = "최소 100원부터 구매가 가능 합니다.")
        @Max(value = 1000000000L, message = "최대 10억원 까지 구매가 가능 합니다.")
        private Long totalAmt;

        @Builder
        public PaymentDto(@NotNull @Length(min = 6, max = 10) String cardNo, @NotNull @Length(min = 4, max = 4) String expirationDate, @NotNull @Length String cvc, @NotNull @Max(12) @Min(0) Integer monthlyPayment, Long vatAmt, @NotNull Long totalAmt) {
            this.cardNo = cardNo;
            this.expirationDate = expirationDate;
            this.cvc = cvc;
            this.monthlyPayment = monthlyPayment;

            if (vatAmt == null) {
                BigDecimal totAmt = new BigDecimal(totalAmt);
                BigDecimal vatRate = new BigDecimal(VAT_RATE);
                vatAmt = totAmt.divide(vatRate, 0, RoundingMode.HALF_UP).longValue();
            }
            this.vatAmt = vatAmt;
            this.totalAmt = totalAmt;
        }
    }
}
