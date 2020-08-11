package com.cyk.user.bean;




public class OuVo {

    int oulevel;                    // 部门级别
    String address;                 // 地址
    String ouguid;                  // 部门guid
    String oushortname;             // 部门简称
    int issubwebflow;               // 是否独立子流程
    String description;             // 部门描述
    String parentouguid;            // 父部门guid
    String issbgj;                  // 未知，json数据中有，文档中没有
    String oucodelevel;             // 部门代码级别
    String ordernumberfull;         // 完全排序号
    int haschildou;                 // 子部门数量
    String baseouguid;              // 独立管理部门
    String postalcode;              // 邮政编码
    int ordernumber;                // 部门排序
    String oucode;                  // 部门编码
    String tel;                     // 部门电话
    OuExtendInfo extendinfo;      // 部门扩展信息
    String updatetime;              // 更新时间
    int haschilduser;               // 直属用户数
    String ouname;                  // 部门名称

    public int getOulevel() {
        return oulevel;
    }

    public void setOulevel(int oulevel) {
        this.oulevel = oulevel;
    }

    public String getAddress() {
        return null==address?"":address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOuguid() {
        return null==ouguid?"":ouguid;
    }

    public void setOuguid(String ouguid) {
        this.ouguid = ouguid;
    }

    public String getOushortname() {
        return null==oushortname?"":oushortname;
    }

    public void setOushortname(String oushortname) {
        this.oushortname = oushortname;
    }

    public int getIssubwebflow() {
        return issubwebflow;
    }

    public void setIssubwebflow(int issubwebflow) {
        this.issubwebflow = issubwebflow;
    }

    public String getDescription() {
        return null==description?"":description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentouguid() {
        return null==parentouguid?"":parentouguid;
    }

    public void setParentouguid(String parentouguid) {
        this.parentouguid = parentouguid;
    }

    public String getIssbgj() {
        return null==issbgj?"":issbgj;
    }

    public void setIssbgj(String issbgj) {
        this.issbgj = issbgj;
    }

    public String getOucodelevel() {
        return null==oucodelevel?"":oucodelevel;
    }

    public void setOucodelevel(String oucodelevel) {
        this.oucodelevel = oucodelevel;
    }

    public String getOrdernumberfull() {
        return null==ordernumberfull?"":ordernumberfull;
    }

    public void setOrdernumberfull(String ordernumberfull) {
        this.ordernumberfull = ordernumberfull;
    }

    public int getHaschildou() {
        return haschildou;
    }

    public void setHaschildou(int haschildou) {
        this.haschildou = haschildou;
    }

    public String getBaseouguid() {
        return null==baseouguid?"":baseouguid;
    }

    public void setBaseouguid(String baseouguid) {
        this.baseouguid = baseouguid;
    }

    public String getPostalcode() {
        return null==postalcode?"":postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getOucode() {
        return null==oucode?"":oucode;
    }

    public void setOucode(String oucode) {
        this.oucode = oucode;
    }

    public String getTel() {
        return null==tel?"":tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public OuExtendInfo getExtendinfo() {
        return extendinfo;
    }

    public void setExtendinfo(OuExtendInfo extendinfo) {
        this.extendinfo = extendinfo;
    }

    public String getUpdatetime() {
        return null==updatetime?"":updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getHaschilduser() {
        return haschilduser;
    }

    public void setHaschilduser(int haschilduser) {
        this.haschilduser = haschilduser;
    }

    public String getOuname() {
        return null==ouname?"":ouname;
    }

    public void setOuname(String ouname) {
        this.ouname = ouname;
    }

}
