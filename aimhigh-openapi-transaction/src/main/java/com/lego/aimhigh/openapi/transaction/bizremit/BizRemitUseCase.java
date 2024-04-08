package com.lego.aimhigh.openapi.transaction.bizremit;

import com.lego.aimhigh.openapi.transaction.command.BizRemitRequestCommand;

public interface BizRemitUseCase {

  Long createBizRemitRequest(BizRemitRequestCommand command);
  void depositToKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId);
}
