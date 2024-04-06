package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;

public interface OpenApiAccountTransferModel {
  void send(BizRemitRequest bizRemitRequest, KcdBankAccount kcdBankAccount);
}
