package com.lego.aimhigh.openapi.transaction.model;


import com.lego.aimhigh.openapi.transaction.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.command.BizRemitRequestCommand;

public interface CreateBizRemitRequestModel {

  BizRemitRequest createBizRemitRequest(BizRemitRequestCommand command);
}
