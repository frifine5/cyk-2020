package com.cyk.user.entity;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * 法人详细信息
 * @author WangChengyu
 * 2020/5/17 12:13
 */
public class EntUserInfo {

    private long id;
    private String infoId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rdTime;
    private String entName;
    private int entStatusCode;
    private String entStatus;
    private String encSocId;
    private String entTaxpayId;
    private String entOrgId;
    private String entRegId;
    private String entRegDept;
    private String entTypeCode;
    private String entType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entCrtDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entRegValidSt;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entRegValidEnd;
    private String entRegAreaCode;
    private String entRegArea;
    private String entRegAddr;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date entRegApprDate;
    private String entScropInfo;

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

    public int getEntStatusCode() {
        return entStatusCode;
    }

    public void setEntStatusCode(int entStatusCode) {
        this.entStatusCode = entStatusCode;
    }

    public String getEntStatus() {
        return entStatus;
    }

    public void setEntStatus(String entStatus) {
        this.entStatus = entStatus;
    }

    public String getEncSocId() {
        return encSocId;
    }

    public void setEncSocId(String encSocId) {
        this.encSocId = encSocId;
    }

    public String getEntTaxpayId() {
        return entTaxpayId;
    }

    public void setEntTaxpayId(String entTaxpayId) {
        this.entTaxpayId = entTaxpayId;
    }

    public String getEntOrgId() {
        return entOrgId;
    }

    public void setEntOrgId(String entOrgId) {
        this.entOrgId = entOrgId;
    }

    public String getEntRegId() {
        return entRegId;
    }

    public void setEntRegId(String entRegId) {
        this.entRegId = entRegId;
    }

    public String getEntRegDept() {
        return entRegDept;
    }

    public void setEntRegDept(String entRegDept) {
        this.entRegDept = entRegDept;
    }

    public String getEntTypeCode() {
        return entTypeCode;
    }

    public void setEntTypeCode(String entTypeCode) {
        this.entTypeCode = entTypeCode;
    }

    public String getEntType() {
        return entType;
    }

    public void setEntType(String entType) {
        this.entType = entType;
    }

    public Date getEntCrtDate() {
        return entCrtDate;
    }

    public void setEntCrtDate(Date entCrtDate) {
        this.entCrtDate = entCrtDate;
    }

    public Date getEntRegValidSt() {
        return entRegValidSt;
    }

    public void setEntRegValidSt(Date entRegValidSt) {
        this.entRegValidSt = entRegValidSt;
    }

    public Date getEntRegValidEnd() {
        return entRegValidEnd;
    }

    public void setEntRegValidEnd(Date entRegValidEnd) {
        this.entRegValidEnd = entRegValidEnd;
    }

    public String getEntRegAreaCode() {
        return entRegAreaCode;
    }

    public void setEntRegAreaCode(String entRegAreaCode) {
        this.entRegAreaCode = entRegAreaCode;
    }

    public String getEntRegArea() {
        return entRegArea;
    }

    public void setEntRegArea(String entRegArea) {
        this.entRegArea = entRegArea;
    }

    public String getEntRegAddr() {
        return entRegAddr;
    }

    public void setEntRegAddr(String entRegAddr) {
        this.entRegAddr = entRegAddr;
    }

    public Date getEntRegApprDate() {
        return entRegApprDate;
    }

    public void setEntRegApprDate(Date entRegApprDate) {
        this.entRegApprDate = entRegApprDate;
    }

    public String getEntScropInfo() {
        return entScropInfo;
    }

    public void setEntScropInfo(String entScropInfo) {
        this.entScropInfo = entScropInfo;
    }




}
