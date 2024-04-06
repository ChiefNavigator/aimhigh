package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.contant;

import com.lego.aimhigh.openapi.inbound.trasaction.interaction.contant.ResultVoStatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KcdBankAccountDepositPayloadExceptionStatusCode {

  INVALID_PARAMETER(ResultVoStatusCode.FAIL, "잘못된 파라메터로 인해 KCD 계좌에 입금할 수 없습니다.");

  private final String statusCode;
  private final String message;
}
