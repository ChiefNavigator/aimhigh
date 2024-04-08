package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit;

import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface BizRemitUseCase {

  Long createBizRemitRequest(BizRemitRequestCommand command);
  void depositToKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId);
  void withdrawalFromKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId);
}
