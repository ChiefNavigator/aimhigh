package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;

public interface GetKcdBankAccountRecordModel {
  KcdBankAccountRecord getNullableKcdBankAccountRecord(String bankTransactionId, KcdBankAccountAction action);
}
