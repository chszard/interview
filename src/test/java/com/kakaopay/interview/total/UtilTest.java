package com.kakaopay.interview.total;

import com.kakaopay.interview.utils.encryption.Encryption;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    public void maskingUtilTest() {
        System.out.println(Encryption.maskingCardNo("1234567890"));
    }
}
