package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;

public interface GetBizRemitRequestModel {

  BizRemitRequest getBizRemitRequest(Long id);
  BizRemitRequest getBizRemitRequest(String bankTransactionId);
}
