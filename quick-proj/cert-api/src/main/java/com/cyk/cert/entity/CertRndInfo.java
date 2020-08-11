package com.cyk.cert.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class CertRndInfo {


    private long id;
    private String serial;
    private int certTypeCode;
    private String certType;
    private String algType;
    private String dn;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validSt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validEnd;
    private byte[] cerData;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private int statusCode;
    private String status;
    private String preSerial;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public String getAlgType() {
        return algType;
    }

    public void setAlgType(String algType) {
        this.algType = algType;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
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

    public byte[] getCerData() {
        return cerData;
    }

    public void setCerData(byte[] cerData) {
        this.cerData = cerData;
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
        switch (statusCode){
            case 0:             return "正常";
            case 1:             return "冻结";
            case 2:             return "注销";
            default:    return "未定义状态";
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreSerial() {
        return preSerial;
    }

    public void setPreSerial(String preSerial) {
        this.preSerial = preSerial;
    }
}
