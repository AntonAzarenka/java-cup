package com.azarenka.jc.service.mail;


import com.azarenka.jc.domain.User;
import com.azarenka.jc.repository.IUserRepository;
import com.azarenka.jc.service.auth.UserPrinciple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of Mail Service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 28.08.2020
 */
@Service
public class MailService {

    @Autowired
    private Mail mail;
    @Autowired
    private IUserRepository userRepository;
    @Value("${uri}")
    private String uri;

    //@Scheduled
    public void sendMail() {
        List<User> users = userRepository.getAll();
        String login = UserPrinciple.safeGet().getEmail();
        users.forEach(this::accept);
    }

    public void sendMessage(String uri, String email) {
        SendMessage sendMessage = new SendMessage(email, MailType.MAILING,
            buildMessageData(uri, email));
        mail.sendMessage(sendMessage);
    }

    public static Map<String, String> buildMessageData(String uri, String login) {
        Map<String, String> data = new HashMap<>();
        int endIndex = StringUtils.ordinalIndexOf(uri, "/", 3);
        data.put("uri", endIndex < 0 ? uri : uri.substring(0, endIndex));
        data.put("link", uri);
        return data;
    }

    private void accept(User user) {
        sendMessage(uri, user.getEmail());
    }
}
