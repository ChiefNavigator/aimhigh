package com.lego.aimhigh.channel.domain.usecase.bizremit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BizRemitExceptionCode {

  CREATE_BIZ_REMIT_REQUEST("CREATE_BIZ_REMIT_REQUEST", "사업자 송금 요청에 실패하였습니다."),
  DEPOSIT_TO_KCD_BANK_ACCOUNT("DEPOSIT_TO_KCD_BANK_ACCOUNT", "KCD 은행 계좌 입금에 실패하였습니다."),
  WITHDRAWAL_FROM_KCD_BANK_ACCOUNT("WITHDRAWAL_TO_KCD_BANK_ACCOUNT", "KCD 은행 계좌 출금에 실패하였습니다.");

  private final String statusCode;
  private final String message;
}
