package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class KcdBankAccountDepositCommand {
  private final Long userId;
  private final Long userKcdBankAccountId;
  private final LocalDateTime requestDate;
  private final String fromAccountId;
  private final String toAccountId;
  private final Long amount;
}
