package org.example.redditclone.services;

import org.example.redditclone.exceptions.EmailException;
import org.example.redditclone.models.NotificationEmail;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class EmailService {
    
    /*
    * Send an email to user for registration 
    */

    private final JavaMailSender mailSender;

    public void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("reddit-clone@email.com");
            messageHelper.setTo(notificationEmail.getTo());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getEmailBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("[*] -> User account activation email sent");
        } catch (MailException e) {
            throw new EmailException("Oops, something went wrong while sending an email to: " + notificationEmail.getTo(), e);
        }
    }
}
