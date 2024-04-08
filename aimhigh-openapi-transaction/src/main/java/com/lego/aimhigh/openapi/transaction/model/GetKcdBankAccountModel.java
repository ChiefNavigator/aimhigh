package com.lego.aimhigh.openapi.transaction.model;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccount;

public interface GetKcdBankAccountModel {

  KcdBankAccount getKcdBankAccount(Long id);
}
