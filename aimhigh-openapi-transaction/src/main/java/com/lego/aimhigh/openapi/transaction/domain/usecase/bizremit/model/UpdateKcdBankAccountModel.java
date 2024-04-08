package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface UpdateKcdBankAccountModel {

  KcdBankAccount updateAmount(BizRemitRequestCommand command);
}
