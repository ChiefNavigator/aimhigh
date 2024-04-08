package com.lego.aimhigh.openapi.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class AimHighOpenApiTransactionApplication {
  public static void main(String[] args) {
    SpringApplication.run(AimHighOpenApiTransactionApplication.class, args);
  }
}
