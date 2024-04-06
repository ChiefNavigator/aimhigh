package com.lego.aimhigh.openapi.inbound.trasaction.domain.usecase.kcdbankaccountdeposit.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

@Getter
@Builder
public class KcdBankAccountDepositCommand {
  @NonNull private final Long userId;
  @NonNull private final Long userKcdBankAccountId;
  @NonNull private final LocalDateTime requestDate;
  @NonNull private final String fromAccountId;
  @NonNull private final String toAccountId;
  @NonNull private final Long amount;
}
