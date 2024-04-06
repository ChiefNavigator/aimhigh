package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.exception;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.constant.KcdBankAccountDepositExceptionCode;
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

  public KcdBankAccountDepositException(KcdBankAccountDepositExceptionCode code) {
    super(code.getMessage());
    this.statusCode = code.getStatusCode();
    this.message = code.getMessage();
  }
}
