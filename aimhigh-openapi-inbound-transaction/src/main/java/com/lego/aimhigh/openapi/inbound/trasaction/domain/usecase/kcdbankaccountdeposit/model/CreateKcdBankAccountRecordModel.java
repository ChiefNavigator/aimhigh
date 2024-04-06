package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.contant.KcdBankAccountAction;

public interface CreateKcdBankAccountRecordModel {
  KcdBankAccountRecord createKcdBankAccountRecord(KcdBankAccount kcdBankAccount, KcdBankAccountAction kcdBankAccountAction, Long userId);
}