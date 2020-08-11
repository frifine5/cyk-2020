package com.cyk.common.ex;

public class OptFailException extends Exception {

    public OptFailException(String message) {
        super(message);
    }

    public OptFailException(Throwable t){
        super(t);
    }

}
