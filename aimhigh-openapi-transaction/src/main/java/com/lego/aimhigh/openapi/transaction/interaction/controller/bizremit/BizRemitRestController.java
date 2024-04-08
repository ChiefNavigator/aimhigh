package com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit;

import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.BizRemitUseCase;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.command.BizRemitRequestCommand;
import com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.contant.BizRemitRequestPayloadExceptionCode;
import com.lego.aimhigh.openapi.transaction.domain.usecase.bizremit.exception.BizRemitException;
import com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.exception.BizRemitPayloadException;
import com.lego.aimhigh.openapi.transaction.interaction.controller.bizremit.model.BizRemitRequestPayload;
import com.lego.aimhigh.openapi.transaction.interaction.controller.model.ResultVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BizRemitRestController {

  private final BizRemitUseCase bizRemitUseCase;

  @PostMapping("/openapi-transaction/api/biz-remit-request/v1")
  public ResultVo<Long> createBizRemitRequest(@RequestBody BizRemitRequestPayload payload) {
    validateBizRemitRequestPayload(payload);

    BizRemitRequestCommand command = BizRemitRequestCommand.builder()
      .bankTransactionId(payload.getBankTransactionId())
      .userId(payload.getUserId())
      .userKcdBankAccountId(payload.getUserKcdBankAccountId())
      .requestDate(payload.getRequestDate())
      .fromAccountId(payload.getFromAccountId())
      .toAccountId(payload.getToAccountId())
      .amount(payload.getAmount())
      .build();

    Long bizReitRequestId = bizRemitUseCase.createBizRemitRequest(command);
    return ResultVo.buildSuccess(bizReitRequestId);
  }

  @PostMapping("/openapi-transaction/api/biz-remit-request/v1/{bizRemitRequestId}/account-deposit")
  public ResultVo<Void> depositToKcdBankAccount(@RequestBody BizRemitRequestPayload payload, @PathVariable Long bizRemitRequestId) {
    validateBizRemitRequestPayload(payload);

    BizRemitRequestCommand command = BizRemitRequestCommand.builder()
      .bankTransactionId(payload.getBankTransactionId())
      .userId(payload.getUserId())
      .userKcdBankAccountId(payload.getUserKcdBankAccountId())
      .requestDate(payload.getRequestDate())
      .fromAccountId(payload.getFromAccountId())
      .toAccountId(payload.getToAccountId())
      .amount(payload.getAmount())
      .build();

    bizRemitUseCase.depositToKcdBankAccount(command, bizRemitRequestId);
    return ResultVo.buildSuccess();
  }

  @PostMapping("/openapi-transaction/api/biz-remit-request/v1/{bizRemitRequestId}/account-withdrawal")
  public ResultVo<Void> withdrawalFromKcdBankAccount(@RequestBody BizRemitRequestPayload payload, @PathVariable Long bizRemitRequestId) {
    validateBizRemitRequestPayload(payload);

    BizRemitRequestCommand command = BizRemitRequestCommand.builder()
      .bankTransactionId(payload.getBankTransactionId())
      .userId(payload.getUserId())
      .userKcdBankAccountId(payload.getUserKcdBankAccountId())
      .requestDate(payload.getRequestDate())
      .fromAccountId(payload.getFromAccountId())
      .toAccountId(payload.getToAccountId())
      .amount(payload.getAmount())
      .build();

    bizRemitUseCase.withdrawalFromKcdBankAccount(command, bizRemitRequestId);
    return ResultVo.buildSuccess();
  }


  @ExceptionHandler(BizRemitException.class)
  public ResultVo<Void> handleBizRemitException(BizRemitException ex) {
    log.error("handleBizRemitException bizRemitRequestId:{}", ex.getBizRemitRequestId(), ex);
    return ResultVo.buildFail(ex.getMessage());
  }

  private void validateBizRemitRequestPayload(BizRemitRequestPayload payload) {
    if (payload == null
      || payload.getBankTransactionId() == null
      || payload.getUserId() == null
      || payload.getUserKcdBankAccountId() == null
      || payload.getRequestDate() == null
      || StringUtils.isEmpty(payload.getFromAccountId())
      || StringUtils.isEmpty(payload.getToAccountId())
      || payload.getAmount() == null
    ) {
      throw new BizRemitPayloadException(BizRemitRequestPayloadExceptionCode.INVALID_PARAMETER);
    }
  }
}
