package com.cyk.per.p.entity;


/**
 * 个人印章
 * @author WangChengyu
 * 2020/4/14 14:39
 */
public class PsStamp {
    private String id;
    private int uid;
    private String ksType;
    private String ksPath;
    private String ksCert;
    private String crtTime;
    private String validSt;
    private String validEnd;
    private String uptTime;
    private String entInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getKsType() {
        return ksType;
    }

    public void setKsType(String ksType) {
        this.ksType = ksType;
    }

    public String getKsPath() {
        return ksPath;
    }

    public void setKsPath(String ksPath) {
        this.ksPath = ksPath;
    }

    public String getKsCert() {
        return ksCert;
    }

    public void setKsCert(String ksCert) {
        this.ksCert = ksCert;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
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

    public String getUptTime() {
        return uptTime;
    }

    public void setUptTime(String uptTime) {
        this.uptTime = uptTime;
    }

    public String getEntInfo() {
        return entInfo;
    }

    public void setEntInfo(String entInfo) {
        this.entInfo = entInfo;
    }
}
