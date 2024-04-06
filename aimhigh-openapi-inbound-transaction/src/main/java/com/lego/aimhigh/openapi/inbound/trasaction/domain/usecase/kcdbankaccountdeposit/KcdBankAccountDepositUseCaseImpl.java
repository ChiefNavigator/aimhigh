package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.KcdBankAccount;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bankaccount.contant.KcdBankAccountAction;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.BizRemitRequest;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.entity.bizremit.contant.BizRemitRequestStatus;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KcdBankAccountDepositUseCaseImpl implements KcdBankAccountDepositUseCase {

  private final CreateBizRemitRequestModel createBizRemitRequestModel;
  private final CreateBizRemitRequestRecordModel createBizRemitRequestRecordModel;
  private final GetBizRemitRequestModel getBizRemitRequestModel;
  private final OpenApiAccountTransferModel openApiAccountTransferModel;
  private final CreateKcdBankAccountRecordModel createKcdBankAccountRecordModel;
  private final UpdateKcdBankAccountModel updateKcdBankAccountModel;
  private final GetKcdBankAccountModel getKcdBankAccountModel;

  @Override
  @Transactional
  public Long createBizReitRequest(KcdBankAccountDepositCommand command) {
    final BizRemitRequest bizRemitRequest = createBizRemitRequestModel.createBizRemitRequest(command);

    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      bizRemitRequest,
      BizRemitRequestStatus.REQUEST
    );
    final KcdBankAccount kcdBankAccount = getKcdBankAccountModel.getKcdBankAccount(command.getUserKcdBankAccountId());
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      bizRemitRequest,
      BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING
    );

    openApiAccountTransferModel.send(bizRemitRequest, kcdBankAccount);

    return bizRemitRequest.getId();
  }

  @Override
  @Transactional
  public void depositToKcdBankAccount(KcdBankAccountDepositCommand command, Long bizRemitRequestId) {
    final KcdBankAccount kcdBankAccount = updateKcdBankAccountModel.updateAmount(command);
    createKcdBankAccountRecordModel.createKcdBankAccountRecord(
      kcdBankAccount,
      KcdBankAccountAction.DEPOSIT,
      command.getUserId()
    );
    createBizRemitRequestRecordModel.createBizRemitRequestRecord(
      getBizRemitRequestModel.getBizRemitRequest(bizRemitRequestId),
      BizRemitRequestStatus.KCD_ACCOUNT_DEPOSIT_PENDING
    );
  }

}
