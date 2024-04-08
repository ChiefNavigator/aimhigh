package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;

public interface UpdateKcdBankAccountModel {

  KcdBankAccount amountDeposit(KcdBankAccount kcdBankAccount, Long amount, Long userId);

  KcdBankAccount amountWithdrawal(KcdBankAccount kcdBankAccount, Long amount, Long userId);
}
