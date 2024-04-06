package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KcdBankAccountDepositExceptionCode {

  OPEN_API_ACCOUNT_TRANSFER_FAIL("OPEN_API_ACCOUNT_TRANSFER_FAIL", "Open API 계좌 이체 실패하였습니다.");

  private final String statusCode;
  private final String message;
}
