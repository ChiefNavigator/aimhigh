package com.lego.aimhigh.openapi.inbound.trasaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class AimHighOpenApiInboundTransactionApplication {
  public static void main(String[] args) {
    SpringApplication.run(AimHighOpenApiInboundTransactionApplication.class, args);
  }
}
