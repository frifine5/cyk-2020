package com.cyk.image.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 印章图片属性记录表
 * @author WangChengyu
 * 2020/5/17 16:47
 */
public class SealImage {
    private long id;
    private String uuid;
    private String uid;
    private String sealName;
    private int sealTypeCode;
    private String sealType;
    private int width;
    private int height;
    private String picType;

    private byte[] picData;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private int statusCode;
    private String status;
    private String certId;
    private String sealCode;
    private byte[] sealData;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public int getSealTypeCode() {
        return sealTypeCode;
    }

    public void setSealTypeCode(int sealTypeCode) {
        this.sealTypeCode = sealTypeCode;
    }

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public byte[] getPicData() {
        return picData;
    }

    public void setPicData(byte[] picData) {
        this.picData = picData;
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

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public byte[] getSealData() {
        return sealData;
    }

    public void setSealData(byte[] sealData) {
        this.sealData = sealData;
    }
}
