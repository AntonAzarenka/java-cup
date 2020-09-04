package com.azarenka.jc.service.evaluator;



import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.LoginForm;
import com.azarenka.jc.domain.auth.SignUpForm;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.service.exeptions.AuthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userEvaluator")
public class UserEvaluator {

    @Autowired
    private IUserRepository repository;

    public UserEvaluator() {
    }

    public boolean check(SignUpForm registrationUser) {
        User user = repository.getByEmail(registrationUser.getUsername());
        if (null == user) {
            return true;
        }
        return false;
    }

    public boolean checkActivate(LoginForm loginForm){
        User user = repository.getByEmail(loginForm.getUsername());
        if(user.getActivateCode().equals("ACTIVATED")){
            return true;
        }
        throw  new AuthException(String.format("Пользователь %s не прошел активацию.", user.getEmail()));
    }
}
