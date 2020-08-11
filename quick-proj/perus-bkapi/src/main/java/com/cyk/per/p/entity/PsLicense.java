package com.cyk.per.p.entity;



public class PsLicense {

    private int id;
    private String phone;
    private String license;
    private String licPath;
    private String validSt;
    private String validEnd;
    private int restTimes;
    private String uptTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicPath() {
        return licPath;
    }

    public void setLicPath(String licPath) {
        this.licPath = licPath;
    }

    public String getValidSt() {
        return validSt;
    }

    public void setValidSt(String validSt) {
        this.validSt = validSt;
    }

    public String getValidEnd() {
        return validEnd;
    }

    public void setValidEnd(String validEnd) {
        this.validEnd = validEnd;
    }

    public int getRestTimes() {
        return restTimes;
    }

    public void setRestTimes(int restTimes) {
        this.restTimes = restTimes;
    }

    public String getUptTime() {
        return uptTime;
    }

    public void setUptTime(String uptTime) {
        this.uptTime = uptTime;
    }
}
