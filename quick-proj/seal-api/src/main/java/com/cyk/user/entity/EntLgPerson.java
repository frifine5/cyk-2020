package com.cyk.user.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 法人 主要人员
 * @author WangChengyu
 * 2020/5/17 15:47
 */
public class EntLgPerson {
    private long id;
    private String infoId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private String entName;
    private String lgName;
    private int lgPsTypeCode;
    private String lgPsType;
    private String lgPsDuty;
    private int lgPsIdTypeCode;
    private String lgPsIdType;
    private String lgPsId;
    private String lgPsPhone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public Date getRdTime() {
        return rdTime;
    }

    public void setRdTime(Date rdTime) {
        this.rdTime = rdTime;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getLgName() {
        return lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName;
    }

    public int getLgPsTypeCode() {
        return lgPsTypeCode;
    }

    public void setLgPsTypeCode(int lgPsTypeCode) {
        this.lgPsTypeCode = lgPsTypeCode;
    }

    public String getLgPsType() {
        return lgPsType;
    }

    public void setLgPsType(String lgPsType) {
        this.lgPsType = lgPsType;
    }

    public String getLgPsDuty() {
        return lgPsDuty;
    }

    public void setLgPsDuty(String lgPsDuty) {
        this.lgPsDuty = lgPsDuty;
    }

    public int getLgPsIdTypeCode() {
        return lgPsIdTypeCode;
    }

    public void setLgPsIdTypeCode(int lgPsIdTypeCode) {
        this.lgPsIdTypeCode = lgPsIdTypeCode;
    }

    public String getLgPsIdType() {
        return lgPsIdType;
    }

    public void setLgPsIdType(String lgPsIdType) {
        this.lgPsIdType = lgPsIdType;
    }

    public String getLgPsId() {
        return lgPsId;
    }

    public void setLgPsId(String lgPsId) {
        this.lgPsId = lgPsId;
    }

    public String getLgPsPhone() {
        return lgPsPhone;
    }

    public void setLgPsPhone(String lgPsPhone) {
        this.lgPsPhone = lgPsPhone;
    }
}
