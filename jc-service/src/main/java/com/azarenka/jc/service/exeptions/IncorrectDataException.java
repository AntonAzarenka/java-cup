package com.azarenka.jc.service.exeptions;

public class IncorrectDataException extends MealException {

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
