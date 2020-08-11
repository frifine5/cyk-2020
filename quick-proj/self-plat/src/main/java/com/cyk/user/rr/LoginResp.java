package com.cyk.user.rr;

public class LoginResp {

    private String account;
    private String name;
    private String userGuid;

    private String deptName;
    private String ouGuid;
    private String ouCode;

    public LoginResp() {
    }

    public LoginResp(String account, String name, String userGuid, String deptName, String ouGuid, String ouCode ) {
        this.account = account;
        this.name = name;
        this.deptName = deptName;
        this.userGuid = userGuid;
        this.ouGuid = ouGuid;
        this.ouCode = ouCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getOuGuid() {
        return ouGuid;
    }

    public void setOuGuid(String ouGuid) {
        this.ouGuid = ouGuid;
    }

    public String getOuCode() {
        return ouCode;
    }

    public void setOuCode(String ouCode) {
        this.ouCode = ouCode;
    }


}
