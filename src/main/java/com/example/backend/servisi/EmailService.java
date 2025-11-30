package com.example.backend.servisi;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public int slanjeHtmlEmaila(String to, String subject, String bodyHtml){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true); 

            mailSender.send(message);
            return 0;

        } catch (MessagingException e){
            e.printStackTrace();
            return -1;
        }
    }

    public void slanjeEmailaSaPrilogom(String to, String subject, String bodyHtml, byte[] attachment, String attachmentName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyHtml, true);

            helper.addAttachment(attachmentName, new ByteArrayResource(attachment));

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
