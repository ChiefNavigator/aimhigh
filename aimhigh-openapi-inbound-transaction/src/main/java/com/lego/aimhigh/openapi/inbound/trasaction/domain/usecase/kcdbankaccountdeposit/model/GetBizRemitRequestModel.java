package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;

public interface GetBizRemitRequestModel {

  BizRemitRequest getBizRemitRequest(Long id);

}
