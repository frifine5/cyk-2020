package com.cyk.user.bean;




public class UserRoleRef {

    String rowguid;
    int row_id;
    String userguid;
    String roleguid;
    String updatetime;
    int isfromsoa;

    public String getRowguid() {
        return null==rowguid?"":rowguid;
    }

    public void setRowguid(String rowguid) {
        this.rowguid = rowguid;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public String getUserguid() {
        return null==userguid?"":userguid;
    }

    public void setUserguid(String userguid) {
        this.userguid = userguid;
    }

    public String getRoleguid() {
        return null==roleguid?"":roleguid;
    }

    public void setRoleguid(String roleguid) {
        this.roleguid = roleguid;
    }

    public String getUpdatetime() {
        return null==updatetime?"":updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getIsfromsoa() {
        return isfromsoa;
    }

    public void setIsfromsoa(int isfromsoa) {
        this.isfromsoa = isfromsoa;
    }
}
