package com.kakaopay.interview.business.pay.entity;

public enum PayType {
    PAY("PAYMENT", "결제"), CANCEL_PAY("CANCEL", "결제취");
    private String type;
    private String desc;

    public String getType() {
        return this.type;
    }

    private PayType(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
