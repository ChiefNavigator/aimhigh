package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.exception;

import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.contant.KcdBankAccountDepositPayloadExceptionStatusCode;
import lombok.Getter;

@Getter
public class KcdBankAccountDepositPayloadException extends KcdBankAccountDepositException {

  public KcdBankAccountDepositPayloadException(KcdBankAccountDepositPayloadExceptionStatusCode statusCode) {
    super(statusCode.getStatusCode(), statusCode.getMessage());
  }
}
