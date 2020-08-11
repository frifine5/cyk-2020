package com.cyk.seal.entity;

public class SealApplyEntity {

    private String orderId;
    private String deptName;
    private String deptCode;
    private String area;
    private String address;
    private String tel;
    private String linkman;
    private String reqPerson;
    private String sealName;
    private String sealColor;
    private String validStart;
    private String validEnd;
    private int valid;
    private byte[] picData;
    private int picWidth;
    private int picHeight;
    private String picType;
    private String rdTime;
    private int status;

    public SealApplyEntity() {
    }
    public SealApplyEntity(SealApplyEntity o){
        this.orderId = o.getOrderId();
        this.deptName = o.getDeptName();
        this.deptCode = o.getDeptCode();
        this.area = o.getArea();
        this.address = o.getAddress();
        this.tel = o.getTel();
        this.linkman = o.getLinkman();
        this.reqPerson = o.getReqPerson();
        this.sealName = o.getSealName();
        this.sealColor = o.getSealColor();
        this.validStart = o.getValidStart();
        this.validEnd = o.getValidEnd();
        this.valid = o.getValid();
        this.picData = o.getPicData();
        this.picWidth = o.getPicWidth();
        this.picHeight = o.getPicHeight();
        this.picType = o.getPicType();
        this.rdTime = o.getRdTime();
        this.status = o.getStatus();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getReqPerson() {
        return reqPerson;
    }

    public void setReqPerson(String reqPerson) {
        this.reqPerson = reqPerson;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealColor() {
        return sealColor;
    }

    public void setSealColor(String sealColor) {
        this.sealColor = sealColor;
    }

    public String getValidStart() {
        return validStart;
    }

    public void setValidStart(String validStart) {
        this.validStart = validStart;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public byte[] getPicData() {
        return picData;
    }

    public void setPicData(byte[] picData) {
        this.picData = picData;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getRdTime() {
        return rdTime;
    }

    public void setRdTime(String rdTime) {
        this.rdTime = rdTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }





}
