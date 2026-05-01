package com.navishkadarshana.smileygame.utilities;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailSender {

    @Value("${mail.from}")
    private String mailFrom;

    private final Environment environment;



    private final JavaMailSender javaMailSender;


    public void sendHtmlEmail(MimeMessage message) {
        try {
            log.info("Start function sendHtmlEmail");

            javaMailSender.send(message);

            log.info("E-mail Sent.!");
        } catch (Exception e) {
            log.error("Failed to send HTML email: " + e.getMessage(), e);
            throw e;
        }
    }

    public void sendSimpleEmail(String recipient, String subject, String content) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setFrom(mailFrom, "Smiley Game");


            log.info("recipient : " + recipient);
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));


            message.setSubject(subject);

            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);

            sendHtmlEmail(message);
            log.info("Email successfully dispatched.");

        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Function : sendSimpleEmail " + e.getMessage(), e);
            try {
                throw e;
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
    }



}
