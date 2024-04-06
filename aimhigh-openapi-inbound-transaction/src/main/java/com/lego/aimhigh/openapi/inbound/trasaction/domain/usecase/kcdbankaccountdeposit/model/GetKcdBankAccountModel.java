package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;

public interface GetKcdBankAccountModel {

  KcdBankAccount getKcdBankAccount(Long id);
}
