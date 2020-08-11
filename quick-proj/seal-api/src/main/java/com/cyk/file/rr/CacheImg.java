package com.cyk.file.rr;

/**
 * 缓存的文件图片
 */
public class CacheImg {


    public CacheImg() {
    }

    public CacheImg(String key, int pageNo, int width, int height, byte[] data) {
        this.key = key;
        this.pageNo = pageNo;
        this.width = width;
        this.height = height;
        this.data = data;
    }

    String key;
    int pageNo;
    int width;
    int height;
    byte[] data;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
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
}
