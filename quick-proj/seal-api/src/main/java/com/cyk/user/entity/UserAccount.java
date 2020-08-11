package com.cyk.user.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 账号,与用户管理
 * @author WangChengyu
 * 2020/5/17 11:35
 */
public class UserAccount {

    private long id;
    private String uid;
    private String account;
    private int accountTypeCode;
    private String accountType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private String info;
    private int passApprCode;
    private String passAppr;
    private String apprInfo;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(int accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPassApprCode() {
        return passApprCode;
    }

    public void setPassApprCode(int passApprCode) {
        this.passApprCode = passApprCode;
    }

    public String getPassAppr() {
        switch (passApprCode){
            case 0:                return "待审";
            case 1:                return "通过";
            case 2:                return "未通过";
            default: return "UNDEFINE";
        }
    }

    public void setPassAppr(String passAppr) {
        this.passAppr = passAppr;
    }

    public String getApprInfo() {
        return apprInfo;
    }

    public void setApprInfo(String apprInfo) {
        this.apprInfo = apprInfo;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        switch (statusCode){
            case 0:                return "无效";
            case 1:                return "有效";
            default: return "UNDEFINE";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
