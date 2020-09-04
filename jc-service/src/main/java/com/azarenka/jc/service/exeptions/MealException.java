package com.azarenka.jc.service.exeptions;

/**
 * Handler of Exception.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 30.09.2020
 */
public class MealException extends RuntimeException {

    public MealException(String message) {
        super(message);
    }

    public MealException(String message, Throwable cause) {
        super(message, cause);
    }
}
