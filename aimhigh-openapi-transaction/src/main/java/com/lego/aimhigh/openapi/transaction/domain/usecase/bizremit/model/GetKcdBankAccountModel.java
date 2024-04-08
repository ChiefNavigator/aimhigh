package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;

public interface GetKcdBankAccountModel {

  KcdBankAccount getKcdBankAccount(Long id);
}
