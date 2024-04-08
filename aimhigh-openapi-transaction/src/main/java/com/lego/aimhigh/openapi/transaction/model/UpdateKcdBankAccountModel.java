package com.lego.aimhigh.openapi.transaction.model;

import com.lego.aimhigh.openapi.transaction.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.command.BizRemitRequestCommand;

public interface UpdateKcdBankAccountModel {

  KcdBankAccount updateAmount(BizRemitRequestCommand command);
}
