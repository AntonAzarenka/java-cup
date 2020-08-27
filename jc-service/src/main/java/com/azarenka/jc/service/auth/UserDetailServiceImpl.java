package com.azarenka.jc.service.auth;

import com.azarenka.jc.domain.User;
import com.azarenka.jc.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implements user detail service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 28.08.2020
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired(required = false)
    private IUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByEmail(username);
        if (null == user) {
            throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
        }

        return UserPrinciple.build(user);
    }
}
