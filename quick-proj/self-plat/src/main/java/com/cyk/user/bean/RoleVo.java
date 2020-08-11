package com.cyk.user.bean;






public class RoleVo {

    String roleguid;            // 角色guid
    String rolename;            // 角色名称
    int ordernumber;            // 排序号
    int isreserved;             // 是否保留角色
    String belongouguid;        // 所属部门id
    String roletype;            // 角色类型
    int isallowassign;          // 是否允许分配

    public String getRoleguid() {
        return null==roleguid?"":roleguid;
    }

    public void setRoleguid(String roleguid) {
        this.roleguid = roleguid;
    }

    public String getRolename() {
        return null==rolename?"":rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public int getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(int ordernumber) {
        this.ordernumber = ordernumber;
    }

    public int getIsreserved() {
        return isreserved;
    }

    public void setIsreserved(int isreserved) {
        this.isreserved = isreserved;
    }

    public String getBelongouguid() {
        return null==belongouguid?"":belongouguid;
    }

    public void setBelongouguid(String belongouguid) {
        this.belongouguid = belongouguid;
    }

    public String getRoletype() {
        return null==roletype?"":roletype;
    }

    public void setRoletype(String roletype) {
        this.roletype = roletype;
    }

    public int getIsallowassign() {
        return isallowassign;
    }

    public void setIsallowassign(int isallowassign) {
        this.isallowassign = isallowassign;
    }
}
