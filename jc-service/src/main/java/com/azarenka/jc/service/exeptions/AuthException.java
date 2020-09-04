package com.azarenka.jc.service.exeptions;

/**
 * Handle of Exception.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 30.09.2020
 */
public class AuthException extends MealException{

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
