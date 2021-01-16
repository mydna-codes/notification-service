package codes.mydna.services.impl;

import codes.mydna.lib.Email;
import codes.mydna.services.ArchiveService;
import codes.mydna.services.EmailService;
import codes.mydna.configurations.EmailConfig;
import codes.mydna.validation.Assert;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.InternalServerErrorException;
import java.util.Properties;
import java.util.logging.Logger;

@ApplicationScoped
public class EmailServiceImpl implements EmailService {

    public static final Logger LOG = Logger.getLogger(EmailServiceImpl.class.getName());

    @Inject
    private ArchiveService archiveService;

    @Inject
    private EmailConfig emailConfig;

    @Override
    public void sendEmail(Email email) {

        if(!emailConfig.isSendingAllowed()){
            LOG.warning("Email sending is disabled in configuration server!");
            return;
        }

        String to = email.getTo();
        String subject = email.getSubject();
        String content = email.getContent();
        String from = emailConfig.getFrom();
        String user = emailConfig.getUser();
        String pass = emailConfig.getPass();
        String host = emailConfig.getHost();
        String port = emailConfig.getPort();

        // Set sender to email
        email.setFrom(from);

        Assert.fieldNotEmpty(to, "to", Email.class);
        Assert.fieldNotEmpty(subject, "subject", Email.class);
        Assert.fieldNotEmpty(content, "content", Email.class);

        //Get the session object
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pass);
                    }
                });

        Email sentEmail = archiveService.saveEmail(email);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content);
            message.addHeader("Content-type", "text/html; charset=UTF-8");
            message.addHeader("format", "flowed");
            message.addHeader("Content-Transfer-Encoding", "8bit");
            Transport.send(message);
        } catch (MessagingException e) {
            // Rollback
            if(sentEmail != null)
                archiveService.deleteEmail(sentEmail.getId());
            LOG.severe("Failed to send email. Reason: " + e.getMessage());

            throw new InternalServerErrorException("Failed to send email");
        }
    }



}
