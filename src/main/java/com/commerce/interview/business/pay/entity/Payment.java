package com.commerce.interview.business.pay.entity;

import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

public class Payment {

    @Getter
    public static class Card {
        @NotNull
        private String cardNo;

        @NotNull
        private String expirationDate;

        private String encryptedStr;

        @NotNull
        private String cvc;

        public Card(@NotNull String cardNo, @NotNull String expirationDate, @NotNull String cvc) {
            this.cardNo = cardNo;
            this.expirationDate = expirationDate;
            this.cvc = cvc;
            encrypt();
        }

        private void encrypt() {
            StringBuffer sb = new StringBuffer();
            sb.append(this.cardNo);
            sb.append("<");
            sb.append(this.expirationDate);
            sb.append("<");
            sb.append(this.cvc);

            this.encryptedStr = Base64.encodeBase64String(sb.toString().getBytes());
        }

        public Payment.Card decrypt() {
            if (this.encryptedStr == null) new NoSuchElementException("인코딩된 카드 정보가 존재하지 않습니다.");
            String decryptStr = new String(Base64.decodeBase64(this.encryptedStr));
            String[] decryptArr = decryptStr.split("<");
            return new Payment.Card(decryptArr[0], decryptArr[1], decryptArr[2]);
        }

        public static Payment.Card decrypt(String encryptedStr) {
            if (encryptedStr == null) new NoSuchElementException("인코딩된 카드 정보가 존재하지 않습니다.");
            String decryptStr = new String(Base64.decodeBase64(encryptedStr));
            String[] decryptArr = decryptStr.split("<");
            return new Payment.Card(decryptArr[0], decryptArr[1], decryptArr[2]);
        }
    }
}
