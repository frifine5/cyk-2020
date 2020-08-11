package com.cyk.per.p.entity;


/**
 * 个人图片：印、签名
 * @author WangChengyu
 * 2020/4/14 14:37
 */
public class PsImage {
    private int id;
    private int uid;
    private int type;
    private String imgPath;
    private String crtTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }
}
