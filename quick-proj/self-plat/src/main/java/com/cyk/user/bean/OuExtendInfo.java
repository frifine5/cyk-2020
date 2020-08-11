package com.cyk.user.bean;



public class OuExtendInfo {


    int isindependence;             // 是否独立部门
    String oucertname;              // 部门证书名称
    String ouguid;                  // 部门guid
    String oufax;                   // 部门传真
    String oucertguid;              // 部门证书guid
    String oucertcontent;           // 部门证书内容
    String individuationimgpath;    // 个性化图片路径

    public int getIsindependence() {
        return isindependence;
    }

    public void setIsindependence(int isindependence) {
        this.isindependence = isindependence;
    }

    public String getOucertname() {
        return null==oucertname?"":oucertname;
    }

    public void setOucertname(String oucertname) {
        this.oucertname = oucertname;
    }

    public String getOuguid() {
        return null==ouguid?"":ouguid;
    }

    public void setOuguid(String ouguid) {
        this.ouguid = ouguid;
    }

    public String getOufax() {
        return null==oufax?"":oufax;
    }

    public void setOufax(String oufax) {
        this.oufax = oufax;
    }

    public String getOucertguid() {
        return null==oucertguid?"":oucertguid;
    }

    public void setOucertguid(String oucertguid) {
        this.oucertguid = oucertguid;
    }

    public String getOucertcontent() {
        return null==oucertcontent?"":oucertcontent;
    }

    public void setOucertcontent(String oucertcontent) {
        this.oucertcontent = oucertcontent;
    }

    public String getIndividuationimgpath() {
        return null==individuationimgpath?"":individuationimgpath;
    }

    public void setIndividuationimgpath(String individuationimgpath) {
        this.individuationimgpath = individuationimgpath;
    }

}
