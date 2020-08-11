package com.cyk.user.rr;

import com.cyk.user.entity.PsUserAccount;

public class PersonUserInfo extends PsUserAccount {

    private String nickName;
    private String realName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public PersonUserInfo() {
    }

    public PersonUserInfo(PsUserAccount psUserAccount) {// 为了赋值方便
        setId(psUserAccount.getId());
        setUid(psUserAccount.getUid());
        setAccount(psUserAccount.getAccount());
        setAccountTypeCode(psUserAccount.getAccountTypeCode());
        setRdTime(psUserAccount.getRdTime());
        setInfo(psUserAccount.getInfo());
        setPassApprCode(psUserAccount.getPassApprCode());
        setApprInfo(psUserAccount.getApprInfo());
        setStatusCode(psUserAccount.getStatusCode());
    }

    public PersonUserInfo(PsUserAccount psUserAccount, String nickName, String realName) {// 为了赋值方便
        setId(psUserAccount.getId());
        setUid(psUserAccount.getUid());
        setAccount(psUserAccount.getAccount());
        setAccountTypeCode(psUserAccount.getAccountTypeCode());
        setRdTime(psUserAccount.getRdTime());
        setInfo(psUserAccount.getInfo());
        setPassApprCode(psUserAccount.getPassApprCode());
        setApprInfo(psUserAccount.getApprInfo());
        setStatusCode(psUserAccount.getStatusCode());
        setNickName(nickName);
        setRealName(realName);
    }




}
