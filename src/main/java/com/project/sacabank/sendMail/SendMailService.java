package com.project.sacabank.sendMail;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendMailService {
  @Autowired
  private JavaMailSender mailSender;

  @Value("${spring.mail.properties.mail.smtp.from}")
  String from;

  @Value("${spring.mail.properties.mail.smtp.cc}")
  String cc;

  @Autowired
  TemplateEngine templateEngine;

  public MimeMessage createMessage(
      String to,
      String subject,
      String templateName,
      Context context,
      List<String> cc) throws MessagingException {

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

    if (cc != null && !cc.isEmpty()) {
      InternetAddress[] ccAddresses = cc.stream()
          .map(email -> {
            try {
              return new InternetAddress(email);
            } catch (AddressException e) {
              // Handle or log the exception as needed
              return null;
            }
          })
          .filter(Objects::nonNull)
          .toArray(InternetAddress[]::new);
      helper.setCc(ccAddresses);
    }

    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);

    String htmlContent = templateEngine.process(templateName, context);
    helper.setText(htmlContent, true);

    return mimeMessage;
  }

  @Async
  public void sendEmail(String to, String subject, String templateName, Context context) {

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
    try {
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setCc(cc);
      String htmlContent = templateEngine.process(templateName, context);
      helper.setText(htmlContent, true);
      mailSender.send(mimeMessage);
    } catch (MessagingException e) {
      return;
    }

  }

  @Async
  public void sendMailUserNamePassword(MailUserNamePasswordDto mailDto) throws MessagingException {

    Context context = new Context();
    context.setVariable("username", mailDto.getUsername());
    context.setVariable("password", mailDto.getPassword());

    var message = createMessage(mailDto.getTo(), mailDto.getSubject(), "email-template", context, List.of(cc));
    mailSender.send(message);

    log.info("::::::::: send email success ::::::::::");

  }

}
