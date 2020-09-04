package com.azarenka.jc.service.impl;

import com.azarenka.jc.domain.Role;
import com.azarenka.jc.domain.User;
import com.azarenka.jc.domain.auth.SignUpForm;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.repository.IUsersRoleMapRepository;
import com.azarenka.jc.service.api.IUserService;
import com.azarenka.jc.service.mail.Mail;
import com.azarenka.jc.service.mail.MailType;
import com.azarenka.jc.service.mail.SendMessage;
import com.azarenka.jc.service.util.KeyGenerator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link IUserService}.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 28.08.2020
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IUsersRoleMapRepository roleMapRepository;
    @Autowired
    private Mail mail;
    @Value("${mail.url}")
    private String confirmUserCode;

    @Override
    public void save(SignUpForm registrationUser) {
        User user = new User();
        user.setId(KeyGenerator.generateUuid());
        user.setEmail(registrationUser.getUsername());
        user.setName(registrationUser.getName());
        user.setActivateCode(KeyGenerator.generateUuid());
        try {
            LOGGER.info("Start creating user {}", registrationUser.getName());
            user.setPassword(encoder.encode(registrationUser.getPassword()));
            userRepository.save(user);
            String roleId = roleMapRepository.getIdByRole(Role.USER.name());
            roleMapRepository.saveRole(user.getId(), roleId);
            sendMessage(confirmUserCode, user.getEmail(), user.getActivateCode());
            LOGGER.info("User {} has been created", user.getEmail());
        } catch (Exception e) {
            LOGGER.error("Mail hasn't been send to {}, {}", registrationUser.getUsername(), e.getMessage());
            LOGGER.info("User {} hasn't been created", user.getEmail());
        }
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public boolean activating(String code) {
        LOGGER.info("activating....");
        User user = userRepository.getByActivateCode(code);
        if (null == user) {
            return false;
        }
        userRepository.updateActivationStatus(user.getId());
        LOGGER.info(String.format("User %s has been activated", user.getEmail()));
        return true;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private void sendMessage(String uri, String login, String code) {
        SendMessage sendMessage = new SendMessage(login, MailType.REGISTER_CONFIRMATION,
            buildMessageData(uri, code));
        mail.sendMessage(sendMessage);
    }

    private static Map<String, String> buildMessageData(String uri, String code) {
        Map<String, String> data = new HashMap<>();
        int endIndex = StringUtils.ordinalIndexOf(uri, "/", 3);
        data.put("uri", endIndex < 0 ? uri : uri.substring(0, endIndex));
        data.put("link", uri + code);
        return data;
    }
}
