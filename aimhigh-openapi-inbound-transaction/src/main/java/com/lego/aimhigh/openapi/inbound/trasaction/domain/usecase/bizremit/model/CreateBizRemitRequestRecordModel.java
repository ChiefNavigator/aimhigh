package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;

public interface CreateBizRemitRequestRecordModel {

  BizRemitRequestRecord createBizRemitRequestRecord(BizRemitRequest bizRemitRequest, BizRemitRequestStatus jpaBizRemitRequestStatus);
}
