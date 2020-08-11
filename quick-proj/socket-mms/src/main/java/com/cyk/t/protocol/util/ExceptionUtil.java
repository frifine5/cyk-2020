package com.cyk.t.protocol.util;

public class ExceptionUtil {

    public static String getCouseStrackString(Throwable t, String msg){
        if(null == msg){
            msg = "";
        }
        msg += t.getCause() == null ? "": t.getCause().getMessage() +" >> " + getCouseStrackString(t.getCause(), msg);
        return msg;
    }
}
