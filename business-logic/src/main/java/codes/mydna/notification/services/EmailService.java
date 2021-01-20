package codes.mydna.notification.services;

import codes.mydna.notification.lib.Email;
import codes.mydna.notification.services.impl.EmailServiceImpl;

/**
 * @see EmailServiceImpl
 */
public interface EmailService {

    void sendEmail(Email email);

}
