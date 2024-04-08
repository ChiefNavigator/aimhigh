package com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit;


import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.mapper.BizRemitRequestStatusMapper;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.command.BizRemitRequestCommand;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model.CreateBizRemitRequestRecordModel;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model.CreateBizRemitRequestModel;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model.GetBizRemitRequestModel;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.bizremit.model.UpdateBizRemitRequestModel;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.dbprovider.bizremit.mapper.JpaBizRemitRequestEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class BizRemitRemitRequestDataProvider implements
  CreateBizRemitRequestModel,
  UpdateBizRemitRequestModel,
  GetBizRemitRequestModel,
  CreateBizRemitRequestRecordModel {

  private final BizRemitRequestRepository bizRemitRequestRepository;
  private final BizRemitRequestRecordRepository bizRemitRequestRecordRepository;

  @Override
  @Transactional
  public BizRemitRequest createBizRemitRequest(BizRemitRequestCommand command) {
    JpaBizRemitRequest jpaBizRemitRequest = new JpaBizRemitRequest();
    jpaBizRemitRequest.setBankTransactionId(command.getBankTransactionId());
    jpaBizRemitRequest.setUserId(command.getUserId());
    jpaBizRemitRequest.setUserKcdBankAccountId(command.getUserKcdBankAccountId());
    jpaBizRemitRequest.setRequestDate(command.getRequestDate());
    jpaBizRemitRequest.setFromAccountId(command.getFromAccountId());
    jpaBizRemitRequest.setToAccountId(command.getToAccountId());
    jpaBizRemitRequest.setAmount(command.getAmount());
    jpaBizRemitRequest.setStatus(JpaBizRemitRequestStatus.REQUEST);
    jpaBizRemitRequest.setRetryCount(0);
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

  @Override
  public BizRemitRequest getBizRemitRequest(Long id) {
    return JpaBizRemitRequestEntityMapper.to(jpaBizRemitRequestFindById(id));
  }

  private JpaBizRemitRequest jpaBizRemitRequestFindById(Long id) {
    return bizRemitRequestRepository.findById(id).orElseThrow();
  }

  @Override
  public BizRemitRequest getBizRemitRequest(String bankTransactionId) {
    return JpaBizRemitRequestEntityMapper.to(jpaBizRemitRequestFindByBankTransactionId(bankTransactionId));
  }

  private JpaBizRemitRequest jpaBizRemitRequestFindByBankTransactionId(String bankTransactionId) {
    return bizRemitRequestRepository.findJpaBizRemitRequestByBankTransactionId(bankTransactionId).orElseThrow();
  }

  @Override
  public Integer increaseRetryCount(Long id, Long userId) {
    JpaBizRemitRequest jpaBizRemitRequest = jpaBizRemitRequestFindById(id);
    final Integer retryCount = jpaBizRemitRequest.getRetryCount();
    jpaBizRemitRequest.setRetryCount(retryCount + 1);
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequest.setUpdatedBy(String.valueOf(userId));
    jpaBizRemitRequest.setUpdatedAt(now);

    return null;
  }

  @Override
  public void updateRequestStatusFail(Long id, Long userId) {
    JpaBizRemitRequest jpaBizRemitRequest = jpaBizRemitRequestFindById(id);
    jpaBizRemitRequest.setStatus(JpaBizRemitRequestStatus.FAIL);
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequest.setUpdatedBy(String.valueOf(userId));
    jpaBizRemitRequest.setUpdatedAt(now);
  }
}
