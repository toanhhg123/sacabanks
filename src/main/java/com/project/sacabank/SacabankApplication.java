package com.project.sacabank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SacabankApplication {

  public static void main(String[] args) {
    SpringApplication.run(SacabankApplication.class, args);
  }

}
