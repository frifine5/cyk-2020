package com.cyk.apiuser.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RentProjInfo {

    private long id;
    private String uid;
    private String projId;
    private String algType;
    private String authType;
    private String authPuk;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getAlgType() {
        return algType;
    }

    public void setAlgType(String algType) {
        this.algType = algType;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthPuk() {
        return authPuk;
    }

    public void setAuthPuk(String authPuk) {
        this.authPuk = authPuk;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }
}
