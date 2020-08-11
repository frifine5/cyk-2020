package com.cyk.account.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 账户操作信息记录表
 * @author WangChengyu
 * 2020/5/17 16:10
 */

public class UserAccountOptRd {
    private long id;
    private String uid;
    private int optTypeCode;
    private String optType;
    private String optBfBlc;    // 操作前余额
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;
    private String optNumber;
    private String balance;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private int isFinCode;
    private boolean fin;
    private String info;

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

    public int getOptTypeCode() {
        return optTypeCode;
    }

    public void setOptTypeCode(int optTypeCode) {
        this.optTypeCode = optTypeCode;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOptBfBlc() {
        return optBfBlc;
    }

    public void setOptBfBlc(String optBfBlc) {
        this.optBfBlc = optBfBlc;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getOptNumber() {
        return optNumber;
    }

    public void setOptNumber(String optNumber) {
        this.optNumber = optNumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }

    public int getIsFinCode() {
        return isFinCode;
    }

    public void setIsFinCode(int isFinCode) {
        this.isFinCode = isFinCode;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
