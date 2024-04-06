package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;

public interface UpdateKcdBankAccountModel {
  KcdBankAccount updateAmount(KcdBankAccountDepositCommand command);

}
