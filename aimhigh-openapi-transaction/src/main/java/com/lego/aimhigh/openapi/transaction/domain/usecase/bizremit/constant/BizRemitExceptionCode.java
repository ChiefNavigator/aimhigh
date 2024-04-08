package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BizRemitExceptionCode {

  OPEN_API_ACCOUNT_TRANSFER_FAIL("OPEN_API_ACCOUNT_TRANSFER_FAIL", "Open API 계좌 이체 실패하였습니다."),
  EXCEED_MAX_RETRY_COUNT("MAX_RETRY_COUNT", "최대 송금 요청 횟수를 초과하였습니다.");

  private final String statusCode;
  private final String message;
}
