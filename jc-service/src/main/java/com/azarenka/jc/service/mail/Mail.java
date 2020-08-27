package com.azarenka.jc.service.mail;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Implementation of Mail.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 28.08.2020
 */
@Component
public class Mail {

    private static final Logger LOGGER = LoggerFactory.getLogger(Mail.class);
    @Value("${mail.username}")
    private String username;
    @Autowired
    private JavaMailSender javaMailSender;
    @Resource
    private Configuration freemarkerConfig;

    @Transactional
    public void sendMessage(SendMessage sendMessage) {
        try {
            LOGGER.info("Send mail to {}", sendMessage.getRecipient());
            MailType mailType = sendMessage.getMailType();
            MimeMessage message = javaMailSender.createMimeMessage();
            freemarkerConfig.setClassForTemplateLoading(getClass(), ServiceConfigurations.BASE_PACKAGE_PATH);
            Template template = freemarkerConfig.getTemplate(mailType.getTemplateFilename());
            String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, sendMessage.getData());
            MimeMessageHelper helper = new MimeMessageHelper(message);
            String sender = sendMessage.getSender();
            if (StringUtils.isNotBlank(sender)) {
                helper.setCc(sender);
            }
            helper.setFrom(username);
            helper.setTo(sendMessage.getRecipient());
            helper.setSubject(mailType.getSubject());
            helper.setText(body, true);
            javaMailSender.send(message);
        } catch (IOException | TemplateException | MessagingException e) {
            ExceptionUtils.handleThrowable(e);
        }
    }
}
