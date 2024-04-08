package com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.command.CreateKcdBankAccountCommand;

public interface KcdBankAccountUseCase {

  KcdBankAccount createKcdBankAccount(CreateKcdBankAccountCommand command);
}
