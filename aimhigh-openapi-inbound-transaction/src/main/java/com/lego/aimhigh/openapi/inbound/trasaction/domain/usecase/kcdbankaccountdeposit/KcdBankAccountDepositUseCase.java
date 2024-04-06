package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;

public interface KcdBankAccountDepositUseCase {

  Long createBizReitRequest(KcdBankAccountDepositCommand command);

  void depositToKcdBankAccount(KcdBankAccountDepositCommand command, Long bizRemitRequestId);

}
