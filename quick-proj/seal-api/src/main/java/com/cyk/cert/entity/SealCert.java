package com.cyk.cert.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 签章人证书记录表
 * @author WangChengyu
 * 2020/5/17 16:53
 */
public class SealCert {

    private long id;
    private String uid;
    private String uuid;
    private String alg;
    private int skid;
    private String pk;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validSt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validEnd;
    private String validInfo;
    private String issuerId;
    private String ownerDn;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private int statusCode;
    private String status;
    private byte[] certData;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public int getSkid() {
        return skid;
    }

    public void setSkid(int skid) {
        this.skid = skid;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public Date getValidSt() {
        return validSt;
    }

    public void setValidSt(Date validSt) {
        this.validSt = validSt;
    }

    public Date getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(Date validEnd) {
        this.validEnd = validEnd;
    }

    public String getValidInfo() {
        return validInfo;
    }

    public void setValidInfo(String validInfo) {
        this.validInfo = validInfo;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getOwnerDn() {
        return ownerDn;
    }

    public void setOwnerDn(String ownerDn) {
        this.ownerDn = ownerDn;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
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

    public byte[] getCertData() {
        return certData;
    }

    public void setCertData(byte[] certData) {
        this.certData = certData;
    }

}
