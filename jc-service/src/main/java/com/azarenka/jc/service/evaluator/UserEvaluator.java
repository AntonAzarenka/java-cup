package com.azarenka.jc.service.evaluator;

import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.LoginForm;
import com.azarenka.jc.domain.auth.SignUpForm;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.service.exeptions.AuthException;
import com.azarenka.jc.service.impl.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Checks user authentication.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 30.09.2020
 */
@Component("userEvaluator")
public class UserEvaluator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserRepository repository;

    public UserEvaluator() {
    }

    public boolean check(SignUpForm registrationUser) {
        User user = repository.getByEmail(registrationUser.getUsername());
        if (null == user) {
            return true;
        }
        throw new AuthException(String.format("Пользователь %s уже существует.", user.getEmail()));
    }

    public boolean checkActivate(LoginForm loginForm) {
        User user = repository.getByEmail(loginForm.getUsername());
        if (user.getActivateCode().equals("ACTIVATED")) {
            return true;
        }
        throw new AuthException(String.format("Пользователь %s не прошел активацию.", user.getEmail()));
    }
}
