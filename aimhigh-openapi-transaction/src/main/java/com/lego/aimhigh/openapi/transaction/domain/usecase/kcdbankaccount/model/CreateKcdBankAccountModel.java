package com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;

public interface CreateKcdBankAccountModel {

  KcdBankAccount createKcdBankAccount(KcdBankAccount kcdBankAccount);
}
