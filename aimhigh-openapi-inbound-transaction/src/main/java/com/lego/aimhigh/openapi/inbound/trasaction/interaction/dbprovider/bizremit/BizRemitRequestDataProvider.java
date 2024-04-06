package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.mapper.BizRemitRequestStatusMapper;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model.CreateBizRemitRequestRecordModel;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model.CreateBizRemitRequestModel;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model.GetBizRemitRequestModel;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.mapper.JpaBizRemitRequestEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class BizRemitRequestDataProvider implements CreateBizRemitRequestModel, CreateBizRemitRequestRecordModel, GetBizRemitRequestModel {

  private final BizRemitRequestRepository bizRemitRequestRepository;
  private final BizRemitRequestRecordRepository bizRemitRequestRecordRepository;

  @Override
  @Transactional
  public BizRemitRequest createBizRemitRequest(KcdBankAccountDepositCommand command) {
    JpaBizRemitRequest jpaBizRemitRequest = new JpaBizRemitRequest();
    jpaBizRemitRequest.setUserId(command.getUserId());
    jpaBizRemitRequest.setUserKcdBankAccountId(command.getUserKcdBankAccountId());
    jpaBizRemitRequest.setRequestDate(command.getRequestDate());
    jpaBizRemitRequest.setFromAccountId(command.getFromAccountId());
    jpaBizRemitRequest.setToAccountId(command.getToAccountId());
    jpaBizRemitRequest.setAmount(command.getAmount());
    jpaBizRemitRequest.setStatus(JpaBizRemitRequestStatus.REQUEST);
    jpaBizRemitRequest.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequest.setCreatedBy(String.valueOf(command.getUserId()));
    jpaBizRemitRequest.setUpdatedBy(String.valueOf(command.getUserId()));
    jpaBizRemitRequest.setCreatedAt(now);
    jpaBizRemitRequest.setUpdatedAt(now);

    return JpaBizRemitRequestEntityMapper.to(bizRemitRequestRepository.save(jpaBizRemitRequest));
  }

  @Override
  @Transactional
  public BizRemitRequestRecord createBizRemitRequestRecord(
    BizRemitRequest bizRemitRequest,
    BizRemitRequestStatus bizRemitRequestStatus
  ) {
    JpaBizRemitRequestRecord jpaBizRemitRequestRecord = new JpaBizRemitRequestRecord();
    jpaBizRemitRequestRecord.setBizRemitRequest(jpaBizRemitRequestFindById(bizRemitRequest.getId()));
    jpaBizRemitRequestRecord.setStatus(BizRemitRequestStatusMapper.to(bizRemitRequestStatus));
    jpaBizRemitRequestRecord.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequestRecord.setCreatedBy(String.valueOf(bizRemitRequest.getUserId()));
    jpaBizRemitRequestRecord.setUpdatedBy(String.valueOf(bizRemitRequest.getUserId()));
    jpaBizRemitRequestRecord.setCreatedAt(now);
    jpaBizRemitRequestRecord.setUpdatedAt(now);

    return JpaBizRemitRequestEntityMapper.to(bizRemitRequestRecordRepository.save(jpaBizRemitRequestRecord));
  }

  private JpaBizRemitRequest jpaBizRemitRequestFindById(Long id) {
    return bizRemitRequestRepository.findById(id).orElseThrow();
  }

  @Override
  public BizRemitRequest getBizRemitRequest(Long id) {
    return JpaBizRemitRequestEntityMapper.to(jpaBizRemitRequestFindById(id));
  }
}
