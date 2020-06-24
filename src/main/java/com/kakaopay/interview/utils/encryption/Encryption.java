package com.kakaopay.interview.utils.encryption;

import antlr.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class Encryption {

    private static final int MASKING_START_RANGE = 6;
    private static final int MASKING_END_RANGE = 3;

    public static String convertByteArrayLeftPadding(byte[] convTarget, int size) throws UnsupportedEncodingException {
        byte[] padding = new byte[size - convTarget.length];
        cleanByteArray(padding);
        byte[] ret = ByteBuffer.wrap(new byte[size]).put(padding).put(convTarget).array();
        return new String(ret);
    }

    public static String convertByteArrayRightPadding(byte[] convTarget, int size) throws UnsupportedEncodingException {
        byte[] padding = new byte[size - convTarget.length];
        cleanByteArray(padding);
        byte[] ret = ByteBuffer.wrap(new byte[size]).put(convTarget).put(padding).array();
        return new String(ret);
    }

    public static String convertByteArrayLeftZeroPadding(byte[] convTarget, int size) throws UnsupportedEncodingException {
        byte[] padding = new byte[size - convTarget.length];
        cleanZeroByteArray(padding);
        byte[] ret = ByteBuffer.wrap(new byte[size]).put(padding).put(convTarget).array();
        return new String(ret);
    }

    public static String convertByteArrayRightZeroPadding(byte[] convTarget, int size) throws UnsupportedEncodingException {
        byte[] padding = new byte[size - convTarget.length];
        cleanZeroByteArray(padding);
        byte[] ret = ByteBuffer.wrap(new byte[size]).put(convTarget).put(padding).array();
        return new String(ret);
    }


    public static void cleanByteArray(byte[] target){
        for(int i = 0; i < target.length; i++){
            target[i] = (byte) 32;
        }
    }

    public static void cleanZeroByteArray(byte[] target){
        for(int i = 0; i < target.length; i++){
            target[i] = (byte) 48;
        }
    }

    public static String maskingCardNo(String cardNo) {
        char[] ch = cardNo.toCharArray();
        for(int i = 0; i < ch.length; i ++){
            if ((MASKING_START_RANGE <= i) && ( i < ch.length - MASKING_END_RANGE)) ch[i] = '*';
        }
        return String.valueOf(ch);
    }
}
