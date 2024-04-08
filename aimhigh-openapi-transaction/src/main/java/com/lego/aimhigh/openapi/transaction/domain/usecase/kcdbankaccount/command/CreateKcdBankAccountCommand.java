package com.lego.aimhigh.openapi.transaction.domain.usecase.kcdbankaccount.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class CreateKcdBankAccountCommand {
  @NonNull private final String accountNumber;
  @NonNull private final Long userId;
  @NonNull private final Long amount;
}
