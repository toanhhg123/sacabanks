package com.project.sacabank.sendMail;

import static com.project.sacabank.utils.Constants.API_MAIL_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.sacabank.base.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = API_MAIL_PATH)
public class MailController extends BaseController {
  @Autowired
  SendMailService sendMailService;

  @PostMapping()
  public ResponseEntity<?> sendMail(@RequestBody MailDto mailDto) {
    sendMailService.sendEmail(mailDto.getTo(), mailDto.getSubject(), mailDto.getBody());
    return this.onSuccess(mailDto);
  }

}
