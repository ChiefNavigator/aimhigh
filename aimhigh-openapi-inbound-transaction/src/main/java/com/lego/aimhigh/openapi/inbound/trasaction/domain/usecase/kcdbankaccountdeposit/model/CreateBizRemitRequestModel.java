package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;

public interface CreateBizRemitRequestModel {

  BizRemitRequest createBizRemitRequest(KcdBankAccountDepositCommand command);

}
