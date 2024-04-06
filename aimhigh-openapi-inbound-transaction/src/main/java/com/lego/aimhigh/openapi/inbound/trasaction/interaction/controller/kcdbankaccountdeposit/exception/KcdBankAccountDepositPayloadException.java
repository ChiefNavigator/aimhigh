package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.exception;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.exception.KcdBankAccountDepositException;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.contant.KcdBankAccountDepositPayloadExceptionCode;
import lombok.Getter;

@Getter
public class KcdBankAccountDepositPayloadException extends KcdBankAccountDepositException {

  public KcdBankAccountDepositPayloadException(KcdBankAccountDepositPayloadExceptionCode code) {
    super(code.getStatusCode(), code.getMessage());
  }
}
