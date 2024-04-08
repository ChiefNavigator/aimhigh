package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;

public interface GetBizRemitRequestModel {

  BizRemitRequest getBizRemitRequest(Long id);
  BizRemitRequest getBizRemitRequest(String bankTransactionId);
}
