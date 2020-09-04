package com.azarenka.jc.service.exeptions;

public class AuthException extends MealException{

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
