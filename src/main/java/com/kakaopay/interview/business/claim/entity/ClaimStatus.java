package com.kakaopay.interview.business.claim.entity;

public enum ClaimStatus {
    AV("구매취","AV"),
    WAIT("구매취소대기","WT");
    private String desc;
    private String status;

    public String getStatus() {
        return this.status;
    }

    private ClaimStatus(String desc, String status) {
        this.desc = desc;
        this.status = status;
    }
}
