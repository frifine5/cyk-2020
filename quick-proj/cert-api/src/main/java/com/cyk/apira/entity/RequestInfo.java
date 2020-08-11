package com.cyk.apira.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RequestInfo {

    private long id;
    private String uid;
    private byte[] reqData;
    private String bizType;
    private int certTypeCode;
    private String certType;
    private String reqId;
    private String code;
    private String errMsg;
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


    public byte[] getReqData() {
        return reqData;
    }

    public void setReqData(byte[] reqData) {
        this.reqData = reqData;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public int getCertTypeCode() {
        return certTypeCode;
    }

    public void setCertTypeCode(int certTypeCode) {
        this.certTypeCode = certTypeCode;
    }

    public String getCertType() {
        switch (certTypeCode){
            case 0:             return "个人";
            case 1:             return "企业";
            case 2:             return "机构";
            default:    return "未定义状态";
        }
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }
}
