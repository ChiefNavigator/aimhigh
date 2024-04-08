package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface UpdateKcdBankAccountModel {

  KcdBankAccount updateAmount(BizRemitRequestCommand command);
}
