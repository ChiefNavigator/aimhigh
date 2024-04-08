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
      final int retryCount = createdBizRemitRequest.getRetryCount() + 1;
      final BizRemitRequestStatus bizRemitRequestStatus = retryCount >= MAX_RETRY_COUNT ? BizRemitRequestStatus.FAIL : createdBizRemitRequest.getStatus();
      createdBizRemitRequest.setRetryCount(retryCount + 1);
      createdBizRemitRequest.setStatus(bizRemitRequestStatus);
      updateBizRemitRequestModel.updateBizRemitRequest(createdBizRemitRequest);

      if (retryCount >= MAX_RETRY_COUNT) {
//        Slack Alert
        log.error("BizRequestFail Cause: MaxRetryCount of attempts was exceeded. bizRemitRequestId: {}", createdBizRemitRequest.getId());
        throw new BizRemitException(BizRemitExceptionCode.EXCEED_MAX_RETRY_COUNT, createdBizRemitRequest.getId());
      }

      return createdBizRemitRequest.getId();
    }

    final BizRemitRequest bizRemitRequest = createBizRemitRequestModel.createBizRemitRequest(buildBizRemitRequest(command));
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      bizRemitRequest,
      BizRemitRequestStatus.REQUEST
    );
    final KcdBankAccount kcdBankAccount = getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId());
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      bizRemitRequest,
      BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING
    );
    openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount, KcdBankAccountAction.DEPOSIT);

    return bizRemitRequest.getId();
  }

  private static BizRemitRequest buildBizRemitRequest(BizRemitRequestCommand command) {
    BizRemitRequest bizRemitRequest = new BizRemitRequest();
    bizRemitRequest.setBankTransactionId(command.getBankTransactionId());
    bizRemitRequest.setUserId(command.getUserId());
    bizRemitRequest.setUserKcdBankAccountId(command.getUserKcdBankAccountId());
    bizRemitRequest.setRequestDate(command.getRequestDate());
    bizRemitRequest.setFromAccountId(command.getFromAccountId());
    bizRemitRequest.setToAccountId(command.getToAccountId());
    bizRemitRequest.setAmount(command.getAmount());
    bizRemitRequest.setStatus(BizRemitRequestStatus.REQUEST);
    bizRemitRequest.setRetryCount(0);

    return bizRemitRequest;
  }

  @Override
  @Transactional
  public void depositToKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId) {
    KcdBankAccountRecord kcdBankAccountRecord = getKcdBankAccountRecordModel.getKcdBankAccountRecord(command.getBankTransactionId(), KcdBankAccountAction.DEPOSIT);
    if (kcdBankAccountRecord != null) {
      log.warn("KcdBankAccount deposit has already been processed. bizRemitRequestId: {}", bizRemitRequestId);
      return;
    }

    final KcdBankAccount kcdBankAccount = getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId());
    final Long amount = kcdBankAccount.getAmount() + command.getAmount();
    kcdBankAccount.setAmount(amount);
    updateKcdBankAccountModel.updateAmount(kcdBankAccount, command.getUserId());

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

  @Override
  @Transactional
  public void withdrawalFromKcdBankAccount(BizRemitRequestCommand command, Long bizRemitRequestId) {
    KcdBankAccountRecord kcdBankAccountRecord = getKcdBankAccountRecordModel.getKcdBankAccountRecord(command.getBankTransactionId(), KcdBankAccountAction.WITHDRAWAL);
    if (kcdBankAccountRecord != null) {
      log.warn("KcdBankAccount withdrawal has already been processed bizRemitRequestId: {}", bizRemitRequestId);
      return;
    }

    final BizRemitRequest bizRemitRequest = getBizRemitRequestModel.getBizRemitRequest(command.getBankTransactionId());
    final KcdBankAccount kcdBankAccount = getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId());
    final Long amount = kcdBankAccount.getAmount() - command.getAmount();
    kcdBankAccount.setAmount(amount);
    updateKcdBankAccountModel.updateAmount(kcdBankAccount, command.getUserId());

    createKcdBankAccountRecordModel.createKcdBankAccountRecord(
      kcdBankAccount,
      KcdBankAccountAction.WITHDRAWAL,
      command.getUserId(),
      command.getBankTransactionId()
    );

    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
      BizRemitRequestStatus.KCD_ACCOUNT_WITHDRAWAL_COMPLETED
    );
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
      BizRemitRequestStatus.DONE
    );

    bizRemitRequest.setStatus(BizRemitRequestStatus.DONE);
    updateBizRemitRequestModel.updateBizRemitRequest(bizRemitRequest);

    openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount, KcdBankAccountAction.WITHDRAWAL);
  }

}
