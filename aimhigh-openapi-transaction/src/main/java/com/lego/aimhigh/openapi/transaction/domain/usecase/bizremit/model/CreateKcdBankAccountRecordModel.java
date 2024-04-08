package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;

public interface CreateKcdBankAccountRecordModel {

  KcdBankAccountRecord createKcdBankAccountRecord(
    KcdBankAccount kcdBankAccount,
    Long amount,
    KcdBankAccountAction kcdBankAccountAction,
    Long userId,
    String bankTransactionId
  );
}
