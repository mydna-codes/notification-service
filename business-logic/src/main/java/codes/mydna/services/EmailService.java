package codes.mydna.services;

import codes.mydna.lib.Email;

/**
 * @see codes.mydna.services.impl.EmailServiceImpl
 */
public interface EmailService {

    public Email sendEmail(Email email);

}
