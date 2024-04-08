package com.lego.aimhigh.openapi.transaction.model;


import com.lego.aimhigh.openapi.transaction.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.transaction.entity.bizremit.contant.BizRemitRequestStatus;

public interface CreateBizRemitRequestRecordModel {

  BizRemitRequestRecord createBizRemitRequestRecord(BizRemitRequest bizRemitRequest, BizRemitRequestStatus jpaBizRemitRequestStatus);
}
