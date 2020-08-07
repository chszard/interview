package com.commerce.interview.business.pay.entity;

public enum PayStatus {
    AV("결제", "AV"),
    WAIT("결제대기", "WT");
    private String desc;
    private String status;

    public String getStatus() {
        return this.status;
    }

    private PayStatus(String desc, String status) {
        this.desc = desc;
        this.status = status;
    }
}
