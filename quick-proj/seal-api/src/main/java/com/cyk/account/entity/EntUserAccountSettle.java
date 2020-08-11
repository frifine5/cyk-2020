package com.cyk.account.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 法人结算表
 * @author WangChengyu
 * 2020/5/17 16:18
 */
public class EntUserAccountSettle {

    private long id;
    private String uid;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date seTime;
    private String seMount;
    private int seTypeCode;
    private String seType;
    private int StatusCode;
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

    public Date getSeTime() {
        return seTime;
    }

    public void setSeTime(Date seTime) {
        this.seTime = seTime;
    }

    public String getSeMount() {
        return seMount;
    }

    public void setSeMount(String seMount) {
        this.seMount = seMount;
    }

    public int getSeTypeCode() {
        return seTypeCode;
    }

    public void setSeTypeCode(int seTypeCode) {
        this.seTypeCode = seTypeCode;
    }

    public String getSeType() {
        return seType;
    }

    public void setSeType(String seType) {
        this.seType = seType;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
