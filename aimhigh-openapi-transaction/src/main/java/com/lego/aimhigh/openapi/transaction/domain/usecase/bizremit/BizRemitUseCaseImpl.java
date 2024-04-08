package com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit;

import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.KcdBankAccountRecord;
import com.lego.aimhigh.openapi.transaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.transaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.constant.BizRemitExceptionCode;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateBizRemitRequestRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.CreateKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.GetKcdBankAccountRecordModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.OpenApiAccountTransferModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateBizRemitRequestModel;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.model.UpdateKcdBankAccountModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BizRemitUseCaseImpl implements BizRemitUseCase {

  private static final Integer MAX_RETRY_COUNT = 3;

  private final CreateBizRemitRequestModel createBizRemitRequestModel;
  private final GetBizRemitRequestModel getBizRemitRequestModel;
  private final UpdateBizRemitRequestModel updateBizRemitRequestModel;
  private final CreateBizRemitRequestRecordModel createBizRemitRequestRecordModel;

  private final OpenApiAccountTransferModel openApiAccountTransferModel;

  private final UpdateKcdBankAccountModel updateKcdBankAccountModel;
  private final GetKcdBankAccountModel getKcdBankAccountModel;
  private final CreateKcdBankAccountRecordModel createKcdBankAccountRecordModel;
  private final GetKcdBankAccountRecordModel getKcdBankAccountRecordModel;

  @Override
  @Transactional
  public Long createBizRemitRequest(BizRemitRequestCommand command) {
    BizRemitRequest createdBizRemitRequest = getBizRemitRequestModel.getBizRemitRequest(command.getBankTransactionId());
    if (createdBizRemitRequest != null) {
      Integer retryCount = updateBizRemitRequestModel.increaseRetryCount(createdBizRemitRequest.getId(), command.getUserId());
      if (retryCount >= MAX_RETRY_COUNT) {
        updateBizRemitRequestModel.updateRequestStatusFail(createdBizRemitRequest.getId(), command.getUserId());
        log.error("BizRequestFail Cause: MaxRetryCount of attempts was exceeded. bizRemitRequestId: {}", createdBizRemitRequest.getId());
        throw new BizRemitException(BizRemitExceptionCode.EXCEED_MAX_RETRY_COUNT);
      }

      return createdBizRemitRequest.getId();
    }

    final BizRemitRequest bizRemitRequest = createBizRemitRequestModel.createBizRemitRequest(command);
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      bizRemitRequest,
      BizRemitRequestStatus.REQUEST
    );
    final KcdBankAccount kcdBankAccount = getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId());

    openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount)
      .thenRun(() ->
        createBizRemitRequestRecordModel.createBizRemitRequestRecord(
          bizRemitRequest,
          BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING
        )
      );

    return bizRemitRequest.getId();
  }

  @Override
  @Transactional
  public void depositToKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId) {
    KcdBankAccountRecord kcdBankAccountRecord = getKcdBankAccountRecordModel.getKcdBankAccountRecord(command.getBankTransactionId());
    if (kcdBankAccountRecord != null) {
      log.warn("OpenAPI account transfer failed. bizRemitRequestId: {}", bizRemitRequestId);
      return;
    }

    final KcdBankAccount kcdBankAccount = updateKcdBankAccountModel.updateAmount(command);
    createKcdBankAccountRecordModel.createKcdBankAccountRecord(
      kcdBankAccount,
      KcdBankAccountAction.DEPOSIT,
      command.getUserId(),
      command.getBankTransactionId()
    );


    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
      BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_COMPLETED
    );
  }

}
