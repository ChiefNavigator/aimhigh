package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;

public interface OpenApiAccountTransferModel {

  void send(BizRemitRequest bizRemitRequest, KcdBankAccount kcdBankAccount, KcdBankAccountAction accountAction);
}
