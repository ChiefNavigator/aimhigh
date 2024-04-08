package com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.contant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BizRemitRequestPayloadExceptionCode {

  INVALID_PARAMETER("INVALID_PARAMETER", "잘못된 파라메터로 인해 KCD 계좌에 입금할 수 없습니다.");

  private final String statusCode;
  private final String message;
}
