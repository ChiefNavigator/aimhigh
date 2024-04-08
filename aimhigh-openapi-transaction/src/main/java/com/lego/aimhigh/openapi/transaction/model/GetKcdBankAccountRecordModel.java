package com.lego.aimhigh.openapi.transaction.model;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccountRecord;

public interface GetKcdBankAccountRecordModel {

  KcdBankAccountRecord getKcdBankAccountRecord(String bankTransactionId);
}
