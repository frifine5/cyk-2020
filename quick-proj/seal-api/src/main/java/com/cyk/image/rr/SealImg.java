package com.cyk.image.rr;


/**
 * 印章小图属性类
 * @author WangChengyu
 * 2020/6/5 16:21
 */
public class SealImg {

    public SealImg() {
    }

    public SealImg(String uuid, String type, int width, int height, byte[] data) {
        this.uuid = uuid;
        this.type = type;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    public SealImg(String uuid, String sealCode, String sealName, String type, int width, int height, byte[] data) {
        this.uuid = uuid;
        this.sealCode = sealCode;
        this.sealName = sealName;
        this.type = type;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    String uuid;
    String sealCode;
    String sealName;
    String type;
    int width;
    int height;
    byte[] data;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }
}
