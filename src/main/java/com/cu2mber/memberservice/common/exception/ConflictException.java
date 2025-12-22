package com.cu2mber.memberservice.common.exception;

public class ConflictException extends CommonHttpException{

    private static final int HTTP_STATUS = 409;

    public ConflictException(){
        super(HTTP_STATUS, "Conflict with existing resource");
    }

    public ConflictException(String message){
        super(HTTP_STATUS, message);
    }
}
