package com.cyk.image.entity;



public class PsSealImage extends SealImage {


    public String getSealType() {
        switch (getSealTypeCode()){
            case 0:                return "制式个人名章";
            case 1:                return "自制个人名章";
            case 2:                return "自制个人手签";
            default: return "未知";
        }
    }

    public String getStatus() {
        switch (getStatusCode()){
            case 0:                return "待制作";
            case 1:                return "有效";
            case 2:                return "冻结中";
            default: return "无效";
        }
    }


}
