package com.azarenka.jc.service.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static java.util.Collections.singleton;

import com.azarenka.jc.domain.Role;
import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.SignUpForm;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.repository.IUsersRoleMapRepository;
import com.azarenka.jc.service.impl.UserService;
import com.azarenka.jc.service.mail.Mail;
import com.azarenka.jc.service.util.KeyGenerator;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

/**
 * Test for {@link UserService}.
 *
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * Date 02.09.2020
 *
 * @author Anton Azarenka
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({KeyGenerator.class})
public class UserServiceTest {

    @InjectMocks
    private  UserService userService;
    @Mock
    private IUserRepository repository;
    @Mock
    private BCryptPasswordEncoder encoder;
    @Mock
    private IUsersRoleMapRepository roleMapRepository;
    @Mock
    private KeyGenerator keyGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test//TODO rewrite this test
    @Ignore
    public void testSave() {
        //PowerMockito.mockStatic(KeyGenerator.class);
        // when(keyGenerator.generateUuid()).thenReturn("123");
        when(encoder.encode("password")).thenReturn("password");
        doNothing().when(repository).save(getUser());
        when(roleMapRepository.getIdByRole(Role.USER.name())).thenReturn("user_id");
        doNothing().when(roleMapRepository).saveRole("d36dd577-a844-43d3-84e9-a9639c6b7c46", "user_id");
        userService.save(getForm());
        spy(new User(getForm()));
        //verify(repository).save(any(User.class));
        verify(roleMapRepository).getIdByRole(Role.USER.name());
        //verify(roleMapRepository).saveRole(eq("asd"), "user_id");
    }

    @Test
    public void testGetByEmail() {
        User expectedUser = getUser();
        when(repository.getByEmail("email")).thenReturn(expectedUser);
        userService.getByEmail("email");
        verify(repository).getByEmail("email");
    }

   /* @Test
    public void testIsActivate() {
        mockStatic(KeyGenerator.class);
        when(repository.getByActivateCode("123")).thenReturn(new User());
        userService.activating("123");
        verify(repository).getByActivateCode("123");
        verify(repository).updateActivationCode(null, true);
    }*/

    private User getUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("name");
        user.setId("123");
        user.setRegistrationDate(LocalDateTime.of(2019, 12, 30, 0, 0, 0, 0));
        user.setActivateCode("123");
        user.setPassword("password");

        return user;
    }

    public SignUpForm getForm() {
        SignUpForm form = new SignUpForm();
        form.setUsername("name");
        form.setName("name");
        form.setPassword("password");
        form.setRole(singleton(Role.valueOf("USER").name()));
        return form;
    }
}
