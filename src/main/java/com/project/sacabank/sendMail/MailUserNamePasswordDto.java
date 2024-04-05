package com.project.sacabank.sendMail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class MailUserNamePasswordDto {
  String to;
  String subject;
  String username;
  String password;

}
