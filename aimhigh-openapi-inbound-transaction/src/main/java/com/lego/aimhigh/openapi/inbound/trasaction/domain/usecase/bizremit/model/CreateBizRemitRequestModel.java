package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.command.BizRemitRequestCommand;

public interface CreateBizRemitRequestModel {

  BizRemitRequest createBizRemitRequest(BizRemitRequestCommand command);
}
