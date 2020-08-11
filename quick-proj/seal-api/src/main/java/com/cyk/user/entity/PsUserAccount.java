package com.cyk.user.entity;


/**
 * 个人用户账号
 * @author WangChengyu
 * 2020/5/17 11:45
 */
public class PsUserAccount extends UserAccount {

    @Override
    public String getAccountType() {

        switch (getAccountTypeCode()){
            case 0:                return "手机号";
            case 1:                return "电子邮箱";
            case 2:                return "身份证";
            default: return "UNDEFINE";
        }

    }

}
