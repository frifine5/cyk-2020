package com.cyk.apiuser.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RentUser {

    private long id;
    private String uid;
    private String uname;
    private String pin;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date limTime;

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }

    public Date getLimTime() {
        return limTime;
    }

    public void setLimTime(Date limTime) {
        this.limTime = limTime;
    }
}
