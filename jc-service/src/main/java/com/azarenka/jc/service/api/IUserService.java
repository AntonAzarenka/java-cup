package com.azarenka.jc.service.api;

import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.SignUpForm;

/**
 * Interface for user service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
public interface IUserService {

    /**
     * Saves user.
     *
     * @param user user
     */
    void save(SignUpForm user);

    /**
     * Returns user by email.
     *
     * @param email email
     * @return {@link User}.
     */
    User getByEmail(String email);

    /**
     * Returns is activate user.
     *
     * @param code code
     * @return true if {@link User was activate email address} otherwise false
     */
    boolean activating(String code);
}
