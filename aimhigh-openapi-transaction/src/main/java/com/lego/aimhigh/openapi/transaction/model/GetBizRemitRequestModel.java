package com.lego.aimhigh.openapi.transaction.model;


import com.lego.aimhigh.openapi.transaction.entity.bizremit.BizRemitRequest;

public interface GetBizRemitRequestModel {

  BizRemitRequest getBizRemitRequest(Long id);
  BizRemitRequest getBizRemitRequest(String bankTransactionId);
}
