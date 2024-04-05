package com.project.sacabank.sendMail;

import static com.project.sacabank.utils.Constants.API_MAIL_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.thymeleaf.context.Context;

@RestController
@RequestMapping(path = API_MAIL_PATH)
public class MailController extends BaseController {
  @Autowired
  SendMailService sendMailService;

  // @PostMapping()
  // public ResponseEntity<?> sendMail(@RequestBody MailUserNamePasswordDto
  // mailDto) {

  // Context context = new Context();
  // context.setVariable("username", mailDto.getUsername());
  // context.setVariable("password", mailDto.getPassword());

  // sendMailService.sendEmail(mailDto.getTo(), mailDto.getSubject(),
  // "email-template", context);
  // return this.onSuccess(mailDto);
  // }

  @PostMapping("send_mail_create_account")
  public ResponseEntity<?> sendMailCreateAccount(@RequestBody MailUserNamePasswordDto mailDto) {

    Context context = new Context();
    context.setVariable("username", mailDto.getUsername());
    context.setVariable("password", mailDto.getPassword());

    sendMailService.sendEmail(mailDto.getTo(), mailDto.getSubject(), "email-template", context);
    return this.onSuccess(mailDto);
  }

}
