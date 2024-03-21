package com.project.sacabank.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.sacabank.exception.AuthEntryPointJwt;

@Configuration
public class FlywayConfig {

  private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

  @Bean
  public FlywayMigrationStrategy flywayMigrationStrategy() {
    return flyway -> {

      logger.info("2173981278 ==>> flyway running {}", flyway.baseline().baselineVersion);

      flyway.repair();
      flyway.migrate();

    };
  }
}