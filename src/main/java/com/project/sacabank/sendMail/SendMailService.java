package com.project.sacabank.sendMail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendMailService {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.properties.mail.smtp.from}")
  String from;

  @Autowired
  TemplateEngine templateEngine;

  public void sendEmail(String to, String subject, String templateName, Context context) {

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    try {
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      String htmlContent = templateEngine.process(templateName, context);
      helper.setText(htmlContent, true);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      return;
    }

  }
}
