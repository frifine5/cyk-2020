package com.cyk.account.entity;

/**
 * 账户（money）结果/余额表
 * @author WangChengyu
 * 2020/5/17 16:04
 */

public class UserAccountRd {

    private long id;
    private String uid;
    private String balance;
    private String overDraft;
    private String ovTypeCode;
    private String ovType;
    private int statusCode;
    private String status;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getOverDraft() {
        return overDraft;
    }

    public void setOverDraft(String overDraft) {
        this.overDraft = overDraft;
    }

    public String getOvTypeCode() {
        return ovTypeCode;
    }

    public void setOvTypeCode(String ovTypeCode) {
        this.ovTypeCode = ovTypeCode;
    }

    public String getOvType() {
        return ovType;
    }

    public void setOvType(String ovType) {
        this.ovType = ovType;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
