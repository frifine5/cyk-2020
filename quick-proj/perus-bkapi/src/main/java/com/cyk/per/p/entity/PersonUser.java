package com.cyk.per.p.entity;

public class PersonUser {

    private int id;
    private String name;
    private String code;
    private String crtTime;
    private String uptTime;
    private int vp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }

    public String getUptTime() {
        return uptTime;
    }

    public void setUptTime(String uptTime) {
        this.uptTime = uptTime;
    }

    public int getVp() {
        return vp;
    }

    public void setVp(int vp) {
        this.vp = vp;
    }
}
