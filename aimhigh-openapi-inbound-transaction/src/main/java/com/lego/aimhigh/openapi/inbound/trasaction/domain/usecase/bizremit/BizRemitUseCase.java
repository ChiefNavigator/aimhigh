package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface BizRemitUseCase {

  Long createBizRemitRequest(BizRemitRequestCommand command);
  void depositToKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId);
}
