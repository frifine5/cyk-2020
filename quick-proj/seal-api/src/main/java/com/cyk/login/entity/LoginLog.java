package com.cyk.login.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 登录日志
 * @author WangChengyu
 * 2020/5/17 15:57
 */

public class LoginLog {


    private long id;
    private String account;
    private String loginType;
    private String loginIp;
    private String loginPoint;
    private String loginInfo;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginPoint() {
        return loginPoint;
    }

    public void setLoginPoint(String loginPoint) {
        this.loginPoint = loginPoint;
    }

    public String getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }
}
