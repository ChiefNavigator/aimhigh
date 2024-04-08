package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord;

public interface GetKcdBankAccountRecordModel {

  KcdBankAccountRecord getKcdBankAccountRecord(String bankTransactionId);
}
