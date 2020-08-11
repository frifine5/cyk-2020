package com.cyk.user.bean;




public class OuSecondVo {
    String userguid;            // 用户guid
    String ouguid;              // 部门guid
    int ordernumber;            // 排序号
    int userordernumber;        // 按用户排序号
    String tel;                 // 电话
    int row_id;                 // 自增主键
    String title;               // 标题

    public String getUserguid() {
        return null==userguid?"":userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public String getOuguid() {
        return null==ouguid?"":ouguid;
    }

    public void setOuguid(String ouguid) {
        this.ouguid = ouguid;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public int getUserordernumber() {
        return userordernumber;
    }

    public void setUserordernumber(int userordernumber) {
        this.userordernumber = userordernumber;
    }

    public String getTel() {
        return null==tel?"":tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getTitle() {
        return null==title?"":title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
