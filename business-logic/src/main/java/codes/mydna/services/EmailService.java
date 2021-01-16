package codes.mydna.services;

import codes.mydna.lib.Email;

/**
 * @see codes.mydna.services.impl.EmailServiceImpl
 */
public interface EmailService {

    void sendEmail(Email email);

}
