package com.kakaopay.interview.business.claim.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ClaimDto {

    private static final String VAT_RATE = "11";

    @Setter
    @Getter
    public static class CancelDto {
        @NotNull
        private Long orderNo;

        @NotNull
        private Long cancelTotalAmt = 0L;

        @JsonIgnore
        @NotNull
        private Long cancelAmt = 0L;
        private Long cancelVatAmt = 0L;

        @Builder
        public CancelDto(@NotNull Long orderNo, @NotNull Long cancelTotalAmt, @NotNull Long cancelAmt, Long cancelVatAmt) {
            this.orderNo = orderNo;
            this.cancelTotalAmt = cancelTotalAmt;
            if (cancelVatAmt == null) {
                BigDecimal cAmt = new BigDecimal(cancelTotalAmt);
                BigDecimal vatRate = new BigDecimal(VAT_RATE);
                cancelVatAmt = cAmt.divide(vatRate, 0, RoundingMode.HALF_UP).longValue();
            }
            this.cancelVatAmt = cancelVatAmt;
            this.cancelAmt = this.cancelTotalAmt - this.cancelVatAmt;
        }
    }
}
