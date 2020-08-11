package com.cyk.user.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用户
 * @author WangChengyu
 * 2020/5/17 10:15
 */
public class User {

    private long id;
    private String uid;
    private String pin;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rdTime;

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
}
