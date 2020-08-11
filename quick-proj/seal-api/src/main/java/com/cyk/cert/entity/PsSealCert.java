package com.cyk.cert.entity;

public class PsSealCert extends SealCert {

    public String getStatus(){
        switch (getStatusCode()){
            case 0:         return "未启用";
            case 1:         return "启用";
            case 2:         return "冻结";
            case 3:         return "注销";
            default: return "未定义状态";
        }
    }
}
