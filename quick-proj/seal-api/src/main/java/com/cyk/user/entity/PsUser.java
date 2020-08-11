package com.cyk.user.entity;


/**
 * 个人章用户
 * @author WangChengyu
 * 2020/5/17 10:14
 */
public class PsUser extends User {

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

}
