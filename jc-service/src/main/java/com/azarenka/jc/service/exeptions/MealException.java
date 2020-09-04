package com.azarenka.jc.service.exeptions;

public class MealException extends RuntimeException {

    public MealException(String message) {
        super(message);
    }

    public MealException(String message, Throwable cause) {
        super(message, cause);
    }
}
