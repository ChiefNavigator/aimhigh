package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.exception;

import lombok.Getter;

@Getter
public class KcdBankAccountDepositException extends RuntimeException {

  private final String statusCode;

  private final String message;

  public KcdBankAccountDepositException(String statusCode, String message) {
    super(message);
    this.statusCode = statusCode;
    this.message = message;
  }
}
