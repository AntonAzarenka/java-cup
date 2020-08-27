package com.azarenka.jc.service.mail;

/**
 * Implementation of MailType.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date: 28.08.2020
 */
public enum MailType {

    /**
     * Template for  registration.
     */
    REGISTER_CONFIRMATION("registerConfirmationTemplate.ftl", "Register confirmation"),

    /**
     * Template for mailing.
     */
    MAILING("mailingTemplate.ftl", "Update MealUI");

    private String templateFilename;
    private String subject;

    /**
     * Constructor.
     *
     * @param templateFilename template filename
     * @param subject          the subject
     */
    MailType(String templateFilename, String subject) {
        this.templateFilename = templateFilename;
        this.subject = subject;
    }

    /**
     * Gets template filename.
     *
     * @return template filename
     */
    public String getTemplateFilename() {
        return templateFilename;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
}
