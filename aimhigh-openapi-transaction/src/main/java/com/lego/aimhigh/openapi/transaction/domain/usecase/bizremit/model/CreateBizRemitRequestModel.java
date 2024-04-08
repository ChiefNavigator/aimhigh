package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface CreateBizRemitRequestModel {

  BizRemitRequest createBizRemitRequest(BizRemitRequestCommand command);
}
