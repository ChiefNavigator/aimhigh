package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.mapper;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.JpaBizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.JpaBizRemitRequestRecord;

public class JpaBizRemitRequestEntityMapper {

  private JpaBizRemitRequestEntityMapper() {
  }

  public static BizRemitRequest to(JpaBizRemitRequest request) {
    BizRemitRequest bizRemitRequest = new BizRemitRequest();
    bizRemitRequest.setId(request.getId());
    bizRemitRequest.setBankTransactionId(request.getBankTransactionId());
    bizRemitRequest.setUserId(request.getUserId());
    bizRemitRequest.setUserKcdBankAccountId(request.getUserKcdBankAccountId());
    bizRemitRequest.setRequestDate(request.getRequestDate());
    bizRemitRequest.setFromAccountId(request.getFromAccountId());
    bizRemitRequest.setToAccountId(request.getToAccountId());
    bizRemitRequest.setAmount(request.getAmount());
    bizRemitRequest.setStatus(JpaBizRemitRequestStatusMapper.to(request.getStatus()));
    bizRemitRequest.setRetryCount(request.getRetryCount());

    return bizRemitRequest;
  }

  public static BizRemitRequestRecord to(JpaBizRemitRequestRecord record) {
    BizRemitRequestRecord bizRemitRequestRecord = new BizRemitRequestRecord();
    bizRemitRequestRecord.setId(record.getId());
    bizRemitRequestRecord.setBizRemitRequest(to(record.getBizRemitRequest()));
    bizRemitRequestRecord.setStatus(JpaBizRemitRequestStatusMapper.to(record.getStatus()));

    return bizRemitRequestRecord;
  }
}
