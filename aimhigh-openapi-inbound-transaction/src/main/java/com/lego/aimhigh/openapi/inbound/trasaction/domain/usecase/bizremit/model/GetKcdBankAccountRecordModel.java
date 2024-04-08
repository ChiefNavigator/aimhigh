package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccountRecord;

public interface GetKcdBankAccountRecordModel {

  KcdBankAccountRecord getKcdBankAccountRecord(String bankTransactionId);
}
