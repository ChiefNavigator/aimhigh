package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;

public interface CreateBizRemitRequestModel {

  BizRemitRequest createBizRemitRequest(BizRemitRequest bizRemitRequest);
}
