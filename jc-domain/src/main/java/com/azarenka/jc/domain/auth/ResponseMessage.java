package com.azarenka.jc.domain.auth;

/**
 * Entity for response message.
 *
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * date 28.08.2020
 */
public final class ResponseMessage {

    private String message;

    /**
     * Constructor.
     *
     * @param message message
     */
    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
