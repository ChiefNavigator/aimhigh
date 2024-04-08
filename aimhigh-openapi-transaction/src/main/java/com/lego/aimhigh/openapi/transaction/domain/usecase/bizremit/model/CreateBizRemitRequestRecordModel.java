package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model;


import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus;

public interface CreateBizRemitRequestRecordModel {

  BizRemitRequestRecord createBizRemitRequestRecord(BizRemitRequest bizRemitRequest, BizRemitRequestStatus jpaBizRemitRequestStatus);
}
