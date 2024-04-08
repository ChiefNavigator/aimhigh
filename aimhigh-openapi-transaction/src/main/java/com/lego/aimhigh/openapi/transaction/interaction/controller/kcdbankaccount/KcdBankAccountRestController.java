package com.lego.aimhigh.openapi.transaction.interaction.controller.kcdbankaccount;

import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.KcdBankAccountUseCase;
import com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.command.CreateKcdBankAccountCommand;
import com.lego.aimhigh.openapi.transaction.interaction.controller.kcdbankaccount.model.CreateKcdBankAccountPayload;
import com.lego.aimhigh.openapi.transaction.interaction.controller.model.ResultVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KcdBankAccountRestController {

  private final KcdBankAccountUseCase kcdBankAccountUseCase;

  @PostMapping("/openapi-transaction/api/kcd-bank/account-create/v1")
  public ResultVo<Void> createBizRemitRequest(@RequestBody CreateKcdBankAccountPayload payload) {
    validateCreateKcdBankAccountPayload(payload);

    CreateKcdBankAccountCommand command = CreateKcdBankAccountCommand.builder()
      .accountNumber(payload.getAccountNumber())
      .userId(payload.getUserId())
      .amount(payload.getAmount())
      .build();

    kcdBankAccountUseCase.createKcdBankAccount(command);

    return ResultVo.buildSuccess();
  }

  private void validateCreateKcdBankAccountPayload(CreateKcdBankAccountPayload payload) {
    if (payload == null
      || payload.getAccountNumber() == null
      || payload.getUserId() == null
      || payload.getAmount() == null
    ) {
      throw new IllegalArgumentException("Invalid CreateKcdBankAccountPayload");
    }
  }
}
