package com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit;


import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequestRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.mapper.BizRemitRequestStatusMapper;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit.contant.JpaBizRemitRequestStatus;
import com.lego.aimhigh.openapi.transaction.interaction.dbprovider.bizremit.mapper.JpaBizRemitRequestEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
  public BizRemitRequest createBizRemitRequest(BizRemitRequest bizRemitRequest) {
    JpaBizRemitRequest jpaBizRemitRequest = new JpaBizRemitRequest();
    jpaBizRemitRequest.setBankTransactionId(bizRemitRequest.getBankTransactionId());
    jpaBizRemitRequest.setUserId(bizRemitRequest.getUserId());
    jpaBizRemitRequest.setUserKcdBankAccountId(bizRemitRequest.getUserKcdBankAccountId());
    jpaBizRemitRequest.setRequestDate(bizRemitRequest.getRequestDate());
    jpaBizRemitRequest.setFromAccountId(bizRemitRequest.getFromAccountId());
    jpaBizRemitRequest.setToAccountId(bizRemitRequest.getToAccountId());
    jpaBizRemitRequest.setAmount(bizRemitRequest.getAmount());
    jpaBizRemitRequest.setStatus(JpaBizRemitRequestStatus.REQUEST);
    jpaBizRemitRequest.setRetryCount(0);
    jpaBizRemitRequest.setDeleted(false);
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequest.setCreatedBy(String.valueOf(bizRemitRequest.getUserId()));
    jpaBizRemitRequest.setUpdatedBy(String.valueOf(bizRemitRequest.getUserId()));
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

  @Override
  public BizRemitRequest getNullableBizRemitRequest(String bankTransactionId) {
    return bizRemitRequestRepository.findJpaBizRemitRequestByBankTransactionId(bankTransactionId)
      .map(JpaBizRemitRequestEntityMapper::to)
      .orElse(null);
  }

  private JpaBizRemitRequest jpaBizRemitRequestFindByBankTransactionId(String bankTransactionId) {
    return bizRemitRequestRepository.findJpaBizRemitRequestByBankTransactionId(bankTransactionId).orElseThrow();
  }

  @Override
  @Transactional
  public void updateBizRemitRequest(BizRemitRequest request) {
    JpaBizRemitRequest jpaBizRemitRequest = jpaBizRemitRequestFindById(request.getId());
    jpaBizRemitRequest.setRetryCount(request.getRetryCount());
    jpaBizRemitRequest.setStatus(BizRemitRequestStatusMapper.to(request.getStatus()));
    LocalDateTime now = LocalDateTime.now();
    jpaBizRemitRequest.setUpdatedBy(String.valueOf(request.getUserId()));
    jpaBizRemitRequest.setUpdatedAt(now);
  }
}
