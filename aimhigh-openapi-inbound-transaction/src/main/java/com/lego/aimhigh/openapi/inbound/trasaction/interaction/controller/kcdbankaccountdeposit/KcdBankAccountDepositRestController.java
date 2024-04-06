package com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit;

import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.KcdBankAccountDepositUseCase;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command.KcdBankAccountDepositCommand;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.contant.KcdBankAccountDepositPayloadExceptionCode;
import com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.exception.KcdBankAccountDepositException;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.exception.KcdBankAccountDepositPayloadException;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.kcdbankaccountdeposit.model.KcdBankAccountDepositPayload;
import com.lego.aimhigh.openapi.inbound.trasaction.interaction.controller.model.ResultVo;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KcdBankAccountDepositRestController {

  private final KcdBankAccountDepositUseCase kcdBankAccountDepositUseCase;

  @PostMapping("/api/kcd-bank/account-deposit/v1")
  public ResultVo<Void> deposit(@RequestBody KcdBankAccountDepositPayload payload) {
    validateKcdBankAccountDepositPayload(payload);

    KcdBankAccountDepositCommand command = KcdBankAccountDepositCommand.builder()
      .userId(payload.getUserId())
      .userKcdBankAccountId(payload.getUserKcdBankAccountId())
      .requestDate(payload.getRequestDate())
      .fromAccountId(payload.getFromAccountId())
      .toAccountId(payload.getToAccountId())
      .amount(payload.getAmount())
      .build();

    Long bizReitRequestId = kcdBankAccountDepositUseCase.createBizReitRequest(command);
    kcdBankAccountDepositUseCase.depositToKcdBankAccount(command, bizReitRequestId);

    return ResultVo.buildSuccess();
  }

  @ExceptionHandler(KcdBankAccountDepositException.class)
  public ResultVo<Void> handleIllegalArgumentException(KcdBankAccountDepositException ex) {
    log.error("exception on /api/kcd-bank/account-deposit/v1", ex);
    return ResultVo.buildFail(ex.getMessage());
  }

  private void validateKcdBankAccountDepositPayload(KcdBankAccountDepositPayload payload) {
    if (payload == null
      || payload.getUserId() == null
      || payload.getUserKcdBankAccountId() == null
      || payload.getRequestDate() == null
      || StringUtils.isEmpty(payload.getFromAccountId())
      || StringUtils.isEmpty(payload.getToAccountId())
      || payload.getAmount() == null
    ) {
      throw new KcdBankAccountDepositPayloadException(KcdBankAccountDepositPayloadExceptionCode.INVALID_PARAMETER);
    }
  }
}
