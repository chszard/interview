package com.commerce.interview.business.order.entity;

public enum OrderStatus {
    AV("구매", "AV"),
    WAIT("구매대기", "WT");
    private String desc;
    private String status;

    public String getStatus() {
        return this.status;
    }

    private OrderStatus(String desc, String status) {
        this.desc = desc;
        this.status = status;
    }
}
